package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.*
import com.github.p1k0chu.bacup.advancement.criteria.*
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicate
import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.criterion.*
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.level.saveddata.maps.MapItemSavedData
import java.util.*

object AdventureTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "adventure"

    const val CAT_GIFT = "cat_gift"
    const val ALL_CAT_GIFTS = "all_cat_gifts"
    const val LOCK_MAP = "lock_map"
    const val PLETHORA_OF_CATS = "plethora_of_cats"
    const val CAN_YOU_HEAR_IT_FROM_HERE = "can_you_hear_it_from_here"
    const val MASTER_ARCHEOLOGIST = "master_archaeologist"
    const val MAXIMUM_COVERAGE = "maximum_coverage"
    const val PAINT_IT_RED = "paint_it_red"
    const val SECRET_SUPPLIES = "secret_supplies"
    const val DEHYDRATION = "dehydration"
    const val THIS_IS_NOT_COOKIE_CLICKER = "this_is_not_cookie_clicker"
    const val SHORT_CIRCUIT = "short_circuit"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        val catGift = advancement(consumer, TAB_NAME, CAT_GIFT) {
            parent(createPlaceholder("blazeandcave:adventure/crazy_cat_lady"))
            display {
                title = "Where have you been?"
                description = "Receive a gift from a tamed cat in the morning."
                // cat head; https://minecraft-heads.com/custom-heads/head/66218-cat-plushie-siamese
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU5MTFlMjRjODU1M2ZlYWQ1YTJmMGEwZWM1OWM0YWY2MmYxMjZhZTcwZDZiZWQyNjFhZWQ0Zjk5YzE0YjQ5MiJ9fX0=")
            }
            addCriterion("gift", Criteria.CAT_GIFT_RECEIVED.createCriterion(SingleItemCriterion.Conditions()))
        }

        val plethoraOfCats = advancement(consumer, TAB_NAME, PLETHORA_OF_CATS) {
            parent(catGift)
            display {
                title = "Plethora of Cats"
                description = "Befriend twenty stray cats"
                type = AdvancementType.GOAL
                icon = ItemStackTemplate(Items.COD)
            }
            exp = 50
            addCriterion(
                "20", Criteria.PET_TAMED.createCriterion(
                    PetTamedCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity().entityType(
                                EntityTypePredicate.of(
                                    provider.lookupOrThrow(Registries.ENTITY_TYPE),
                                    EntityType.CAT
                                )
                            ).build()
                        ),
                        Optional.of(MinMaxBounds.Ints.atLeast(20)),
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, ALL_CAT_GIFTS) {
            parent(plethoraOfCats)
            display {
                title = "A Meow Massages The Heart"
                description = "Receive every kind of gifts from your cat"
                type = AdvancementType.GOAL
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
                        SingleItemCriterion.Conditions.items(provider, it)
                    )
                )
            }
        }

        advancement(consumer, TAB_NAME, LOCK_MAP) {
            parent(createPlaceholder("blazeandcave:adventure/mapmakers_table"))
            display {
                title = "Do It For The Frame"
                description = "Use a glass pane in a cartography table to lock a map, preventing it from updating"
                icon = ItemStackTemplate(Items.GLASS_PANE)
            }
            addCriterion("lock", Criteria.MAP_LOCKED.createCriterion(EmptyCriterion.Conditions()))
        }

        advancement(consumer, TAB_NAME, CAN_YOU_HEAR_IT_FROM_HERE) {
            parent(createPlaceholder("blazeandcave:adventure/oh_look_it_dings"))
            display {
                title = "Can you hear it from here?"
                description = "Shoot a bell from more than 50 blocks away"
                icon = ItemStackTemplate(Items.ARROW)
                type = AdvancementType.CHALLENGE
            }
            exp = 50
            addCriterion(
                "50", Criteria.BELL_SHOT_FROM_DISTANCE.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(50))
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, MASTER_ARCHEOLOGIST) {
            parent(createPlaceholder("blazeandcave:adventure/a_suspicious_advancement"))
            display {
                title = "Master Archeologist"
                description = "Get every item possible from sus sand or gravel"
                icon = ItemStackTemplate(
                    Items.BRUSH,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.HIDDEN
                hidden = true
            }
            exp = 200

            susLoot.forEach { item ->
                addCriterion(
                    item.builtInRegistryHolder().registeredName, Criteria.SUS_BLOCK_GOT_ITEM.createCriterion(
                        SingleItemCriterion.Conditions.items(provider, item)
                    )
                )
            }
        }

        advancement(consumer, TAB_NAME, MAXIMUM_COVERAGE) {
            parent(createPlaceholder("blazeandcave:adventure/mapmakers_table"))
            display {
                title = "Maximum Coverage"
                description = "Fully expand a map"
                icon = ItemStackTemplate(Items.FILLED_MAP)
            }
            addCriterion(
                "max_coverage", Criteria.MAP_STATE.createCriterion(
                    MapCriterion.Instance(
                        Optional.empty(),
                        Optional.of(MapStatePredicate(scale = MinMaxBounds.Ints.exactly(MapItemSavedData.MAX_SCALE)))
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, PAINT_IT_RED) {
            parent(createPlaceholder("blazeandcave:adventure/mapmaker"))
            display {
                title = "Paint it, red"
                description = "Fill a map with red color"
                icon =
                    getPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ4NjM2MmQwZjY4OTVkOWMyMmNlN2JmNjRkODU3Mjc2MDFiNWQ5ODNmZmM1YTUzZmE1YmYzYjQ3OTRkMWZkMSJ9fX0=")
                type = AdvancementType.CHALLENGE
            }
            addCriterion(
                "paint_it_red", Criteria.MAP_STATE.createCriterion(
                    MapCriterion.Instance(
                        Optional.empty(),
                        Optional.of(
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
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, SECRET_SUPPLIES) {
            parent(createPlaceholder("blazeandcave:adventure/shady_deals"))

            display {
                title = "Secret Supplies"
                description = "Acquire a potion of invisibility and milk bucket dropped from wandering traders"
                icon = ItemStackTemplate(Items.DEAD_BUSH)
                type = AdvancementType.GOAL
            }

            addCriterion(
                "milk_bucket", Criteria.WANDERING_TRADER_DROPPED_ITEM.createCriterion(
                    EntityDroppedLootCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity()
                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                                .build()
                        ),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.MILK_BUCKET)
                                .build()
                        )
                    )
                )
            )

            // don't check if it's invisibility cuz pointless
            addCriterion(
                "potion", Criteria.WANDERING_TRADER_DROPPED_ITEM.createCriterion(
                    EntityDroppedLootCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity()
                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WANDERING_TRADER)
                                .build()
                        ),
                        Optional.of(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.POTION)
                                .build()
                        )
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, DEHYDRATION) {
            parent(createPlaceholder("blazeandcave:adventure/dry_spell"))

            display {
                title = "Dehydration"
                description = "Dry a sponge in a furnace, but not without taking the water back"
                icon = ItemStackTemplate(Items.WATER_BUCKET)
            }

            addCriterion(
                "dehydration", Criteria.FURNACE_TOOK_WATER_BUCKET_FUEL.createCriterion(
                    EmptyCriterion.Conditions()
                )
            )
        }

        advancement(consumer, TAB_NAME, THIS_IS_NOT_COOKIE_CLICKER) {
            parent(createPlaceholder("blazeandcave:farming/me_love_cookie"))

            display {
                title = "This Is Not Cookie Clicker"
                description = "Why did you double click an empty slot?"
                icon = ItemStackTemplate(
                    Items.COOKIE,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.HIDDEN
            }

            addCriterion(
                "are_you_looking_for_something",
                CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance())
            )
        }

        advancement(consumer, TAB_NAME, SHORT_CIRCUIT) {
            parent(createPlaceholder("blazeandcave:adventure/this_will_be_a_breeze"))

            display {
                title = "Short Circuit"
                description = "Get a breeze to activate 9 redstone components at the same time"
                icon = ItemStackTemplate(Items.COPPER_TRAPDOOR)
                type = AdvancementType.GOAL
            }

            addCriterion(
                "short_circuit",
                CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance())
            )
        }
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
