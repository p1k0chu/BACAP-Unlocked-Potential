package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.MonstersTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object MonstersTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(MonstersTabGenerator.TAB_NAME) {
                advancement(MonstersTabGenerator.BEAM_ME_UP) {
                    type = AdvancementType.GOAL
                    exp = 50
                    item(ItemStack(Items.ENDER_PEARL, 1))
                }
            }
        }
    }
}