package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.item.ItemStack
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class FurnaceFuelConsumedCriterion : AbstractCriterion<FurnaceFuelConsumedCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, fuel: ItemStack) {
        this.trigger(player) { conditions ->
            conditions.matches(fuel)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val fuel: Optional<ItemPredicate>
    ) : AbstractCriterion.Conditions {

        override fun player() = _player

        fun matches(stack: ItemStack): Boolean {
            return fuel.isEmpty || fuel.get().test(stack)
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    ItemPredicate.CODEC.optionalFieldOf("fuel")
                        .forGetter(Conditions::fuel)
                ).apply(instance, FurnaceFuelConsumedCriterion::Conditions)
            }
        }
    }
}
