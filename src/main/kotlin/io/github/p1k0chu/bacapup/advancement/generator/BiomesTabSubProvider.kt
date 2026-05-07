package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.constants.ParrotConstants
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.predicates.entity.EntityTypePredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

object BiomesTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "biomes"

    const val CONDUEL = "conduel"
    const val POLYGLOT = "polyglot"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: AdvancementConsumer
    ) {
        advancement(consumer, TAB_NAME, CONDUEL) {
            parent(createPlaceholder("blazeandcave:biomes/moskstraumen"))
            display {
                title = "Conduel"
                description = "Make conduit kill a mob"
                icon = ItemStackTemplate(
                    Items.CONDUIT,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.GOAL
            }

            addCriterion("conduel", impossibleTrigger())
        }

        advancement(consumer, AnimalsTabSubProvider.TAB_NAME, POLYGLOT) {
            parent(createPlaceholder("blazeandcave:biomes/birdkeeper"))
            display {
                title = "Pollyglot"
                description = "Hear your parrots speak like every mob"
                icon = ItemStackTemplate(Items.WRITABLE_BOOK)
                type = AdvancementType.CHALLENGE
            }

            for (mobType in ParrotConstants.IMITATING_TYPES) {
                addCriterion(
                    BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(mobType).registeredName,
                    BacapupTriggers.PARROT_IMITATES.entityType(
                        EntityTypePredicate.of(provider.lookupOrThrow(Registries.ENTITY_TYPE), mobType)
                    )
                )
            }
        }
    }
}
