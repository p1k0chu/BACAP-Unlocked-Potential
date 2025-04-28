package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.CatGiftReceivedCriterion
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.FurnaceFuelConsumedCriterion
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
    val FURNACE_FUEL_CONSUMED: FurnaceFuelConsumedCriterion = Criteria.register(
        id("furnace_fuel_consumed"), FurnaceFuelConsumedCriterion()
    )

    @JvmField
    val BEAM_ME_UP: EmptyCriterion = Criteria.register(
        id("beam_me_up"), EmptyCriterion()
    )

    override fun onInitialize() {
    }

    fun id(id: String) = "$MOD_ID:$id"
}
