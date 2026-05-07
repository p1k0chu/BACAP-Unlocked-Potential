package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.FurnaceCookedWithFuelTrigger
import net.minecraft.advancements.predicates.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object MiningTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "mining"

    const val LEAFTERALLY = "leafterally"
    const val RAGE_BAITER = "rage_baiter"

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
    }
}
