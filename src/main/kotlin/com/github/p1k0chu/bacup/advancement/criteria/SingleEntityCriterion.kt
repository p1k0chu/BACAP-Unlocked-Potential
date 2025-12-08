package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.entity.Entity
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

class SingleEntityCriterion : SimpleCriterionTrigger<SingleEntityCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, entity: Entity) {
        this.trigger(player) { conditions ->
            conditions.matches(player, entity)
        }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val entity: List<EntityPredicate>
    ) : SimpleCriterionTrigger.SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(player: ServerPlayer, entity: Entity): Boolean {
            return this.entity.isEmpty() || this.entity.any { it.matches(player, entity) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    EntityPredicate.CODEC.listOf().optionalFieldOf("entity", emptyList())
                        .forGetter(Conditions::entity)
                ).apply(instance, SingleEntityCriterion::Conditions)
            }
        }
    }
}
