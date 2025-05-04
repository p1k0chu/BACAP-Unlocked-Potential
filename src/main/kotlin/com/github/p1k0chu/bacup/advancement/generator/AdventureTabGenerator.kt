package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.PetTamedCriterion
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import com.github.p1k0chu.bacup.advancement.criteria.SingleItemCriterion
import com.github.p1k0chu.bacup.advancement.getPlayerHead
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.component.DataComponentTypes
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.EntityTypePredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.*
import java.util.function.Consumer

object AdventureTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "adventure"

    const val CAT_GIFT = "cat_gift"
    const val ALL_CAT_GIFTS = "all_cat_gifts"
    const val LOCK_MAP = "lock_map"
    const val PLETHORA_OF_CATS = "plethora_of_cats"
    const val GET_RAID_OF_IT = "get_raid_of_it"
    const val CAN_YOU_HEAR_IT_FROM_HERE = "can_you_hear_it_from_here"
    const val MASTER_ARCHEOLOGIST = "master_archaeologist"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        val catGift = advancement(TAB_NAME, CAT_GIFT) {
            parent(reference("blazeandcave:adventure/crazy_cat_lady"))
            display {
                // cat head; https://minecraft-heads.com/custom-heads/head/66218-cat-plushie-siamese
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU5MTFlMjRjODU1M2ZlYWQ1YTJmMGEwZWM1OWM0YWY2MmYxMjZhZTcwZDZiZWQyNjFhZWQ0Zjk5YzE0YjQ5MiJ9fX0=")
            }
            criterion("gift", Main.CAT_GIFT_RECEIVED.create(SingleItemCriterion.Conditions()))
        }.also(consumer::accept)

        val plethoraOfCats = advancement(TAB_NAME, PLETHORA_OF_CATS) {
            parent(catGift)
            display {
                frame = AdvancementFrame.GOAL
                icon = Items.COD.defaultStack
            }
            criterion(
                "20", Main.PET_TAMED.create(
                    PetTamedCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.create().type(
                                EntityTypePredicate.create(
                                    wrapperLookup.getOrThrow(RegistryKeys.ENTITY_TYPE),
                                    EntityType.CAT
                                )
                            ).build()
                        ),
                        Optional.of(NumberRange.IntRange.atLeast(20)),
                    )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, ALL_CAT_GIFTS) {
            parent(plethoraOfCats)
            display {
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
                criterion(
                    it.registryEntry.idAsString, Main.CAT_GIFT_RECEIVED.create(
                        SingleItemCriterion.Conditions.items(wrapperLookup, it)
                    )
                )
            }
        }.also(consumer::accept)

        advancement(TAB_NAME, LOCK_MAP) {
            parent(reference("blazeandcave:adventure/mapmakers_table"))
            display {
                icon = Items.GLASS_PANE.defaultStack
            }
            criterion("lock", Main.MAP_LOCKED.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, GET_RAID_OF_IT) {
            parent(reference("blazeandcave:adventure/were_being_attacked"))
            display {
                icon = Items.MILK_BUCKET.defaultStack
            }
            criterion("get_raid_of_it", Main.GET_RAID_OF_IT.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, CAN_YOU_HEAR_IT_FROM_HERE) {
            parent(reference("blazeandcave:adventure/oh_look_it_dings"))
            display {
                icon = Items.ARROW.defaultStack
                frame = AdvancementFrame.CHALLENGE
            }
            criterion("50", Main.BELL_SHOT_FROM_DISTANCE.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(NumberRange.IntRange.atLeast(50)))))
        }.also(consumer::accept)

        advancement(TAB_NAME, MASTER_ARCHEOLOGIST) {
            parent(reference("blazeandcave:adventure/a_suspicious_advancement"))
            display {
                icon = Items.BRUSH.defaultStack.apply {
                    set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                frame = AdvancementFrame.CHALLENGE
                hidden = true
            }

            susLoot.forEach { item ->
                criterion(
                    item.registryEntry.idAsString, Main.SUS_BLOCK_GOT_ITEM.create(
                        SingleItemCriterion.Conditions.items(wrapperLookup, item)
                    )
                )
            }
        }.also(consumer::accept)
    }

    private val susLoot = listOf(
        Items.ARMS_UP_POTTERY_SHERD,
        Items.BREWER_POTTERY_SHERD,
        Items.STICK,
        Items.SUSPICIOUS_STEW,
        Items.CLAY,
        Items.BRICK,
        Items.YELLOW_DYE,
        Items.BLUE_DYE,
        Items.LIGHT_BLUE_DYE,
        Items.WHITE_DYE,
        Items.ORANGE_DYE,
        Items.RED_CANDLE,
        Items.GREEN_CANDLE,
        Items.PURPLE_CANDLE,
        Items.BROWN_CANDLE,
        Items.MAGENTA_STAINED_GLASS_PANE,
        Items.PINK_STAINED_GLASS_PANE,
        Items.BLUE_STAINED_GLASS_PANE,
        Items.LIGHT_BLUE_STAINED_GLASS_PANE,
        Items.RED_STAINED_GLASS_PANE,
        Items.YELLOW_STAINED_GLASS_PANE,
        Items.PURPLE_STAINED_GLASS_PANE,
        Items.SPRUCE_HANGING_SIGN,
        Items.OAK_HANGING_SIGN,
        Items.WHEAT_SEEDS,
        Items.BEETROOT_SEEDS,
        Items.DEAD_BUSH,
        Items.FLOWER_POT,
        Items.STRING,
        Items.LEAD,
        Items.BURN_POTTERY_SHERD,
        Items.DANGER_POTTERY_SHERD,
        Items.FRIEND_POTTERY_SHERD,
        Items.HEART_POTTERY_SHERD,
        Items.HEARTBREAK_POTTERY_SHERD,
        Items.HOWL_POTTERY_SHERD,
        Items.SHEAF_POTTERY_SHERD,
        Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
        Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
        Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
        Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
        Items.MUSIC_DISC_RELIC,
        Items.ANGLER_POTTERY_SHERD,
        Items.SHELTER_POTTERY_SHERD,
        Items.SNORT_POTTERY_SHERD,
        Items.SNIFFER_EGG,
        Items.BLADE_POTTERY_SHERD,
        Items.EXPLORER_POTTERY_SHERD,
        Items.MOURNER_POTTERY_SHERD,
        Items.PLENTY_POTTERY_SHERD,
        Items.IRON_AXE,
        Items.EMERALD,
        Items.WHEAT,
        Items.WOODEN_HOE,
        Items.COAL,
        Items.GOLD_NUGGET
    )
}