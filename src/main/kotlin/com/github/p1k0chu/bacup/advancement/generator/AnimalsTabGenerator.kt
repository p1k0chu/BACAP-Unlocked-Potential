package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.PetTamedCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.*
import java.util.function.Consumer

object AnimalsTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "animal"

    const val WHEN_PIGS_FLY = "when_pigs_fly"
    const val DOG_ARMY = "dog_army"
    const val WOLOLO = "wololo"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        advancement(TAB_NAME, WHEN_PIGS_FLY) {
            parent(reference("blazeandcave:animal/when_pigs_used_to_fly"))
            display {
                icon = Items.SADDLE.defaultStack
            }
            criterion("when_pigs_fly", Main.WHEN_PIGS_FLY.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, DOG_ARMY) {
            parent(reference("blazeandcave:animal/puppies_yipe"))
            display {
                icon = Items.BONE.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("woof", Main.PET_TAMED.create(
                PetTamedCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.create()
                            .type(wrapperLookup.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityType.WOLF)
                            .build()
                    ),
                    Optional.of(
                        NumberRange.IntRange.atLeast(20)
                    )
                )
            ))
        }.also(consumer::accept)

        advancement(TAB_NAME, WOLOLO) {
            parent(reference("blazeandcave:animal/live_and_let_dye"))
            display {
                icon = Items.TOTEM_OF_UNDYING.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("wololo", Main.WOLOLO.create(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}
