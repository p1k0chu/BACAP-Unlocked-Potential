package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.SingleIntRangeTrigger
import net.minecraft.advancements.predicates.MinMaxBounds
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object EndTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "end"

    const val DRAGON2_0 = "dragon_2_0"
    const val INTENTIONAL_ADVANCEMENT_DESIGN = "intentional_advancement_design"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, DRAGON2_0) {
            parent(createPlaceholder("minecraft:end/respawn_dragon"))
            display {
                title = "Dragon 2.0"
                description = "Summon a \"stronger\" dragon using 10 ender crystals"
                icon = ItemStackTemplate(
                    Items.END_CRYSTAL,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStackTemplate(Items.END_CRYSTAL, 2))

            addCriterion("10", BacapupTriggers.SPAWN_DRAGON_WITH_CRYSTALS.createCriterion(
                SingleIntRangeTrigger.Instance(
                    Optional.empty(),
                    Optional.of(MinMaxBounds.Ints.atLeast(10)))))
        }

        advancement(consumer, TAB_NAME, INTENTIONAL_ADVANCEMENT_DESIGN) {
            parent(createPlaceholder("blazeandcave:end/the_actual_end"))
            display {
                title = "Intentional Advancement Design"
                description = "Try to sleep outside of overworld" // and explode :3
                icon = ItemStackTemplate(Items.BED.red)
            }
            addCriterion("die", impossibleTrigger())
        }
    }
}
