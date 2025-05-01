package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.SingleBlockCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementRequirements
import net.minecraft.block.Blocks
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import java.util.function.Consumer

object BuildingTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "building"

    const val FIRE_TRICK = "fire_trick"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, FIRE_TRICK) {
            parent(reference("blazeandcave:building/happy_birthday"))
            display {
                icon = Items.BOW.defaultStack
            }
            criterion(
                "candle", Main.PROJECTILE_LIT_BLOCK.create(
                    SingleBlockCriterion.Conditions.tags(wrapperLookup, BlockTags.CANDLES)
                )
            )
            criterion(
                "candle_cake", Main.PROJECTILE_LIT_BLOCK.create(
                    SingleBlockCriterion.Conditions.tags(wrapperLookup, BlockTags.CANDLE_CAKES)
                )
            )
            criterion(
                "campfire", Main.PROJECTILE_LIT_BLOCK.create(
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