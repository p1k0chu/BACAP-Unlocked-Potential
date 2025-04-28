package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper
import java.util.function.Consumer

object AnimalsTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "animal"

    const val WHEN_PIGS_FLY = "when_pigs_fly"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, WHEN_PIGS_FLY) {
            parent(reference("blazeandcave:animal/when_pigs_used_to_fly"))
            display {
                icon = Items.SADDLE.defaultStack
            }
            criterion("when_pigs_fly", Main.WHEN_PIGS_FLY.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}
