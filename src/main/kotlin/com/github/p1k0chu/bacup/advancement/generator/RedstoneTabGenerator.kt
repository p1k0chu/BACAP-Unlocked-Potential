package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.impossibleTrigger
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import java.util.function.Consumer

object RedstoneTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "redstone"

    const val SMELT_EVERYTHING = "smelt_everything"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: AdvancementConsumer
    ) {
        advancement(consumer, TAB_NAME, SMELT_EVERYTHING) {
            parent(createPlaceholder("blazeandcave:redstone/space_hopper"))
            display {
                title = "Smelt Everything"
                description = "Connect 3 Chests to a single Furnace using 3 Hoppers."
                icon = Items.FURNACE.defaultInstance
            }
            addCriterion("open_it", impossibleTrigger())
        }
    }
}