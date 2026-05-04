package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementGenerator
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.criteria.Criteria
import io.github.p1k0chu.bacapup.advancement.criteria.SingleBlockCriterion
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

object BuildingTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "building"

    const val FIRE_TRICK = "fire_trick"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, FIRE_TRICK) {
            parent(createPlaceholder("blazeandcave:building/happy_birthday"))
            display {
                title = "Fire Trick"
                description = "Light a campfire and a candle by shooting an arrow at them"
                icon = ItemStackTemplate(Items.BOW)
            }
            addCriterion(
                "candle", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(provider, BlockTags.CANDLES)
                )
            )
            addCriterion(
                "candle_cake", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(provider, BlockTags.CANDLE_CAKES)
                )
            )
            addCriterion(
                "campfire", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(provider, BlockTags.CAMPFIRES)
                )
            )

            requirements(
                AdvancementRequirements(
                    listOf(
                        listOf("candle", "candle_cake"),
                        listOf("campfire")
                    )
                )
            )
        }
    }
}
