package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import java.util.function.Consumer

object NetherTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "nether"

    const val EXTREME_BREAK_RISK = "extreme_break_risk"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: Consumer<AdvancementHolder>
    ) {
        advancement(TAB_NAME, EXTREME_BREAK_RISK) {
            parent(createPlaceholder("minecraft:nether/netherite_armor"))
            display {
                icon = Items.NETHERITE_BLOCK.defaultInstance
                frame = AdvancementType.CHALLENGE
            }
            addCriterion(
                "netherite_block", Criteria.PLAYER_BREAK_NETHERITE_BLOCK_WITH_FIST.createCriterion(
                    EmptyCriterion.Conditions()
                ))
        }.also(consumer::accept)
    }
}
