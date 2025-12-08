package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleItemCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object FarmingTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "farming"

    const val ALTERNATIVE_FUEL = "alternative_fuel"
    const val SUPER_FUEL = "super_fuel"
    const val TRASH_BIN = "trash_bin"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        val altFuel = advancement(TAB_NAME, ALTERNATIVE_FUEL) {
            parent(createPlaceholder("blazeandcave:farming/aquatic_biofuel"))
            display {
                icon = Items.DRIED_KELP_BLOCK.defaultInstance
            }
            addCriterion(
                "fuel", Criteria.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.DRIED_KELP_BLOCK)
                                .build()))))
        }.also(consumer::accept)

        advancement(TAB_NAME, SUPER_FUEL) {
            parent(altFuel)
            display {
                icon = Items.LAVA_BUCKET.defaultInstance
            }
            addCriterion(
                "fuel", Criteria.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.LAVA_BUCKET)
                                .build()))))
        }.also(consumer::accept)

        advancement(TAB_NAME, TRASH_BIN) {
            parent(createPlaceholder("blazeandcave:farming/spikey"))
            display {
                icon = Items.COMPOSTER.defaultInstance
            }
            addCriterion("trash_bin", Criteria.CACTUS_DESTROY_ITEM.createCriterion(SingleItemCriterion.Conditions()))
        }.also(consumer::accept)
    }
}
