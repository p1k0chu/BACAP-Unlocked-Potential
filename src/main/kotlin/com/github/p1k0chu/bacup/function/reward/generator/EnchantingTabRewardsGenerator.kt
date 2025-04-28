package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.EnchantingTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object EnchantingTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(EnchantingTabGenerator.TAB_NAME) {
                advancement(EnchantingTabGenerator.DISENCHANTED)
            }
        }
    }
}