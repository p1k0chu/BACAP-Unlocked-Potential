package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.*
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import java.util.*

object EndTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "end"

    const val DRAGON2_0 = "dragon_2_0"
    const val INTENTIONAL_ADVANCEMENT_DESIGN = "intentional_advancement_design"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, DRAGON2_0) {
            parent(createPlaceholder("minecraft:end/respawn_dragon"))
            display {
                title = "Dragon 2.0"
                description = "Summon a \"stronger\" dragon using 10 ender crystals"
                icon = Items.END_CRYSTAL.defaultInstance.apply {
                    enchant(provider.getOrThrow(Enchantments.BLAST_PROTECTION), 1)
                }
                type = AdvancementType.GOAL
            }
            exp = 50
            itemReward(ItemStack(Items.END_CRYSTAL, 2))

            addCriterion("10", Criteria.SPAWN_DRAGON_WITH_CRYSTALS.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(MinMaxBounds.Ints.atLeast(10)))))
        }

        advancement(consumer, TAB_NAME, INTENTIONAL_ADVANCEMENT_DESIGN) {
            parent(createPlaceholder("blazeandcave:end/the_actual_end"))
            display {
                title = "Intentional Advancement Design"
                description = "Try to sleep outside of overworld" // and explode :3
                icon = Items.RED_BED.defaultInstance
            }
            addCriterion("die", impossibleTrigger())
        }
    }
}
