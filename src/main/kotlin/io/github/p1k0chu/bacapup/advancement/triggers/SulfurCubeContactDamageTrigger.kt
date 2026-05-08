package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.TagPredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.triggers.Criterion
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.SulfurCubeArchetype
import java.util.*

class SulfurCubeContactDamageTrigger : SimpleCriterionTrigger<SulfurCubeContactDamageTrigger.Instance>() {
    override fun codec(): Codec<Instance> = Instance.CODEC

    fun trigger(player: ServerPlayer, entity: Entity, damage: SulfurCubeArchetype.ContactDamage) {
        this.trigger(player) {
            it.matches(player, entity, damage.damageType)
        }
    }

    fun entity(entity: EntityPredicate): Criterion<Instance> {
        return createCriterion(Instance(Optional.empty(), Optional.of(entity), Optional.empty()))
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val entity: Optional<EntityPredicate>,
        private val damageType: Optional<TagPredicate<DamageType>>
    ) : SimpleInstance {
        companion object {
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    EntityPredicate.CODEC.optionalFieldOf("entity")
                        .forGetter(Instance::entity),
                    TagPredicate.codec(Registries.DAMAGE_TYPE).optionalFieldOf("damage_type")
                        .forGetter(Instance::damageType)
                ).apply(instance, ::Instance)
            }
        }

        override fun player(): Optional<ContextAwarePredicate> = _player

        fun matches(player: ServerPlayer, entity: Entity, damage: Holder<DamageType>): Boolean {
            return (this.entity.isEmpty || this.entity.get().matches(player, entity))
                    && (this.damageType.isEmpty || this.damageType.get().matches(damage))
        }
    }
}