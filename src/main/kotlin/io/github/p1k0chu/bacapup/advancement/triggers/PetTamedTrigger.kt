package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.predicates.MinMaxBounds
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.TamableAnimal
import java.util.*

class PetTamedTrigger : SimpleCriterionTrigger<PetTamedTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, pet: TamableAnimal, total: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(player, pet, total)
        }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val pet: Optional<EntityPredicate>,
        private val total: Optional<MinMaxBounds.Ints>
    ) : SimpleInstance {

        override fun player() = _player

        fun matches(player: ServerPlayer, pet: TamableAnimal, total: Int): Boolean {
            return (this.total.isEmpty || this.total.get().matches(total))
                    && (this.pet.isEmpty || this.pet.get().matches(player, pet))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    EntityPredicate.CODEC.optionalFieldOf("pet")
                        .forGetter(Instance::pet),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("total")
                        .forGetter(Instance::total)
                ).apply(instance, PetTamedTrigger::Instance)
            }
        }
    }
}
