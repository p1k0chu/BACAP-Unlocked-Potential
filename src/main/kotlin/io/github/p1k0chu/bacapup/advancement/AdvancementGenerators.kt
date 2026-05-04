package io.github.p1k0chu.bacapup.advancement

import io.github.p1k0chu.bacapup.advancement.generator.*
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

fun createAdvancementProvider(
    output: PackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
): AdvancementProvider {
    return AdvancementProvider(
        output, registriesFuture, listOf(
            AdventureTabGenerator,
            AnimalsTabGenerator,
            BiomesTabGenerator,
            EnchantingTabGenerator,
            FarmingTabGenerator,
            MonstersTabGenerator,
            MiningTabGenerator,
            StatisticsTabGenerator,
            EndTabGenerator,
            BuildingTabGenerator,
            ChallengesTabGenerator,
            NetherTabGenerator,
            RedstoneTabGenerator,
        )
    )
}
