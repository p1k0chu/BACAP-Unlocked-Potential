package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.createAdvancementProvider
import com.github.p1k0chu.bacup.function.createFunctionProvider
import com.github.p1k0chu.bacup.language.EnglishLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class DatagenMain : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()
        pack.addProvider(::EnglishLanguageProvider)
        pack.addProvider(::createAdvancementProvider)
        pack.addProvider(::createFunctionProvider)
    }
}