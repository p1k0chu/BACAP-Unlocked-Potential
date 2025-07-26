package com.github.p1k0chu.bacup.advancement.criteria

import com.github.p1k0chu.bacup.Main.id
import net.minecraft.advancement.criterion.Criteria

object Criteria {
    @JvmField
    val CAT_GIFT_RECEIVED: SingleItemCriterion = Criteria.register(
        id("cat_gift_received"), SingleItemCriterion()
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
    val TRADED_FOR_EMERALDS: SingleIntRangeCriterion = Criteria.register(
        id("traded_for_emeralds"), SingleIntRangeCriterion()
    )
    @JvmField
    val ENTITY_DROPPED_LOOT: EntityDroppedLootCriterion = Criteria.register(
        id("entity_dropped_loot"), EntityDroppedLootCriterion()
    )
    @JvmField
    val WANDERING_TRADER_DROPPED_ITEM: EntityDroppedLootCriterion = Criteria.register(
        id("wandering_trader_dropped_item"), EntityDroppedLootCriterion()
    )
    @JvmField
    val GET_RAID_OF_IT: EmptyCriterion = Criteria.register(
        id("get_raid_of_it"), EmptyCriterion()
    )
    @JvmField
    val CACTUS_DESTROY_ITEM: SingleItemCriterion = Criteria.register(
        id("thrown_item_died_to_cactus"), SingleItemCriterion()
    )
    @JvmField
    val SPAWN_DRAGON_WITH_CRYSTALS: SingleIntRangeCriterion = Criteria.register(
        id("spawn_dragon_with_crystals"), SingleIntRangeCriterion()
    )
    @JvmField
    val BELL_SHOT_FROM_DISTANCE: SingleIntRangeCriterion = Criteria.register(
        id("bell_shot_from_distance"), SingleIntRangeCriterion()
    )
    /// works on certain blocks because others don't matter
    @JvmField
    val PROJECTILE_LIT_BLOCK: SingleBlockCriterion = Criteria.register(
        id("projectile_lit_block"), SingleBlockCriterion()
    )
    @JvmField
    val WOLOLO: EmptyCriterion = Criteria.register(
        id("wololo"), EmptyCriterion()
    )
    @JvmField
    val ANVIL_KILL: SingleEntityCriterion = Criteria.register(
        id("anvil_kill"), SingleEntityCriterion()
    )
    @JvmField
    val SUS_BLOCK_GOT_ITEM: SingleItemCriterion = Criteria.register(
        id("sus_block_got_item"), SingleItemCriterion()
    )
    @JvmField
    val GLGLTU: SingleIntRangeCriterion = Criteria.register(
        id("glgltu"), SingleIntRangeCriterion()
    )
}
