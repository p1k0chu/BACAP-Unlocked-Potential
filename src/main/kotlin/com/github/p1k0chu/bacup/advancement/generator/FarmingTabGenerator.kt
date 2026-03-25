package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleItemCriterion
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object FarmingTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "farming"

    const val ALTERNATIVE_FUEL = "alternative_fuel"
    const val SUPER_FUEL = "super_fuel"
    const val TRASH_BIN = "trash_bin"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        val altFuel = advancement(consumer, TAB_NAME, ALTERNATIVE_FUEL) {
            parent(createPlaceholder("blazeandcave:farming/aquatic_biofuel"))
            display {
                title = "Alternative Fuel"
                description = "Power a furnace with a kelp block"
                icon = ItemStackTemplate(Items.DRIED_KELP_BLOCK)
            }
            itemReward(ItemStackTemplate(Items.DRIED_KELP_BLOCK, 1))

            addCriterion(
                "fuel", Criteria.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.DRIED_KELP_BLOCK)
                                .build()))))
        }

        advancement(consumer, TAB_NAME, SUPER_FUEL) {
            parent(altFuel)
            display {
                title = "Super Fuel"
                description = "Power a furnace with lava"
                icon = ItemStackTemplate(Items.LAVA_BUCKET)
            }
            addCriterion(
                "fuel", Criteria.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.LAVA_BUCKET)
                                .build()))))
        }

        advancement(consumer, TAB_NAME, TRASH_BIN) {
            parent(createPlaceholder("blazeandcave:farming/spikey"))
            display {
                title = "Trash Bin"
                description = "Throw an item into a cactus"
                icon = ItemStackTemplate(Items.COMPOSTER)
            }
            addCriterion("trash_bin", Criteria.CACTUS_DESTROY_ITEM.createCriterion(SingleItemCriterion.Conditions()))
        }
    }
}
