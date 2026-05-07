package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementSubProvider
import io.github.p1k0chu.bacapup.advancement.AdvancementType
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.EmptyTrigger
import io.github.p1k0chu.bacapup.advancement.triggers.PetTamedTrigger
import io.github.p1k0chu.bacapup.constants.ParrotConstants
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.predicates.entity.EntityTypePredicate
import net.minecraft.advancements.predicates.MinMaxBounds
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object AnimalsTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "animal"

    const val WHEN_PIGS_FLY = "when_pigs_fly"
    const val DOG_ARMY = "dog_army"
    const val WOLOLO = "wololo"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, WHEN_PIGS_FLY) {
            parent(createPlaceholder("blazeandcave:animal/when_pigs_used_to_fly"))
            display {
                title = "When Pigs Fly"
                description = "Ride a pig off a cliff"
                icon = ItemStackTemplate(Items.SADDLE)
            }
            addCriterion("when_pigs_fly", BacapupTriggers.WHEN_PIGS_FLY.createCriterion(EmptyTrigger.Instance()))
        }

        advancement(consumer, TAB_NAME, DOG_ARMY) {
            parent(createPlaceholder("blazeandcave:animal/puppies_yipe"))
            display {
                title = "Dog Army"
                description = "Befriend twenty wolves"
                icon = ItemStackTemplate(Items.BONE)
                type = AdvancementType.GOAL
            }
            exp = 50
            addCriterion(
                "woof", BacapupTriggers.PET_TAMED.createCriterion(
                    PetTamedTrigger.Instance(
                        Optional.empty(),
                        Optional.of(
                            EntityPredicate.Builder.entity()
                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypes.WOLF)
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
                icon = ItemStackTemplate(Items.TOTEM_OF_UNDYING)
                type = AdvancementType.GOAL
            }
            exp = 1
            addCriterion("wololo", BacapupTriggers.WOLOLO.createCriterion(EmptyTrigger.Instance()))
        }
    }
}
