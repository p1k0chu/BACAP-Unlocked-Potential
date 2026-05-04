package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
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
                icon = ItemStackTemplate(
                    Items.CONDUIT,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.GOAL
            }

            addCriterion("conduel", impossibleTrigger())
        }
    }
}
