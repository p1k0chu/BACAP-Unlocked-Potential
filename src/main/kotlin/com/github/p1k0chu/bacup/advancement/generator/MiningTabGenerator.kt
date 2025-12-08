package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.FurnaceCookedWithFuelCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object MiningTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "mining"

    const val LEAFTERALLY = "leafterally"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, LEAFTERALLY) {
            parent(createPlaceholder("blazeandcave:mining/renewable_energy"))
            display {
                icon = Items.LEAF_LITTER.defaultInstance
            }
            addCriterion(
                "fuel", Criteria.COOKED_WITH_FUEL.createCriterion(
                    FurnaceCookedWithFuelCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.LEAF_LITTER).build()),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.LEAF_LITTER).build()))))
        }.also(consumer::accept)
    }
}