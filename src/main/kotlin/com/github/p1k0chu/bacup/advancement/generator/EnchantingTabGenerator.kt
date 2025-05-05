package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object EnchantingTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "enchanting"

    const val DISENCHANTED = "disenchanted"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, DISENCHANTED) {
            parent(reference("blazeandcave:enchanting/heavy_metal"))
            display {
                icon = Items.GRINDSTONE.defaultStack
            }
            criterion("disenchanted", Criteria.DISENCHANT_GRINDSTONE.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}