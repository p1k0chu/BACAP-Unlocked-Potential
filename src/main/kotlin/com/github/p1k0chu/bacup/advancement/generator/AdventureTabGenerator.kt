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
    }
}
