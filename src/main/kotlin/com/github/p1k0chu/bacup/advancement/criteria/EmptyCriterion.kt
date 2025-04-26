package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

/**
 * 'Empty' criterion trigger.
 * Criteria require a player so it's not really empty.
 */
class EmptyCriterion : AbstractCriterion<EmptyCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity) {
        this.trigger(player) {
            true
        }
    }

    class Conditions(private val _player: Optional<LootContextPredicate>) : AbstractCriterion.Conditions {
        constructor() : this(Optional.empty())

        override fun player() = _player

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                ).apply(instance, EmptyCriterion::Conditions)
            }
        }
    }
}