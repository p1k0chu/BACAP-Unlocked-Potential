package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementSubProvider
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.impossibleTrigger
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

object RedstoneTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "redstone"

    const val SMELT_EVERYTHING = "smelt_everything"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: AdvancementConsumer
    ) {
        advancement(consumer, TAB_NAME, SMELT_EVERYTHING) {
            parent(createPlaceholder("blazeandcave:redstone/space_hopper"))
            display {
                title = "Smelt Everything"
                description = "Connect 3 Chests to a single Furnace using 3 Hoppers."
                icon = ItemStackTemplate(Items.FURNACE)
            }
            addCriterion("open_it", impossibleTrigger())
        }
    }
}
