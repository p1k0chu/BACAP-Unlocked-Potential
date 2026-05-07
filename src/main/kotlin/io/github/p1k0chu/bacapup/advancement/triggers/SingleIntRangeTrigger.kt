package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import java.util.*

class SingleIntRangeTrigger : SimpleCriterionTrigger<SingleIntRangeTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, amount: Int) {
        this.trigger(player) { conditions ->
            conditions.matches(amount)
        }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val amount: Optional<MinMaxBounds.Ints>
    ) : SimpleInstance {
        override fun player() = _player

        fun matches(total: Int): Boolean {
            return this.amount.isEmpty || this.amount.get().matches(total)
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("amount")
                        .forGetter(Instance::amount)
                ).apply(instance, SingleIntRangeTrigger::Instance)
            }
        }
    }
}