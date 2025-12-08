package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.EndTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.core.HolderLookup
import java.util.function.Consumer

object EndTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: HolderLookup.Provider, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(EndTabGenerator.TAB_NAME) {
                advancement(EndTabGenerator.DRAGON2_0) {
                    type = AdvancementType.GOAL
                    exp = 50
                    item(ItemStack(Items.END_CRYSTAL, 2))
                }
            }
        }
    }
}