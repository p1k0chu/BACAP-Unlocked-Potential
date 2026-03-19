package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import com.github.p1k0chu.bacup.advancement.impossibleTrigger
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.Items
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object EndTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "end"

    const val DRAGON2_0 = "dragon_2_0"
    const val INTENTIONAL_ADVANCEMENT_DESIGN = "intentional_advancement_design"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, DRAGON2_0) {
            parent(createPlaceholder("minecraft:end/respawn_dragon"))
            display {
                icon = Items.END_CRYSTAL.defaultInstance.apply {
                    enchant(wrapperLookup.getOrThrow(Enchantments.BLAST_PROTECTION), 1)
                }
                frame = AdvancementType.GOAL
            }
            addCriterion("10", Criteria.SPAWN_DRAGON_WITH_CRYSTALS.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(MinMaxBounds.Ints.atLeast(10)))))
        }.also(consumer::accept)

        advancement(TAB_NAME, INTENTIONAL_ADVANCEMENT_DESIGN) {
            parent(createPlaceholder("blazeandcave:end/the_actual_end"))
            display {
                icon = Items.RED_BED.defaultInstance
                frame = AdvancementType.TASK
            }
            addCriterion("die", impossibleTrigger())
        }.also(consumer::accept)
    }
}