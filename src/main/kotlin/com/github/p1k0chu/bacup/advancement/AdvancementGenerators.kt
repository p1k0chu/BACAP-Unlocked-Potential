package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.advancement.generator.*
import net.minecraft.data.PackOutput
import net.minecraft.data.advancements.AdvancementProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

fun createAdvancementProvider(
    output: PackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
): AdvancementProvider {
    return AdvancementProvider(
        output, registriesFuture, listOf(
            AdventureTabGenerator,
            AnimalsTabGenerator,
            EnchantingTabGenerator,
            FarmingTabGenerator,
            MonstersTabGenerator,
            MiningTabGenerator,
            StatisticsTabGenerator,
            EndTabGenerator,
            BuildingTabGenerator,
            ChallengesTabGenerator
        )
    )
}
