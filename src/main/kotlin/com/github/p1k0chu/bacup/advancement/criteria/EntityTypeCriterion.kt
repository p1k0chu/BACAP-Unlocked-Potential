package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.EntityTypePredicate
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderLookup
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EntityType
import java.util.Optional

class EntityTypeCriterion : SimpleCriterionTrigger<EntityTypeCriterion.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, entityType: EntityType<*>) {
        this.trigger(player) {
            it.matches(entityType)
        }
    }

    fun entityType(entityType: EntityTypePredicate?) = createCriterion(
        Instance(
            Optional.empty(),
            Optional.ofNullable(entityType)
        )
    )

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val entityType: Optional<EntityTypePredicate>
    ) : SimpleInstance {
        override fun player() = _player

        fun matches(entityType: EntityType<*>): Boolean {
            return (this.entityType.isEmpty || this.entityType.get().matches(entityType))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    EntityTypePredicate.CODEC.optionalFieldOf("entity_type")
                        .forGetter(Instance::entityType)
                ).apply(instance, EntityTypeCriterion::Instance)
            }
        }
    }
}