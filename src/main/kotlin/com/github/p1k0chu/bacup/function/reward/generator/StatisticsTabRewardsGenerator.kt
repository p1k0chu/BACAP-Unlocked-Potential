package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.StatisticsTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object StatisticsTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(StatisticsTabGenerator.TAB_NAME) {
                advancement(StatisticsTabGenerator.EMERALD_PORTFOLIO)
                advancement(StatisticsTabGenerator.THE_STOCK_MARKET) {
                    type = AdvancementType.GOAL
                }
                advancement(StatisticsTabGenerator.SMALL_BUSINESS) {
                    type = AdvancementType.CHALLENGE
                    item(ItemStack(Items.EMERALD, 8))
                    exp = 50
                }
                advancement(StatisticsTabGenerator.SMALL_INDIE_COMPANY) {
                    type = AdvancementType.HIDDEN
                    exp = 200
                }
            }
        }
    }
}