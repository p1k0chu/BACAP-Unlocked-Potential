package com.github.p1k0chu.bacup.function

import com.github.p1k0chu.bacup.function.reward.generator.*
import net.minecraft.data.PackOutput
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

fun createFunctionProvider(
    output: PackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
): FunctionProvider {
    return FunctionProvider(
        output, registriesFuture, listOf(
            AdventureTabRewardsGenerator,
            AnimalsTabRewardsGenerator,
            EnchantingTabRewardsGenerator,
            FarmingTabRewardsGenerator,
            MonstersTabRewardsGenerator,
            MiningTabRewardsGenerator,
            StatisticsTabRewardsGenerator,
            EndTabRewardsGenerator,
            BuildingTabRewardsGenerator,
            ChallengesTabRewardsGenerator
        )
    )
}
