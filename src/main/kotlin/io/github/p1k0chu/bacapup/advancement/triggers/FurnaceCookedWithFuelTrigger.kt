package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*

class FurnaceCookedWithFuelTrigger : SimpleCriterionTrigger<FurnaceCookedWithFuelTrigger.Instance>() {
    override fun codec() = Instance.CODEC

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
    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val fuel: Optional<ItemPredicate>,
        private val result: Optional<ItemPredicate>
    ) : SimpleInstance {

        override fun player() = _player

        fun matches(fuel: Item, result: ItemStack): Boolean {
            return (this.fuel.isEmpty || this.fuel.get().test(fuel.defaultInstance))
                    && (this.result.isEmpty || this.result.get().test(result))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    ItemPredicate.CODEC.optionalFieldOf("fuel")
                        .forGetter(Instance::fuel),
                    ItemPredicate.CODEC.optionalFieldOf("result")
                        .forGetter(Instance::result)
                ).apply(instance, FurnaceCookedWithFuelTrigger::Instance)
            }
        }
    }
}
