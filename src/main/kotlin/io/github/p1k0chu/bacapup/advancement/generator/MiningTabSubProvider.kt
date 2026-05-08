package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.FurnaceCookedWithFuelTrigger
import net.minecraft.advancements.predicates.ItemPredicate
import net.minecraft.advancements.predicates.entity.EntityFlagsPredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object MiningTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "mining"

    const val LEAFTERALLY = "leafterally"
    const val RAGE_BAITER = "rage_baiter"
    const val ANTI_MITOSIS = "anti_mitosis"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, LEAFTERALLY) {
            parent(createPlaceholder("blazeandcave:mining/renewable_energy"))
            display {
                title = "Leafterally"
                description = "Smelt leaf litter using leaf litter"
                icon = ItemStackTemplate(Items.LEAF_LITTER)
            }
            addCriterion(
                "fuel", BacapupTriggers.COOKED_WITH_FUEL.createCriterion(
                    FurnaceCookedWithFuelTrigger.Instance(
                        Optional.empty(),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.LEAF_LITTER).build()
                        ),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.LEAF_LITTER).build()
                        )
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, RAGE_BAITER) {
            parent(createPlaceholder("blazeandcave:mining/shriek_forever_after"))
            display {
                title = "Rage Baiter"
                description = "Fish warden several times for its angry value go from 0 to 150"
                icon = ItemStackTemplate(Items.FISHING_ROD)
                type = AdvancementType.GOAL
            }
            addCriterion("rage_bait", impossibleTrigger())
        }

        advancement(consumer, TAB_NAME, ANTI_MITOSIS) {
            parent(createPlaceholder("blazeandcave:mining/mitosis"))
            display {
                title = "Anti-Mitosis"
                description = "Have a Sulfur Cube kill a young one of its own kind"
                icon = ItemStackTemplate(Items.MAGMA_BLOCK)
                type = AdvancementType.GOAL
            }
            addCriterion(
                "the_trigger_yeah_just",
                BacapupTriggers.SULFUR_CUBE_CONTACT_KILL.entity(
                    EntityPredicate.Builder.entity()
                        .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypes.SULFUR_CUBE)
                        .flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true))
                        .build()
                )
            )
        }

    }
}
