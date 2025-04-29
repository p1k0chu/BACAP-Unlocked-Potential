package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.FurnaceCookedWithFuelCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.*
import java.util.function.Consumer

object MiningTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "mining"

    const val LEAFTERALLY = "leafterally"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, LEAFTERALLY) {
            parent(reference("blazeandcave:mining/renewable_energy"))
            display {
                icon = Items.LEAF_LITTER.defaultStack
            }
            criterion(
                "fuel", Main.COOKED_WITH_FUEL.create(
                    FurnaceCookedWithFuelCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            ItemPredicate.Builder.create()
                                .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.LEAF_LITTER).build()),
                        Optional.of(
                            ItemPredicate.Builder.create()
                                .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.LEAF_LITTER).build()))))
        }.also(consumer::accept)
    }
}