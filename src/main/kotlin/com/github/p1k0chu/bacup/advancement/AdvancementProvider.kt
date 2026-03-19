package com.github.p1k0chu.bacup.advancement

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
import kotlinx.io.IOException
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.core.HolderLookup
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import net.minecraft.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class AdvancementProvider(
    output: PackOutput,
    private val registriesFuture: CompletableFuture<HolderLookup.Provider>,
    private val generators: Collection<AdvancementGenerator> = listOf()
) : DataProvider {
    private val advancementPathResolver = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancement")
    private val functionPathResolver = output.createPathProvider(PackOutput.Target.DATA_PACK, "function")

    override fun run(writer: CachedOutput): CompletableFuture<*> {
        return registriesFuture.thenCompose { wrapperLookup: HolderLookup.Provider ->
            val funcIds = mutableSetOf<Identifier>()
            val advIds = mutableSetOf<Identifier>()
            val futures = mutableListOf<CompletableFuture<*>>()

            val consumer = object : AdvancementConsumer {
                override fun advancement(advancementHolder: AdvancementHolder) {
                    if (!advIds.add(advancementHolder.id)) {
                        throw IllegalStateException("Duplicate advancement ${advancementHolder.id}")
                    }
                    futures.add(
                        DataProvider.saveStable(
                            writer,
                            wrapperLookup,
                            Advancement.CODEC,
                            advancementHolder.value(),
                            advancementPathResolver.json(advancementHolder.id)
                        )
                    )
                }

                override fun function(function: MCFunction) {
                    if (!funcIds.add(function.id)) {
                        throw IllegalStateException("Duplicate function ${function.id}")
                    }
                    futures.add(
                        writeToFile(
                            writer,
                            function.body,
                            functionPathResolver.file(function.id, "mcfunction")
                        )
                    )
                }
            }

            generators.forEach { it: AdvancementGenerator ->
                it.generate(wrapperLookup, consumer)
            }

            CompletableFuture.allOf(*futures.toTypedArray())
        }
    }

    override fun getName(): String = "BACAPUPAdvancements"

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AdvancementProvider::class.java)

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
