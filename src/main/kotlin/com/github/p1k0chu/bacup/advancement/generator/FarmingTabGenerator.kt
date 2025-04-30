package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.SingleItemCriterion
import com.github.p1k0chu.bacup.advancement.getPlayerHead
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.component.DataComponentTypes
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.text.Text
import java.util.*
import java.util.function.Consumer

object FarmingTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "farming"

    const val ALTERNATIVE_FUEL = "alternative_fuel"
    const val SUPER_FUEL = "super_fuel"
    const val TRASH_BIN = "trash_bin"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        val altFuel = advancement(TAB_NAME, ALTERNATIVE_FUEL) {
            parent(reference("blazeandcave:farming/aquatic_biofuel"))
            display {
                icon = Items.DRIED_KELP_BLOCK.defaultStack
            }
            criterion(
                "fuel", Main.FURNACE_FUEL_CONSUMED.create(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), Optional.of(
                            ItemPredicate.Builder.create()
                                .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.DRIED_KELP_BLOCK)
                                .build()))))
        }.also(consumer::accept)

        advancement(TAB_NAME, SUPER_FUEL) {
            parent(altFuel)
            display {
                icon = Items.LAVA_BUCKET.defaultStack
            }
            criterion(
                "fuel", Main.FURNACE_FUEL_CONSUMED.create(
                    SingleItemCriterion.Conditions(
                        Optional.empty(), Optional.of(
                            ItemPredicate.Builder.create()
                                .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.LAVA_BUCKET)
                                .build()))))
        }.also(consumer::accept)

        advancement(TAB_NAME, TRASH_BIN) {
            parent(reference("blazeandcave:farming/spikey"))
            display {
                icon = Items.COMPOSTER.defaultStack
            }
            criterion("trash_bin", Main.CACTUS_DESTROY_ITEM.create(SingleItemCriterion.Conditions()))
        }.also(consumer::accept)
    }
}
