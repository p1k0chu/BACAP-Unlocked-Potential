package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.AdvancementType
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.EmptyCriterion
import com.github.p1k0chu.bacup.advancement.criteria.PetTamedCriterion
import com.github.p1k0chu.bacup.constants.ParrotConstants
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.EntityTypePredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import java.util.*

object AnimalsTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "animal"

    const val WHEN_PIGS_FLY = "when_pigs_fly"
    const val DOG_ARMY = "dog_army"
    const val WOLOLO = "wololo"
    const val POLYGLOT = "polyglot"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, WHEN_PIGS_FLY) {
            parent(createPlaceholder("blazeandcave:animal/when_pigs_used_to_fly"))
            display {
                title = "When Pigs Fly"
                description = "Ride a pig off a cliff"
                icon = Items.SADDLE.defaultInstance
            }
            addCriterion("when_pigs_fly", Criteria.WHEN_PIGS_FLY.createCriterion(EmptyCriterion.Conditions()))
        }

        advancement(consumer, TAB_NAME, DOG_ARMY) {
            parent(createPlaceholder("blazeandcave:animal/puppies_yipe"))
            display {
                title = "Dog Army"
                description = "Befriend twenty wolves"
                icon = Items.BONE.defaultInstance
                type = AdvancementType.GOAL
            }
            exp = 50
            addCriterion(
                "woof", Criteria.PET_TAMED.createCriterion(
                    PetTamedCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity()
                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.WOLF)
                                .build()
                        ),
                        Optional.of(
                            MinMaxBounds.Ints.atLeast(20)
                        )
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, WOLOLO) {
            parent(createPlaceholder("blazeandcave:animal/live_and_let_dye"))
            display {
                title = "Wololo"
                description = "Watch evoker recolor blue sheep in red"
                icon = Items.TOTEM_OF_UNDYING.defaultInstance
                type = AdvancementType.GOAL
            }
            exp = 1
            addCriterion("wololo", Criteria.WOLOLO.createCriterion(EmptyCriterion.Conditions()))
        }

        advancement(consumer, TAB_NAME, POLYGLOT) {
            parent(createPlaceholder("blazeandcave:animal/birdkeeper"))
            display {
                title = "Pollyglot"
                description = "Hear your parrots speak like every mob"
                icon = Items.WRITABLE_BOOK.defaultInstance
                type = AdvancementType.CHALLENGE
            }

            for (mobType in ParrotConstants.IMITATING_TYPES) {
                addCriterion(
                    BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(mobType).registeredName,
                    Criteria.PARROT_IMITATES.entityType(
                        EntityTypePredicate.of(provider.lookupOrThrow(Registries.ENTITY_TYPE), mobType)
                    )
                )
            }
        }
    }
}
