package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.impossibleTrigger
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import java.util.function.Consumer

object BiomesTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "biomes"

    const val CONDUEL = "conduel"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: Consumer<AdvancementHolder>
    ) {
        advancement(TAB_NAME, CONDUEL) {
            parent(createPlaceholder("blazeandcave:biomes/moskstraumen"))
            display {
                icon = Items.CONDUIT.defaultInstance.apply {
                    set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                frame = AdvancementType.GOAL
            }

            addCriterion("conduel", impossibleTrigger())
        }.also(consumer::accept)
    }
}