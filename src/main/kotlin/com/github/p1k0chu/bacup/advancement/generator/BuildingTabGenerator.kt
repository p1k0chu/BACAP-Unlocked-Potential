package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleBlockCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.function.Consumer

object BuildingTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "building"

    const val FIRE_TRICK = "fire_trick"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, FIRE_TRICK) {
            parent(createPlaceholder("blazeandcave:building/happy_birthday"))
            display {
                icon = Items.BOW.defaultInstance
            }
            addCriterion(
                "candle", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(wrapperLookup, BlockTags.CANDLES)
                )
            )
            addCriterion(
                "candle_cake", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(wrapperLookup, BlockTags.CANDLE_CAKES)
                )
            )
            addCriterion(
                "campfire", Criteria.PROJECTILE_LIT_BLOCK.createCriterion(
                    SingleBlockCriterion.Conditions.tags(wrapperLookup, BlockTags.CAMPFIRES)
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
        }.also(consumer::accept)
    }
}