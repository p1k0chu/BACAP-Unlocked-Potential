package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import java.util.*

class SingleEntityTrigger : SimpleCriterionTrigger<SingleEntityTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, entity: Entity) {
        this.trigger(player) { conditions ->
            conditions.matches(player, entity)
        }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val entity: List<EntityPredicate>
    ) : SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(player: ServerPlayer, entity: Entity): Boolean {
            return this.entity.isEmpty() || this.entity.any { it.matches(player, entity) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    EntityPredicate.CODEC.listOf().optionalFieldOf("entity", emptyList())
                        .forGetter(Instance::entity)
                ).apply(instance, SingleEntityTrigger::Instance)
            }
        }
    }
}
