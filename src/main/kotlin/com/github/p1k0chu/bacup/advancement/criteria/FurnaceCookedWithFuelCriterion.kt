package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

class FurnaceCookedWithFuelCriterion : SimpleCriterionTrigger<FurnaceCookedWithFuelCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, fuel: Item, result: ItemStack) {
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
        private val _player: Optional<ContextAwarePredicate>,
        private val fuel: Optional<ItemPredicate>,
        private val result: Optional<ItemPredicate>
    ) : SimpleCriterionTrigger.SimpleInstance {

        override fun player() = _player

        fun matches(fuel: Item, result: ItemStack): Boolean {
            return (this.fuel.isEmpty || this.fuel.get().test(fuel.defaultInstance))
                    && (this.result.isEmpty || this.result.get().test(result))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
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
