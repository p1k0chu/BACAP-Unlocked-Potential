package com.github.p1k0chu.bacup.data.advancement

import com.github.p1k0chu.bacup.data.advancement.generator.AdventureTabGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class AdvancementProvider(
    output: FabricDataOutput?, wrapperLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricAdvancementProvider(output, wrapperLookup) {
    companion object {
        val generators: List<AdvancementTabGenerator> = listOf(AdventureTabGenerator)
    }

    override fun generateAdvancement(
        wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>
    ) {
        generators.forEach { it.accept(wrapperLookup, consumer) }
    }
}
