package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.PetTamedCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object AnimalsTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "animal"

    const val WHEN_PIGS_FLY = "when_pigs_fly"
    const val DOG_ARMY = "dog_army"
    const val WOLOLO = "wololo"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, WHEN_PIGS_FLY) {
            parent(createPlaceholder("blazeandcave:animal/when_pigs_used_to_fly"))
            display {
                icon = Items.SADDLE.defaultInstance
            }
            addCriterion("when_pigs_fly", Criteria.WHEN_PIGS_FLY.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)

        advancement(TAB_NAME, DOG_ARMY) {
            parent(createPlaceholder("blazeandcave:animal/puppies_yipe"))
            display {
                icon = Items.BONE.defaultInstance
                frame = AdvancementType.GOAL
            }
            addCriterion("woof", Criteria.PET_TAMED.createCriterion(
                PetTamedCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        EntityPredicate.Builder.entity()
                            .of(wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WOLF)
                            .build()
                    ),
                    Optional.of(
                        MinMaxBounds.Ints.atLeast(20)
                    )
                )
            ))
        }.also(consumer::accept)

        advancement(TAB_NAME, WOLOLO) {
            parent(createPlaceholder("blazeandcave:animal/live_and_let_dye"))
            display {
                icon = Items.TOTEM_OF_UNDYING.defaultInstance
                frame = AdvancementType.GOAL
            }
            addCriterion("wololo", Criteria.WOLOLO.createCriterion(EmptyCriterion.Conditions()))
        }.also(consumer::accept)
    }
}
