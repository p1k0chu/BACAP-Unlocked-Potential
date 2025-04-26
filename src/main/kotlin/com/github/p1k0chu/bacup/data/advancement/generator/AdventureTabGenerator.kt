package com.github.p1k0chu.bacup.data.advancement.generator

import com.github.p1k0chu.bacup.advancement.criteria.CatGiftReceivedCriterion
import com.github.p1k0chu.bacup.data.language.description
import com.github.p1k0chu.bacup.data.language.title
import com.github.p1k0chu.bacup.utils.*
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object AdventureTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "adventure"

    const val CAT_GIFT = "cat_gift"
    const val ALL_CAT_GIFTS = "all_cat_gifts"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        val catGift = advancement(id(TAB_NAME, CAT_GIFT)) {
            parent(reference("blazeandcave:adventure/crazy_cat_lady"))
            display {
                title = title(TAB_NAME, CAT_GIFT)
                description = description(TAB_NAME, CAT_GIFT)
                // cat head; https://minecraft-heads.com/custom-heads/head/66218-cat-plushie-siamese
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU5MTFlMjRjODU1M2ZlYWQ1YTJmMGEwZWM1OWM0YWY2MmYxMjZhZTcwZDZiZWQyNjFhZWQ0Zjk5YzE0YjQ5MiJ9fX0=")
            }
            criterion("gift", CatGiftReceivedCriterion.Conditions.builder {})
        }.also(consumer::accept)

        advancement(id(TAB_NAME, ALL_CAT_GIFTS)) {
            parent(catGift)
            display {
                title = title(TAB_NAME, ALL_CAT_GIFTS)
                description = description(TAB_NAME, ALL_CAT_GIFTS)
                frame = AdvancementFrame.GOAL
                // cat king; https://minecraft-heads.com/custom-heads/head/104289-cat-king
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRiZTYzOTRjOTE1Y2FjMzY0Y2YwMTFkM2Y4Y2EzNmU2YjBlOTk4NjA4ZWJmNGJiZDMxMmUzMjQ3MWQwODZkIn19fQ==")
            }

            listOf(
                Items.RABBIT_FOOT,
                Items.RABBIT_HIDE,
                Items.STRING,
                Items.ROTTEN_FLESH,
                Items.FEATHER,
                Items.CHICKEN,
                Items.PHANTOM_MEMBRANE
            ).forEach {
                criterion(it.registryEntry.idAsString, CatGiftReceivedCriterion.Conditions.builder {
                    items(
                        ItemPredicate.Builder.create().items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), it).build()
                    )
                })
            }
        }.also(consumer::accept)
    }
}