package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.BiomesTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.core.HolderLookup
import java.util.function.Consumer

object BiomesTabRewardsGenerator : FunctionGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        consumer: Consumer<MCFunction>
    ) {
        rewardsBuilder(consumer) {
            tab(BiomesTabGenerator.TAB_NAME) {
                advancement(BiomesTabGenerator.CONDUEL) {
                    type = AdvancementType.GOAL
                }
            }
        }
    }
}