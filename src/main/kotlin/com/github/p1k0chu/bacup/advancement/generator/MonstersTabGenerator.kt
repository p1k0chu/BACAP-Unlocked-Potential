package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.AdvancementType
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.EntityDroppedLootCriterion
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStack
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.item.Items
import java.util.*

object MonstersTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "monsters"

    const val BEAM_ME_UP = "beam_me_up"
    const val FORGED_BY_FLESH = "forged_by_flesh"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, BEAM_ME_UP) {
            parent(createPlaceholder("blazeandcave:monsters/tele_morph"))
            display {
                title = "Beam Me Up"
                description = "Teleport over 100 meters from a single throw of an Ender Pearl"
                icon = Items.ENDER_PEARL.defaultInstance
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStack(Items.ENDER_PEARL, 1))

            addCriterion("beam_me_up", Criteria.BEAM_ME_UP.createCriterion(EmptyCriterion.Conditions()))
        }

        advancement(consumer, TAB_NAME, FORGED_BY_FLESH) {
            parent(createPlaceholder("blazeandcave:monsters/zombie_slayer"))
            display {
                title = "Forged by Flesh"
                description = "Retrieve an iron ingot from a defeated zombie"
                icon = Items.IRON_INGOT.defaultInstance
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStack(Items.IRON_INGOT, 1))

            addCriterion("iron_ingot", Criteria.ENTITY_DROPPED_LOOT.createCriterion(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.entity()
                            .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.ZOMBIES)
                        .build()),
                    Optional.of(
                        ItemPredicate.Builder.item()
                            .of(provider.lookupOrThrow(Registries.ITEM), Items.IRON_INGOT).build()
                    )
                )
            ))
        }
    }
}
