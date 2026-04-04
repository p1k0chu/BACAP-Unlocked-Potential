package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.AdvancementType
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleEntityCriterion
import com.github.p1k0chu.bacup.constants.KillMobConstants
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
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
                icon = Items.ANVIL.defaultInstance
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
