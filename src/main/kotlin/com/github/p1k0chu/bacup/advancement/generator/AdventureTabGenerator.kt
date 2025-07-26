package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.*
import com.github.p1k0chu.bacup.advancement.getPlayerHead
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicate
import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.component.DataComponentTypes
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.item.map.MapState
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.component.ComponentsPredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.EntityTypePredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryKey
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
    const val MAXIMUM_COVERAGE = "maximum_coverage"
    const val PAINT_IT_RED = "paint_it_red"
    const val SECRET_SUPPLIES = "secret_supplies"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        val catGift = advancement(TAB_NAME, CAT_GIFT) {
            parent(reference("blazeandcave:adventure/crazy_cat_lady"))
            display {
                // cat head; https://minecraft-heads.com/custom-heads/head/66218-cat-plushie-siamese
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU5MTFlMjRjODU1M2ZlYWQ1YTJmMGEwZWM1OWM0YWY2MmYxMjZhZTcwZDZiZWQyNjFhZWQ0Zjk5YzE0YjQ5MiJ9fX0=")
            }
            criterion("gift", Criteria.CAT_GIFT_RECEIVED.create(SingleItemCriterion.Conditions()))
        }.also(consumer::accept)

        val plethoraOfCats = advancement(TAB_NAME, PLETHORA_OF_CATS) {
            parent(catGift)
            display {
                frame = AdvancementFrame.GOAL
                icon = Items.COD.defaultStack
            }
            criterion(
                "20", Criteria.PET_TAMED.create(
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
                    it.registryEntry.idAsString, Criteria.CAT_GIFT_RECEIVED.create(
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
            criterion("lock", Criteria.MAP_LOCKED.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, GET_RAID_OF_IT) {
            parent(reference("blazeandcave:adventure/were_being_attacked"))
            display {
                icon = Items.MILK_BUCKET.defaultStack
            }
            criterion("get_raid_of_it", Criteria.GET_RAID_OF_IT.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, CAN_YOU_HEAR_IT_FROM_HERE) {
            parent(reference("blazeandcave:adventure/oh_look_it_dings"))
            display {
                icon = Items.ARROW.defaultStack
                frame = AdvancementFrame.CHALLENGE
            }
            criterion(
                "50", Criteria.BELL_SHOT_FROM_DISTANCE.create(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(NumberRange.IntRange.atLeast(50))
                    )
                )
            )
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
                    item.registryEntry.idAsString, Criteria.SUS_BLOCK_GOT_ITEM.create(
                        SingleItemCriterion.Conditions.items(wrapperLookup, item)
                    )
                )
            }
        }.also(consumer::accept)

        advancement(TAB_NAME, MAXIMUM_COVERAGE) {
            parent(reference("blazeandcave:adventure/mapmakers_table"))
            display {
                icon = Items.FILLED_MAP.defaultStack
            }
            criterion(
                "max_coverage", InventoryChangedCriterion.Conditions.items(
                    ItemPredicate.Builder.create().components(
                        ComponentsPredicate.Builder.create().partial(
                            Main.mapStatePredicate,
                            MapStatePredicate(scale = NumberRange.IntRange.exactly(MapState.MAX_SCALE))
                        )
                            .build()
                    ).build()
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, PAINT_IT_RED) {
            parent(reference("blazeandcave:adventure/mapmaker"))
            display {
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ4NjM2MmQwZjY4OTVkOWMyMmNlN2JmNjRkODU3Mjc2MDFiNWQ5ODNmZmM1YTUzZmE1YmYzYjQ3OTRkMWZkMSJ9fX0=")
                frame = AdvancementFrame.CHALLENGE
            }
            criterion(
                "paint_it_red", InventoryChangedCriterion.Conditions.items(
                    ItemPredicate.Builder.create()
                        .components(
                            ComponentsPredicate.Builder.create()
                                .partial(
                                    Main.mapStatePredicate,
                                    MapStatePredicate(
                                        colors = Optional.of(
                                            MapColorPredicate.AllSame(
                                                listOf(
                                                    // FIRE: tnt, lava, fire, redstone block
                                                    NumberRange.IntRange.between(16, 19),
                                                    // COLOR_RED
                                                    NumberRange.IntRange.between(112, 115)
                                                )
                                            )
                                        )
                                    )
                                )
                                .build()
                        )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, SECRET_SUPPLIES) {
            parent(reference("blazeandcave:adventure/shady_deals"))

            display {
                icon = Items.DEAD_BUSH.defaultStack
                frame = AdvancementFrame.GOAL
            }

            criterion("milk_bucket", Criteria.WANDERING_TRADER_DROPPED_ITEM.create(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(EntityPredicate.Builder.create()
                        .type(wrapperLookup.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                        .build()),
                    Optional.of(ItemPredicate.Builder.create()
                        .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.MILK_BUCKET)
                        .build())
                )
            ))

            // don't check if it's invisibility cuz pointless
            criterion("potion", Criteria.WANDERING_TRADER_DROPPED_ITEM.create(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(EntityPredicate.Builder.create()
                        .type(wrapperLookup.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                        .build()),
                    Optional.of(ItemPredicate.Builder.create()
                        .items(wrapperLookup.getOrThrow(RegistryKeys.ITEM), Items.POTION)
                        .build())
                )
            ))
        }.let(consumer::accept)
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