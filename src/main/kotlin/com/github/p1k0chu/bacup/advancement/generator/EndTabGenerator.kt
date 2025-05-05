package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.registry.RegistryWrapper
import java.util.*
import java.util.function.Consumer

object EndTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "end"

    const val DRAGON2_0 = "dragon_2_0"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, DRAGON2_0) {
            parent(reference("minecraft:end/respawn_dragon"))
            display {
                icon = Items.END_CRYSTAL.defaultStack.apply {
                    addEnchantment(wrapperLookup.getEntryOrThrow(Enchantments.BLAST_PROTECTION), 1)
                }
                frame = AdvancementFrame.GOAL
            }
            criterion("10", Criteria.SPAWN_DRAGON_WITH_CRYSTALS.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(NumberRange.IntRange.atLeast(10)))))
        }.also(consumer::accept)
    }
}