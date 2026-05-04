package io.github.p1k0chu.bacapup

import io.github.p1k0chu.bacapup.advancement.createAdvancementProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class DatagenMain : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()
        pack.addProvider(::createAdvancementProvider)
    }
}
