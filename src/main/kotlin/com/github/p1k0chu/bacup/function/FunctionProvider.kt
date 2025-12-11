package com.github.p1k0chu.bacup.function

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
import kotlinx.io.IOException
import net.minecraft.data.PackOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.CachedOutput
import net.minecraft.core.HolderLookup
import net.minecraft.resources.Identifier
import net.minecraft.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class FunctionProvider(
    output: PackOutput,
    private val registriesFuture: CompletableFuture<HolderLookup.Provider>,
    private val generators: Collection<FunctionGenerator> = listOf()
) : DataProvider {
    private val pathResolver = output.createPathProvider(PackOutput.Target.DATA_PACK, "function")

    override fun run(writer: CachedOutput): CompletableFuture<*> {
        return registriesFuture.thenCompose { wrapperLookup: HolderLookup.Provider ->
            val ids = mutableSetOf<Identifier>()
            val futures = mutableListOf<CompletableFuture<*>>()

            val consumer: (MCFunction) -> Unit = { func: MCFunction ->
                if (!ids.add(func.id)) {
                    throw IllegalStateException("Duplicate function ${func.id}")
                }
                val path = pathResolver.file(func.id, "mcfunction")
                futures.add(writeToFile(writer, func.body, path))
            }

            generators.forEach { it: FunctionGenerator ->
                it.accept(wrapperLookup, consumer)
            }

            CompletableFuture.allOf(*futures.toTypedArray())
        }
    }

    override fun getName(): String = "Functions"

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(FunctionProvider::class.java)

        private fun writeToFile(
            writer: CachedOutput,
            funcBody: String,
            path: Path
        ) = CompletableFuture.runAsync({
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                val hashingOutputStream = HashingOutputStream(Hashing.sha1(), byteArrayOutputStream)

                hashingOutputStream.writer().use { it.write(funcBody) }
                writer.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash())
            } catch (e: IOException) {
                LOGGER.error("Failed to save file to {}", path, e)
            }
        }, Util.backgroundExecutor().forName("saveStable"))
    }
}