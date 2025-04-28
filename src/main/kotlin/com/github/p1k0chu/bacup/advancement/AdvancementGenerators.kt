package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.advancement.generator.*
import net.minecraft.data.DataOutput
import net.minecraft.data.advancement.AdvancementProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

fun createAdvancementProvider(
    output: DataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
): AdvancementProvider {
    return AdvancementProvider(
        output, registriesFuture, listOf(
            AdventureTabGenerator,
            AnimalsTabGenerator,
            EnchantingTabGenerator,
            FarmingTabGenerator,
            MonstersTabGenerator,
        )
    )
}
