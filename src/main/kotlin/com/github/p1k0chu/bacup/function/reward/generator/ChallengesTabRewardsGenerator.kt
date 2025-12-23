package com.github.p1k0chu.bacup.function.reward.generator

import com.github.p1k0chu.bacup.advancement.generator.ChallengesTabGenerator
import com.github.p1k0chu.bacup.function.FunctionGenerator
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.AdvancementType
import com.github.p1k0chu.bacup.function.reward.rewardsBuilder
import net.minecraft.core.HolderLookup
import java.util.function.Consumer

object ChallengesTabRewardsGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: HolderLookup.Provider, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(ChallengesTabGenerator.TAB_NAME) {
                advancement(ChallengesTabGenerator.MOB_FLATTENER_9000) {
                    type = AdvancementType.SUPER_CHALLENGE
                    exp = 200
                }
            }
        }
    }
}