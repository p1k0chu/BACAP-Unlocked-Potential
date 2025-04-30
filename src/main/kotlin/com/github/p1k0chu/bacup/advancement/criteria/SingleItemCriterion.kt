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

class SingleItemCriterion : AbstractCriterion<SingleItemCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, item: ItemStack) {
        this.trigger(player) { conditions ->
            conditions.matches(item)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val item: List<ItemPredicate>
    ) : AbstractCriterion.Conditions {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(stack: ItemStack): Boolean {
            return item.any { it.test(stack) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    ItemPredicate.CODEC.listOf().optionalFieldOf("item", emptyList())
                        .forGetter(Conditions::item)
                ).apply(instance, SingleItemCriterion::Conditions)
            }
        }
    }
}
