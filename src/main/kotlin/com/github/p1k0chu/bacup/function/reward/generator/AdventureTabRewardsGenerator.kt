package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.AdventureTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object AdventureTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(AdventureTabGenerator.TAB_NAME) {
                advancement(AdventureTabGenerator.CAT_GIFT)
                advancement(AdventureTabGenerator.PLETHORA_OF_CATS) {
                    type = AdvancementType.GOAL
                    exp = 50
                }
                advancement(AdventureTabGenerator.ALL_CAT_GIFTS) {
                    type = AdvancementType.GOAL
                }
                advancement(AdventureTabGenerator.LOCK_MAP)
                advancement(AdventureTabGenerator.GET_RAID_OF_IT)
                advancement(AdventureTabGenerator.CAN_YOU_HEAR_IT_FROM_HERE) {
                    type = AdvancementType.GOAL
                    exp = 50
                }
            }
        }
    }
}
