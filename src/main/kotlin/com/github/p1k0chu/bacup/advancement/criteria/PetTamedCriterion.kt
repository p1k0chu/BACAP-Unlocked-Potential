package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.entity.TamableAnimal
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

class PetTamedCriterion : SimpleCriterionTrigger<PetTamedCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, pet: TamableAnimal, total: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(player, pet, total)
        }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val pet: Optional<EntityPredicate>,
        private val total: Optional<MinMaxBounds.Ints>
    ) : SimpleCriterionTrigger.SimpleInstance {

        override fun player() = _player

        fun matches(player: ServerPlayer, pet: TamableAnimal, total: Int): Boolean {
            return (this.total.isEmpty || this.total.get().matches(total))
                    && (this.pet.isEmpty || this.pet.get().matches(player, pet))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    EntityPredicate.CODEC.optionalFieldOf("pet")
                        .forGetter(Conditions::pet),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("total")
                        .forGetter(Conditions::total)
                ).apply(instance, PetTamedCriterion::Conditions)
            }
        }
    }
}