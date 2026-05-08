package io.github.p1k0chu.bacapup.advancement.triggers

import io.github.p1k0chu.bacapup.Main.id
import net.minecraft.advancements.triggers.CriterionTrigger
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries

object BacapupTriggers {
    @JvmField
    val CAT_GIFT_RECEIVED: SingleItemTrigger = register(
        "cat_gift_received", SingleItemTrigger()
    )
    @JvmField
    val MAP_LOCKED: EmptyTrigger = register(
        "map_locked", EmptyTrigger()
    )
    @JvmField
    val WHEN_PIGS_FLY: EmptyTrigger = register(
        "when_pigs_fly", EmptyTrigger()
    )
    @JvmField
    val DISENCHANT_GRINDSTONE: EmptyTrigger = register(
        "disenchant_grindstone", EmptyTrigger()
    )
    @JvmField
    val FURNACE_FUEL_CONSUMED: SingleItemTrigger = register(
        "furnace_fuel_consumed", SingleItemTrigger()
    )
    @JvmField
    val BEAM_ME_UP: EmptyTrigger = register(
        "beam_me_up", EmptyTrigger()
    )
    @JvmField
    val PET_TAMED: PetTamedTrigger = register(
        "pet_tamed", PetTamedTrigger()
    )
    @JvmField
    val COOKED_WITH_FUEL: FurnaceCookedWithFuelTrigger = register(
        "cooked_with_fuel", FurnaceCookedWithFuelTrigger()
    )
    @JvmField
    val TRADED_FOR_EMERALDS: SingleIntRangeTrigger = register(
        "traded_for_emeralds", SingleIntRangeTrigger()
    )
    @JvmField
    val ENTITY_DROPPED_LOOT: EntityDroppedLootTrigger = register(
        "entity_dropped_loot", EntityDroppedLootTrigger()
    )
    @JvmField
    val WANDERING_TRADER_DROPPED_ITEM: EntityDroppedLootTrigger = register(
        "wandering_trader_dropped_item", EntityDroppedLootTrigger()
    )
    @JvmField
    val CACTUS_DESTROY_ITEM: SingleItemTrigger = register(
        "thrown_item_died_to_cactus", SingleItemTrigger()
    )
    @JvmField
    val SPAWN_DRAGON_WITH_CRYSTALS: SingleIntRangeTrigger = register(
        "spawn_dragon_with_crystals", SingleIntRangeTrigger()
    )
    @JvmField
    val BELL_SHOT_FROM_DISTANCE: SingleIntRangeTrigger = register(
        "bell_shot_from_distance", SingleIntRangeTrigger()
    )
    /// works on certain blocks because others don't matter
    @JvmField
    val PROJECTILE_LIT_BLOCK: SingleBlockTrigger = register(
        "projectile_lit_block", SingleBlockTrigger()
    )
    @JvmField
    val WOLOLO: EmptyTrigger = register(
        "wololo", EmptyTrigger()
    )
    @JvmField
    val ANVIL_KILL: SingleEntityTrigger = register(
        "anvil_kill", SingleEntityTrigger()
    )
    @JvmField
    val SUS_BLOCK_GOT_ITEM: SingleItemTrigger = register(
        "sus_block_got_item", SingleItemTrigger()
    )
    @JvmField
    val GLGLTU: SingleIntRangeTrigger = register(
        "glgltu", SingleIntRangeTrigger()
    )
    @JvmField
    val FURNACE_TOOK_WATER_BUCKET_FUEL: EmptyTrigger = register(
        "furnace_took_water_bucket_fuel", EmptyTrigger()
    )
    @JvmField
    val PLAYER_BREAK_NETHERITE_BLOCK_WITH_FIST: EmptyTrigger = register(
        "player_break_netherite_block_with_fist", EmptyTrigger()
    )
    @JvmField
    val BEACON_CONSUMED_PAYMENT: SingleItemTrigger = register(
        "beacon_consumed_payment", SingleItemTrigger()
    )
    @JvmField
    val PARROT_IMITATES: EntityTypeTrigger = register(
        "parrot_imitates", EntityTypeTrigger()
    )
    @JvmField
    val MAP_STATE: MapTrigger = register(
        "map_state", MapTrigger()
    )
    @JvmField
    val SULFUR_CUBE_CONTACT_KILL = register(
        "sulfur_cube_contact_kill", SulfurCubeContactDamageTrigger()
    )

    private fun <T : CriterionTrigger<*>> register(name: String, criterion: T): T {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, id(name), criterion)
    }
}
