package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.*
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.FurnaceCookedWithFuelCriterion
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import java.util.*

object MiningTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "mining"

    const val LEAFTERALLY = "leafterally"
    const val RAGE_BAITER = "rage_baiter"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, LEAFTERALLY) {
            parent(createPlaceholder("blazeandcave:mining/renewable_energy"))
            display {
                title = "Leafterally"
                description = "Smelt leaf litter using leaf litter"
                icon = Items.LEAF_LITTER.defaultInstance
            }
            addCriterion(
                "fuel", Criteria.COOKED_WITH_FUEL.createCriterion(
                    FurnaceCookedWithFuelCriterion.Conditions(
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
                icon = Items.FISHING_ROD.defaultInstance
                type = AdvancementType.GOAL
            }
            addCriterion("rage_bait", impossibleTrigger())
        }
    }
}
