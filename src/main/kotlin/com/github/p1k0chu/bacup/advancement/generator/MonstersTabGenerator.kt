package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.EntityDroppedLootCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.EntityTypeTags
import java.util.*
import java.util.function.Consumer

object MonstersTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "monsters"

    const val BEAM_ME_UP = "beam_me_up"
    const val FORGED_BY_FLESH = "forged_by_flesh"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, BEAM_ME_UP) {
            parent(reference("blazeandcave:monsters/tele_morph"))
            display {
                icon = Items.ENDER_PEARL.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("beam_me_up", Main.BEAM_ME_UP.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, FORGED_BY_FLESH) {
            parent(reference("blazeandcave:monsters/zombie_slayer"))
            display {
                icon = Items.IRON_INGOT.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("iron_ingot", Main.ENTITY_DROPPED_LOOT.create(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(EntityPredicate.Builder.create()
                        .type(wrapperLookup.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityTypeTags.ZOMBIES)
                        .build()),
                    Optional.of(ItemPredicate.Builder.create()
                        .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.IRON_INGOT).build())
                )
            ))
        }.also(consumer::accept)
    }
}