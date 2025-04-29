package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class FurnaceCookedWithFuelCriterion : AbstractCriterion<FurnaceCookedWithFuelCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, fuel: Item, result: ItemStack) {
        this.trigger(player) { conditions ->
            conditions.matches(fuel, result)
        }
    }

    /**
     * @param fuel last used fuel when player took the result out of furnace.
     *
     * DO NOT TEST FOR QUANTITY, it will always be one
     *
     * @param result the stack player took out of furnace at once
     */
    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val fuel: Optional<ItemPredicate>,
        private val result: Optional<ItemPredicate>
    ) : AbstractCriterion.Conditions {

        override fun player() = _player

        fun matches(fuel: Item, result: ItemStack): Boolean {
            return (this.fuel.isEmpty || this.fuel.get().test(fuel.defaultStack))
                    && (this.result.isEmpty || this.result.get().test(result))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    ItemPredicate.CODEC.optionalFieldOf("fuel")
                        .forGetter(Conditions::fuel),
                    ItemPredicate.CODEC.optionalFieldOf("result")
                        .forGetter(Conditions::result)
                ).apply(instance, FurnaceCookedWithFuelCriterion::Conditions)
            }
        }
    }
}
