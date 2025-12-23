package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.EntityDroppedLootCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import net.minecraft.tags.EntityTypeTags
import java.util.*
import java.util.function.Consumer

object MonstersTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "monsters"

    const val BEAM_ME_UP = "beam_me_up"
    const val FORGED_BY_FLESH = "forged_by_flesh"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, BEAM_ME_UP) {
            parent(createPlaceholder("blazeandcave:monsters/tele_morph"))
            display {
                icon = Items.ENDER_PEARL.defaultInstance
                frame = AdvancementType.GOAL
            }
            addCriterion("beam_me_up", Criteria.BEAM_ME_UP.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, FORGED_BY_FLESH) {
            parent(createPlaceholder("blazeandcave:monsters/zombie_slayer"))
            display {
                icon = Items.IRON_INGOT.defaultInstance
                frame = AdvancementType.GOAL
            }
            addCriterion("iron_ingot", Criteria.ENTITY_DROPPED_LOOT.createCriterion(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.entity()
                        .of(wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.ZOMBIES)
                        .build()),
                    Optional.of(
                        ItemPredicate.Builder.item()
                        .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.IRON_INGOT).build())
                )
            ))
        }.also(consumer::accept)
    }
}