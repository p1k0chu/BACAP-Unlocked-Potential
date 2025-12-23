package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.core.HolderLookup
import java.util.function.Consumer

object EnchantingTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "enchanting"

    const val DISENCHANTED = "disenchanted"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, DISENCHANTED) {
            parent(createPlaceholder("blazeandcave:enchanting/heavy_metal"))
            display {
                icon = Items.GRINDSTONE.defaultInstance
            }
            addCriterion("disenchanted", Criteria.DISENCHANT_GRINDSTONE.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}