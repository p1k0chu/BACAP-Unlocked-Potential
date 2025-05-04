package com.github.p1k0chu.bacup.function

import com.github.p1k0chu.bacup.function.reward.generator.*
import net.minecraft.data.DataOutput
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

fun createFunctionProvider(
    output: DataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
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
