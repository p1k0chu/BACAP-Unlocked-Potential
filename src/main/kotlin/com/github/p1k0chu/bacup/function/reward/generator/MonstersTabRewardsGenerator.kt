package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.MonstersTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.core.HolderLookup
import java.util.function.Consumer

object MonstersTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: HolderLookup.Provider, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(MonstersTabGenerator.TAB_NAME) {
                advancement(MonstersTabGenerator.BEAM_ME_UP) {
                    type = AdvancementType.GOAL
                    exp = 50
                    item(ItemStack(Items.ENDER_PEARL, 1))
                }
                advancement(MonstersTabGenerator.FORGED_BY_FLESH) {
                    type = AdvancementType.GOAL
                    exp = 50
                    item(ItemStack(Items.IRON_INGOT, 1))
                }
            }
        }
    }
}