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
            AdventureTabSubProvider,
            AnimalsTabSubProvider,
            BiomesTabSubProvider,
            EnchantingTabSubProvider,
            FarmingTabSubProvider,
            MonstersTabSubProvider,
            MiningTabSubProvider,
            StatisticsTabSubProvider,
            EndTabSubProvider,
            BuildingTabSubProvider,
            ChallengesTabSubProvider,
            NetherTabSubProvider,
            RedstoneTabSubProvider,
        )
    )
}
