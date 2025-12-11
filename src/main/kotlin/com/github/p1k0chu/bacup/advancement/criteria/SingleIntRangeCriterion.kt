package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

class SingleIntRangeCriterion : SimpleCriterionTrigger<SingleIntRangeCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, amount: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(amount)
        }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val amount: Optional<MinMaxBounds.Ints>
    ) : SimpleCriterionTrigger.SimpleInstance {
        override fun player() = _player

        fun matches(total: Int): Boolean {
            return this.amount.isEmpty || this.amount.get().matches(total)
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("amount")
                        .forGetter(Conditions::amount)
                ).apply(instance, SingleIntRangeCriterion::Conditions)
            }
        }
    }
}