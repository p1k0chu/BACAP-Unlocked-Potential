package com.github.p1k0chu.bacup.advancement.reward

import com.github.p1k0chu.bacup.data.advancement.AdventureTabGenerator
import com.github.p1k0chu.bacup.data.function.FunctionGenerator
import com.github.p1k0chu.bacup.data.function.MCFunction
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object RewardFunctionGenerator : FunctionGenerator {
    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<MCFunction>) {
        rewardsBuilder(consumer) {
            tab(AdventureTabGenerator.TAB_NAME) {
                advancement(AdventureTabGenerator.CAT_GIFT)
                advancement(AdventureTabGenerator.ALL_CAT_GIFTS)
            }
        }
    }
}
