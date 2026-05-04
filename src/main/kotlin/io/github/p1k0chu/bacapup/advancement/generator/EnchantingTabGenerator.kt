package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementGenerator
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.criteria.Criteria
import io.github.p1k0chu.bacapup.advancement.criteria.EmptyCriterion
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
