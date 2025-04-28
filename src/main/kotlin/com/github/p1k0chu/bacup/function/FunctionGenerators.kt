package com.github.p1k0chu.bacup.function

import com.github.p1k0chu.bacup.function.reward.generator.AdventureTabRewardsGenerator
import com.github.p1k0chu.bacup.function.reward.generator.AnimalsTabRewardsGenerator
import net.minecraft.data.DataOutput
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

fun createFunctionProvider(
    output: DataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
): FunctionProvider {
    return FunctionProvider(
        output, registriesFuture, listOf(
            AdventureTabRewardsGenerator,
            AnimalsTabRewardsGenerator
        )
    )
}