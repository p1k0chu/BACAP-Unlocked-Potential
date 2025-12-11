package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleEntityCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object ChallengesTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "challenges"
    const val MOB_FLATTENER_9000 = "mob_flattener_9000"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        advancement(TAB_NAME, MOB_FLATTENER_9000) {
            parent(createPlaceholder("blazeandcave:challenges/biological_warfare"))
            display {
                icon = Items.ANVIL.defaultInstance
                frame = AdvancementType.CHALLENGE
            }

            KILLABLE_MOBS.forEach { mob ->
                addCriterion(mob.builtInRegistryHolder().registeredName, Criteria.ANVIL_KILL.createCriterion(
                    SingleEntityCriterion.Conditions(
                        Optional.empty(),
                        listOf(
                            EntityPredicate.Builder.entity()
                                .of(wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE), mob)
                                .build()
                        )
                    )
                ))
            }
        }.also(consumer::accept)
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
