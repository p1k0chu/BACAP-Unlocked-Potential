package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.entity.Entity
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class SingleEntityCriterion : AbstractCriterion<SingleEntityCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, entity: Entity) {
        this.trigger(player) { conditions ->
            conditions.matches(player, entity)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val entity: List<EntityPredicate>
    ) : AbstractCriterion.Conditions {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(player: ServerPlayerEntity, entity: Entity): Boolean {
            return this.entity.isEmpty() || this.entity.any { it.test(player, entity) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    EntityPredicate.CODEC.listOf().optionalFieldOf("entity", emptyList())
                        .forGetter(Conditions::entity)
                ).apply(instance, SingleEntityCriterion::Conditions)
            }
        }
    }
}
