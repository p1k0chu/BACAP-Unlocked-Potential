package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.AdvancementType
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleEntityCriterion
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
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

            KILLABLE_MOBS.forEach { mob ->
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

    private val KILLABLE_MOBS = listOf(
        EntityType.ALLAY,
        EntityType.ARMADILLO,
        EntityType.AXOLOTL,
        EntityType.BAT,
        EntityType.CAMEL,
        EntityType.CAT,
        EntityType.CHICKEN,
        EntityType.COD,
        EntityType.COW,
        EntityType.DONKEY,
        EntityType.FROG,
        EntityType.GLOW_SQUID,
        EntityType.HORSE,
        EntityType.MOOSHROOM,
        EntityType.MULE,
        EntityType.OCELOT,
        EntityType.PARROT,
        EntityType.PIG,
        EntityType.PUFFERFISH,
        EntityType.RABBIT,
        EntityType.SALMON,
        EntityType.SHEEP,
        EntityType.SKELETON_HORSE,
        EntityType.SNIFFER,
        EntityType.SNOW_GOLEM,
        EntityType.SQUID,
        EntityType.STRIDER,
        EntityType.TADPOLE,
        EntityType.TROPICAL_FISH,
        EntityType.TURTLE,
        EntityType.VILLAGER,
        EntityType.WANDERING_TRADER,
        EntityType.BEE,
        EntityType.CAVE_SPIDER,
        EntityType.DOLPHIN,
        EntityType.DROWNED,
        EntityType.ENDERMAN,
        EntityType.FOX,
        EntityType.GOAT,
        EntityType.IRON_GOLEM,
        EntityType.LLAMA,
        EntityType.PANDA,
        EntityType.PIGLIN,
        EntityType.POLAR_BEAR,
        EntityType.SPIDER,
        EntityType.TRADER_LLAMA,
        EntityType.WOLF,
        EntityType.ZOMBIFIED_PIGLIN,
        EntityType.BLAZE,
        EntityType.BOGGED,
        EntityType.BREEZE,
        EntityType.CREEPER,
        EntityType.ELDER_GUARDIAN,
        EntityType.ENDERMITE,
        EntityType.EVOKER,
        EntityType.GHAST,
        EntityType.GUARDIAN,
        EntityType.HOGLIN,
        EntityType.HUSK,
        EntityType.MAGMA_CUBE,
        EntityType.PHANTOM,
        EntityType.PIGLIN_BRUTE,
        EntityType.PILLAGER,
        EntityType.RAVAGER,
        EntityType.SILVERFISH,
        EntityType.SKELETON,
        EntityType.SLIME,
        EntityType.STRAY,
        EntityType.VEX,
        EntityType.VINDICATOR,
        EntityType.WARDEN,
        EntityType.WITCH,
        EntityType.WITHER_SKELETON,
        EntityType.ZOGLIN,
        EntityType.ZOMBIE,
        EntityType.ZOMBIE_VILLAGER,
    )
}
