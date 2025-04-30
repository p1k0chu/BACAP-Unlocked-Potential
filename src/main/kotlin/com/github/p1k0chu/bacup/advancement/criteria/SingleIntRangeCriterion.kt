package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class SingleIntRangeCriterion : AbstractCriterion<SingleIntRangeCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, amount: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(amount)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val amount: Optional<NumberRange.IntRange>
    ) : AbstractCriterion.Conditions {
        override fun player() = _player

        fun matches(total: Int): Boolean {
            return this.amount.isEmpty || this.amount.get().test(total)
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    NumberRange.IntRange.CODEC.optionalFieldOf("amount")
                        .forGetter(Conditions::amount)
                ).apply(instance, SingleIntRangeCriterion::Conditions)
            }
        }
    }
}