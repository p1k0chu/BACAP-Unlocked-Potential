package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.*
import net.fabricmc.api.ModInitializer
import net.minecraft.advancement.criterion.Criteria


object Main : ModInitializer {
    const val MOD_ID: String = "bac-unlocked-potential"

    @JvmField
    val CAT_GIFT_RECEIVED: CatGiftReceivedCriterion = Criteria.register(
        id("cat_gift_received"), CatGiftReceivedCriterion()
    )
    @JvmField
    val MAP_LOCKED: EmptyCriterion = Criteria.register(
        id("map_locked"), EmptyCriterion()
    )
    @JvmField
    val WHEN_PIGS_FLY: EmptyCriterion = Criteria.register(
        id("when_pigs_fly"), EmptyCriterion()
    )
    @JvmField
    val DISENCHANT_GRINDSTONE: EmptyCriterion = Criteria.register(
        id("disenchant_grindstone"), EmptyCriterion()
    )
    @JvmField
    val FURNACE_FUEL_CONSUMED: SingleItemCriterion = Criteria.register(
        id("furnace_fuel_consumed"), SingleItemCriterion()
    )
    @JvmField
    val BEAM_ME_UP: EmptyCriterion = Criteria.register(
        id("beam_me_up"), EmptyCriterion()
    )
    @JvmField
    val PET_TAMED: PetTamedCriterion = Criteria.register(
        id("pet_tamed"), PetTamedCriterion()
    )
    @JvmField
    val COOKED_WITH_FUEL: FurnaceCookedWithFuelCriterion = Criteria.register(
        id("cooked_with_fuel"), FurnaceCookedWithFuelCriterion()
    )
    @JvmField
    val TRADED_FOR_EMERALDS: TradedForEmeraldsCriterion = Criteria.register(
        id("traded_for_emeralds"), TradedForEmeraldsCriterion()
    )
    @JvmField
    val ENTITY_DROPPED_LOOT: EntityDroppedLootCriterion = Criteria.register(
        id("entity_dropped_loot"), EntityDroppedLootCriterion()
    )
    @JvmField
    val GET_RAID_OF_IT: EmptyCriterion = Criteria.register(
        id("get_raid_of_it"), EmptyCriterion()
    )

    override fun onInitialize() {
    }

    fun id(id: String) = "$MOD_ID:$id"
}
