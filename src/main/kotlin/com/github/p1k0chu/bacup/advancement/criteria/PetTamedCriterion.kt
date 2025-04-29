package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PetTamedCriterion : AbstractCriterion<PetTamedCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, pet: TameableEntity, total: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(player, pet, total)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val pet: Optional<EntityPredicate>,
        private val total: Optional<NumberRange.IntRange>
    ) : AbstractCriterion.Conditions {

        override fun player() = _player

        fun matches(player: ServerPlayerEntity, pet: TameableEntity, total: Int): Boolean {
            return (this.total.isEmpty || this.total.get().test(total))
                    && (this.pet.isEmpty || this.pet.get().test(player, pet));
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    EntityPredicate.CODEC.optionalFieldOf("pet")
                        .forGetter(Conditions::pet),
                    NumberRange.IntRange.CODEC.optionalFieldOf("total")
                        .forGetter(Conditions::total)
                ).apply(instance, PetTamedCriterion::Conditions)
            }
        }
    }
}