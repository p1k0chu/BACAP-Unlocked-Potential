package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.NetherTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import com.github.p1k0chu.bacup.utils.setCustomName
import com.github.p1k0chu.bacup.utils.setLore
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Items
import java.util.function.Consumer

object NetherTabRewardsGenerator : FunctionGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        consumer: Consumer<MCFunction>
    ) {
        rewardsBuilder(consumer) {
            tab(NetherTabGenerator.TAB_NAME) {
                advancement(NetherTabGenerator.EXTREME_BREAK_RISK) {
                    type = AdvancementType.CHALLENGE
                    trophy = Items.POLISHED_BLACKSTONE.defaultInstance.apply {
                        setCustomName("Catharsis")
                        setLore("The Last Straw Was: Ate Without Table")
                        set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    }
                }
                advancement(NetherTabGenerator.BEACON_ALL_ITEMS) {
                    type = AdvancementType.GOAL
                }
            }
        }
    }
}