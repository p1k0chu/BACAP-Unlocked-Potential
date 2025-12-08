package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

/**
 * 'Empty' criterion trigger.
 * Criteria require a player so it's not really empty.
 */
class EmptyCriterion : SimpleCriterionTrigger<EmptyCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer) {
        this.trigger(player) {
            true
        }
    }

    class Conditions(private val _player: Optional<ContextAwarePredicate>) : SimpleCriterionTrigger.SimpleInstance {
        constructor() : this(Optional.empty())

        override fun player() = _player

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                ).apply(instance, EmptyCriterion::Conditions)
            }
        }
    }
}