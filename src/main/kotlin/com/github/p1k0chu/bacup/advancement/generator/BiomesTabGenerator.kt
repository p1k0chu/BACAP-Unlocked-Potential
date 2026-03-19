package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.*
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items

object BiomesTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "biomes"

    const val CONDUEL = "conduel"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: AdvancementConsumer
    ) {
        advancement(consumer, TAB_NAME, CONDUEL) {
            parent(createPlaceholder("blazeandcave:biomes/moskstraumen"))
            display {
                title = "Conduel"
                description = "Make conduit kill a mob"
                icon = Items.CONDUIT.defaultInstance.apply {
                    set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                type = AdvancementType.GOAL
            }

            addCriterion("conduel", impossibleTrigger())
        }
    }
}
