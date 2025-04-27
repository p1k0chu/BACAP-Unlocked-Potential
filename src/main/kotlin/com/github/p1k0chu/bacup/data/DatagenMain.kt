package com.github.p1k0chu.bacup.data

import com.github.p1k0chu.bacup.data.advancement.AdventureTabGenerator
import com.github.p1k0chu.bacup.data.language.EnglishLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.data.DataOutput
import net.minecraft.data.advancement.AdvancementProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class DatagenMain : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()
        pack.addProvider(::EnglishLanguageProvider)
        pack.addProvider(::createAdvancementProvider)
    }
}

fun createAdvancementProvider(
    output: DataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
): AdvancementProvider {
    return AdvancementProvider(
        output, registriesFuture, listOf(
            AdventureTabGenerator,
        )
    )
}