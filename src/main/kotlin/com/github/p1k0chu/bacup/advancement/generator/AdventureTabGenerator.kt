package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.*
import com.github.p1k0chu.bacup.advancement.getPlayerHead
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicate
import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import net.minecraft.world.level.saveddata.maps.MapItemSavedData
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.advancements.criterion.DataComponentMatchers
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.EntityTypePredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object AdventureTabGenerator : AdvancementSubProvider {
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
    const val DEHYDRATION = "dehydration"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        val catGift = advancement(TAB_NAME, CAT_GIFT) {
            parent(createPlaceholder("blazeandcave:adventure/crazy_cat_lady"))
            display {
                // cat head; https://minecraft-heads.com/custom-heads/head/66218-cat-plushie-siamese
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU5MTFlMjRjODU1M2ZlYWQ1YTJmMGEwZWM1OWM0YWY2MmYxMjZhZTcwZDZiZWQyNjFhZWQ0Zjk5YzE0YjQ5MiJ9fX0=")
            }
            addCriterion("gift", Criteria.CAT_GIFT_RECEIVED.createCriterion(SingleItemCriterion.Conditions()))
        }.also(consumer::accept)

        val plethoraOfCats = advancement(TAB_NAME, PLETHORA_OF_CATS) {
            parent(catGift)
            display {
                frame = AdvancementType.GOAL
                icon = Items.COD.defaultInstance
            }
            addCriterion(
                "20", Criteria.PET_TAMED.createCriterion(
                    PetTamedCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity().entityType(
                                EntityTypePredicate.of(
                                    wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE),
                                    EntityType.CAT
                                )
                            ).build()
                        ),
                        Optional.of(MinMaxBounds.Ints.atLeast(20)),
                    )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, ALL_CAT_GIFTS) {
            parent(plethoraOfCats)
            display {
                frame = AdvancementType.GOAL
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
                addCriterion(
                    it.builtInRegistryHolder().registeredName, Criteria.CAT_GIFT_RECEIVED.createCriterion(
                        SingleItemCriterion.Conditions.items(wrapperLookup, it)
                    )
                )
            }
        }.also(consumer::accept)

        advancement(TAB_NAME, LOCK_MAP) {
            parent(createPlaceholder("blazeandcave:adventure/mapmakers_table"))
            display {
                icon = Items.GLASS_PANE.defaultInstance
            }
            addCriterion("lock", Criteria.MAP_LOCKED.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, GET_RAID_OF_IT) {
            parent(createPlaceholder("blazeandcave:adventure/were_being_attacked"))
            display {
                icon = Items.MILK_BUCKET.defaultInstance
            }
            addCriterion("get_raid_of_it", Criteria.GET_RAID_OF_IT.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, CAN_YOU_HEAR_IT_FROM_HERE) {
            parent(createPlaceholder("blazeandcave:adventure/oh_look_it_dings"))
            display {
                icon = Items.ARROW.defaultInstance
                frame = AdvancementType.CHALLENGE
            }
            addCriterion(
                "50", Criteria.BELL_SHOT_FROM_DISTANCE.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(50))
                    )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, MASTER_ARCHEOLOGIST) {
            parent(createPlaceholder("blazeandcave:adventure/a_suspicious_advancement"))
            display {
                icon = Items.BRUSH.defaultInstance.apply {
                    set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                frame = AdvancementType.CHALLENGE
                hidden = true
            }

            susLoot.forEach { item ->
                addCriterion(
                    item.builtInRegistryHolder().registeredName, Criteria.SUS_BLOCK_GOT_ITEM.createCriterion(
                        SingleItemCriterion.Conditions.items(wrapperLookup, item)
                    )
                )
            }
        }.also(consumer::accept)

        advancement(TAB_NAME, MAXIMUM_COVERAGE) {
            parent(createPlaceholder("blazeandcave:adventure/mapmakers_table"))
            display {
                icon = Items.FILLED_MAP.defaultInstance
            }
            addCriterion(
                "max_coverage", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item().withComponents(
                        DataComponentMatchers.Builder.components().partial(
                            Main.mapStatePredicate,
                            MapStatePredicate(scale = MinMaxBounds.Ints.exactly(MapItemSavedData.MAX_SCALE))
                        )
                            .build()
                    ).build()
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, PAINT_IT_RED) {
            parent(createPlaceholder("blazeandcave:adventure/mapmaker"))
            display {
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ4NjM2MmQwZjY4OTVkOWMyMmNlN2JmNjRkODU3Mjc2MDFiNWQ5ODNmZmM1YTUzZmE1YmYzYjQ3OTRkMWZkMSJ9fX0=")
                frame = AdvancementType.CHALLENGE
            }
            addCriterion(
                "paint_it_red", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item()
                        .withComponents(
                            DataComponentMatchers.Builder.components()
                                .partial(
                                    Main.mapStatePredicate,
                                    MapStatePredicate(
                                        colors = Optional.of(
                                            MapColorPredicate.AllSame(
                                                listOf(
                                                    // FIRE: tnt, lava, fire, redstone block
                                                    MinMaxBounds.Ints.between(16, 19),
                                                    // COLOR_RED
                                                    MinMaxBounds.Ints.between(112, 115)
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
            parent(createPlaceholder("blazeandcave:adventure/shady_deals"))

            display {
                icon = Items.DEAD_BUSH.defaultInstance
                frame = AdvancementType.GOAL
            }

            addCriterion("milk_bucket", Criteria.WANDERING_TRADER_DROPPED_ITEM.createCriterion(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.entity()
                        .of(wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                        .build()),
                    Optional.of(
                        ItemPredicate.Builder.item()
                        .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.MILK_BUCKET)
                        .build())
                )
            ))

            // don't check if it's invisibility cuz pointless
            addCriterion("potion", Criteria.WANDERING_TRADER_DROPPED_ITEM.createCriterion(
                EntityDroppedLootCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.entity()
                        .of(wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                        .build()),
                    Optional.of(
                        ItemPredicate.Builder.item()
                        .of(wrapperLookup.lookupOrThrow(Registries.ITEM), Items.POTION)
                        .build())
                )
            ))
        }.let(consumer::accept)

        advancement(TAB_NAME, DEHYDRATION) {
            parent(createPlaceholder("blazeandcave:adventure/dry_spell"))

            display {
                icon = Items.WATER_BUCKET.defaultInstance
            }

            addCriterion("dehydration", Criteria.FURNACE_TOOK_WATER_BUCKET_FUEL.createCriterion(
                EmptyCriterion.Conditions()
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
