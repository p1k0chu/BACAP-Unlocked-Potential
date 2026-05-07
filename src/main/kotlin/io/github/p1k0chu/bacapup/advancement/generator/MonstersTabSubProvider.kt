package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementSubProvider
import io.github.p1k0chu.bacapup.advancement.AdvancementType
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.EmptyTrigger
import io.github.p1k0chu.bacapup.advancement.triggers.EntityDroppedLootTrigger
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.predicates.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object MonstersTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "monsters"

    const val BEAM_ME_UP = "beam_me_up"
    const val FORGED_BY_FLESH = "forged_by_flesh"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, BEAM_ME_UP) {
            parent(createPlaceholder("blazeandcave:monsters/tele_morph"))
            display {
                title = "Beam Me Up"
                description = "Teleport over 100 meters from a single throw of an Ender Pearl"
                icon = ItemStackTemplate(Items.ENDER_PEARL)
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStackTemplate(Items.ENDER_PEARL, 1))

            addCriterion("beam_me_up", BacapupTriggers.BEAM_ME_UP.createCriterion(EmptyTrigger.Instance()))
        }

        advancement(consumer, TAB_NAME, FORGED_BY_FLESH) {
            parent(createPlaceholder("blazeandcave:monsters/zombie_slayer"))
            display {
                title = "Forged by Flesh"
                description = "Retrieve an iron ingot from a defeated zombie"
                icon = ItemStackTemplate(Items.IRON_INGOT)
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStackTemplate(Items.IRON_INGOT, 1))

            addCriterion("iron_ingot", BacapupTriggers.ENTITY_DROPPED_LOOT.createCriterion(
                EntityDroppedLootTrigger.Instance(
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
