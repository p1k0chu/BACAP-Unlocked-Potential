package io.github.p1k0chu.bacapup.advancement.triggers

import io.github.p1k0chu.bacapup.Main.id
import net.minecraft.advancements.CriteriaTriggers

object BacapupTriggers {
    @JvmField
    val CAT_GIFT_RECEIVED: SingleItemTrigger = CriteriaTriggers.register(
        id("cat_gift_received"), SingleItemTrigger()
    )
    @JvmField
    val MAP_LOCKED: EmptyTrigger = CriteriaTriggers.register(
        id("map_locked"), EmptyTrigger()
    )
    @JvmField
    val WHEN_PIGS_FLY: EmptyTrigger = CriteriaTriggers.register(
        id("when_pigs_fly"), EmptyTrigger()
    )
    @JvmField
    val DISENCHANT_GRINDSTONE: EmptyTrigger = CriteriaTriggers.register(
        id("disenchant_grindstone"), EmptyTrigger()
    )
    @JvmField
    val FURNACE_FUEL_CONSUMED: SingleItemTrigger = CriteriaTriggers.register(
        id("furnace_fuel_consumed"), SingleItemTrigger()
    )
    @JvmField
    val BEAM_ME_UP: EmptyTrigger = CriteriaTriggers.register(
        id("beam_me_up"), EmptyTrigger()
    )
    @JvmField
    val PET_TAMED: PetTamedTrigger = CriteriaTriggers.register(
        id("pet_tamed"), PetTamedTrigger()
    )
    @JvmField
    val COOKED_WITH_FUEL: FurnaceCookedWithFuelTrigger = CriteriaTriggers.register(
        id("cooked_with_fuel"), FurnaceCookedWithFuelTrigger()
    )
    @JvmField
    val TRADED_FOR_EMERALDS: SingleIntRangeTrigger = CriteriaTriggers.register(
        id("traded_for_emeralds"), SingleIntRangeTrigger()
    )
    @JvmField
    val ENTITY_DROPPED_LOOT: EntityDroppedLootTrigger = CriteriaTriggers.register(
        id("entity_dropped_loot"), EntityDroppedLootTrigger()
    )
    @JvmField
    val WANDERING_TRADER_DROPPED_ITEM: EntityDroppedLootTrigger = CriteriaTriggers.register(
        id("wandering_trader_dropped_item"), EntityDroppedLootTrigger()
    )
    @JvmField
    val CACTUS_DESTROY_ITEM: SingleItemTrigger = CriteriaTriggers.register(
        id("thrown_item_died_to_cactus"), SingleItemTrigger()
    )
    @JvmField
    val SPAWN_DRAGON_WITH_CRYSTALS: SingleIntRangeTrigger = CriteriaTriggers.register(
        id("spawn_dragon_with_crystals"), SingleIntRangeTrigger()
    )
    @JvmField
    val BELL_SHOT_FROM_DISTANCE: SingleIntRangeTrigger = CriteriaTriggers.register(
        id("bell_shot_from_distance"), SingleIntRangeTrigger()
    )
    /// works on certain blocks because others don't matter
    @JvmField
    val PROJECTILE_LIT_BLOCK: SingleBlockTrigger = CriteriaTriggers.register(
        id("projectile_lit_block"), SingleBlockTrigger()
    )
    @JvmField
    val WOLOLO: EmptyTrigger = CriteriaTriggers.register(
        id("wololo"), EmptyTrigger()
    )
    @JvmField
    val ANVIL_KILL: SingleEntityTrigger = CriteriaTriggers.register(
        id("anvil_kill"), SingleEntityTrigger()
    )
    @JvmField
    val SUS_BLOCK_GOT_ITEM: SingleItemTrigger = CriteriaTriggers.register(
        id("sus_block_got_item"), SingleItemTrigger()
    )
    @JvmField
    val GLGLTU: SingleIntRangeTrigger = CriteriaTriggers.register(
        id("glgltu"), SingleIntRangeTrigger()
    )
    @JvmField
    val FURNACE_TOOK_WATER_BUCKET_FUEL: EmptyTrigger = CriteriaTriggers.register(
        id("furnace_took_water_bucket_fuel"), EmptyTrigger()
    )
    @JvmField
    val PLAYER_BREAK_NETHERITE_BLOCK_WITH_FIST: EmptyTrigger = CriteriaTriggers.register(
        id("player_break_netherite_block_with_fist"), EmptyTrigger()
    )
    @JvmField
    val BEACON_CONSUMED_PAYMENT: SingleItemTrigger = CriteriaTriggers.register(
        id("beacon_consumed_payment"), SingleItemTrigger()
    )
    @JvmField
    val PARROT_IMITATES: EntityTypeTrigger = CriteriaTriggers.register(
        id("parrot_imitates"), EntityTypeTrigger()
    )
    @JvmField
    val MAP_STATE: MapTrigger = CriteriaTriggers.register(
        id("map_state"), MapTrigger()
    )
}
