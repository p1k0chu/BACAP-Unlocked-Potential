package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.FarmingTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object FarmingTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(FarmingTabGenerator.TAB_NAME) {
                advancement(FarmingTabGenerator.ALTERNATIVE_FUEL) {
                    item(ItemStack(Items.DRIED_KELP_BLOCK, 1))
                }
                advancement(FarmingTabGenerator.SUPER_FUEL)
            }
        }
    }
}