package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.CatGiftReceivedCriterion
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
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

    override fun onInitialize() {
    }

    fun id(id: String) = "$MOD_ID:$id"
}
