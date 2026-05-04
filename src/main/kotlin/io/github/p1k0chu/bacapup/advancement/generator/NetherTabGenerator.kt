package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementGenerator
import io.github.p1k0chu.bacapup.advancement.AdvancementType
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.criteria.Criteria
import io.github.p1k0chu.bacapup.advancement.criteria.EmptyCriterion
import io.github.p1k0chu.bacapup.advancement.criteria.SingleItemCriterion
import io.github.p1k0chu.bacapup.utils.setCustomName
import io.github.p1k0chu.bacapup.utils.setLore
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

object NetherTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "nether"

    const val EXTREME_BREAK_RISK = "extreme_break_risk"
    const val BEACON_ALL_ITEMS = "beacon_all_items"

    override fun generate(
        provider: HolderLookup.Provider,
        consumer: AdvancementConsumer
    ) {
        advancement(consumer, TAB_NAME, EXTREME_BREAK_RISK) {
            parent(createPlaceholder("minecraft:nether/netherite_armor"))
            display {
                title = "Extreme Break Risk"
                description = "Break a block of netherite with your bare fist."
                icon = ItemStackTemplate(Items.NETHERITE_BLOCK)
                type = AdvancementType.CHALLENGE
            }
            trophy = ItemStackTemplate(
                Items.POLISHED_BLACKSTONE,
                DataComponentPatch.builder()
                    .setCustomName("Catharsis")
                    .setLore("The Last Straw Was: Ate Without Table")
                    .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .build()
            )

            addCriterion(
                "netherite_block", Criteria.PLAYER_BREAK_NETHERITE_BLOCK_WITH_FIST.createCriterion(
                    EmptyCriterion.Conditions()
                )
            )
        }

        advancement(consumer, TAB_NAME, BEACON_ALL_ITEMS) {
            parent(createPlaceholder("minecraft:nether/create_beacon"))
            display {
                title = "Thanos Beacon"
                description = "Power a beacon with every mineral"
                icon = ItemStackTemplate(Items.IRON_INGOT)
                type = AdvancementType.GOAL
            }
            listOf(
                Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.EMERALD, Items.NETHERITE_INGOT
            ).forEach { item ->
                addCriterion(
                    item.builtInRegistryHolder().registeredName, Criteria.BEACON_CONSUMED_PAYMENT.createCriterion(
                        SingleItemCriterion.Conditions.items(provider, item)
                    )
                )
            }
        }
    }
}
