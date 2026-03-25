package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

object EnchantingTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "enchanting"

    const val DISENCHANTED = "disenchanted"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, DISENCHANTED) {
            parent(createPlaceholder("blazeandcave:enchanting/heavy_metal"))
            display {
                title = "Disenchanted"
                description = "Use a Grindstone to get experience from an enchanted item."
                icon = ItemStackTemplate(Items.GRINDSTONE)
            }
            addCriterion("disenchanted", Criteria.DISENCHANT_GRINDSTONE.createCriterion(EmptyCriterion.Conditions()))
        }
    }
}
