package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.AdvancementConsumer
import io.github.p1k0chu.bacapup.advancement.AdvancementGenerator
import io.github.p1k0chu.bacapup.advancement.AdvancementType
import io.github.p1k0chu.bacapup.advancement.advancement
import io.github.p1k0chu.bacapup.advancement.criteria.Criteria
import io.github.p1k0chu.bacapup.advancement.criteria.SingleEntityCriterion
import io.github.p1k0chu.bacapup.constants.KillMobConstants
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object ChallengesTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "challenges"
    const val MOB_FLATTENER_9000 = "mob_flattener_9000"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        advancement(consumer, TAB_NAME, MOB_FLATTENER_9000) {
            parent(createPlaceholder("blazeandcave:challenges/biological_warfare"))
            display {
                title = "Mob Flattener 9000"
                description = "Crush all mobs with an anvil"
                icon = ItemStackTemplate(Items.ANVIL)
                type = AdvancementType.SUPER_CHALLENGE
            }
            exp = 200

            KillMobConstants.getAnvilMobsToKill(provider.lookupOrThrow(Registries.ENTITY_TYPE)).forEach { mob ->
                addCriterion(mob.builtInRegistryHolder().registeredName, Criteria.ANVIL_KILL.createCriterion(
                    SingleEntityCriterion.Conditions(
                        Optional.empty(),
                        listOf(
                            EntityPredicate.Builder.entity()
                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), mob)
                                .build()
                        )
                    )
                ))
            }
        }
    }
}
