package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object MonstersTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "monsters"

    const val BEAM_ME_UP = "beam_me_up"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, BEAM_ME_UP) {
            parent(reference("blazeandcave:monsters/tele_morph"))
            display {
                icon = Items.ENDER_PEARL.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("beam_me_up", Main.BEAM_ME_UP.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}