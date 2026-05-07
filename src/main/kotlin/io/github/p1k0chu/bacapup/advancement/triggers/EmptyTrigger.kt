package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import java.util.*

/**
 * 'Empty' criterion trigger.
 * Criteria require a player so it's not really empty.
 */
class EmptyTrigger : SimpleCriterionTrigger<EmptyTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer) {
        this.trigger(player) {
            true
        }
    }

    class Instance(private val _player: Optional<ContextAwarePredicate>) : SimpleInstance {
        constructor() : this(Optional.empty())

        override fun player() = _player

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                ).apply(instance, EmptyTrigger::Instance)
            }
        }
    }
}
