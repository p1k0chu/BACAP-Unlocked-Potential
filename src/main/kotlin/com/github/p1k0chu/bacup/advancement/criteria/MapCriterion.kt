package com.github.p1k0chu.bacup.advancement.criteria

import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import java.util.*

class MapCriterion : SimpleCriterionTrigger<MapCriterion.Instance>() {
    override fun codec(): Codec<Instance> = Instance.CODEC

    fun trigger(player: ServerPlayer, item: ItemStack) {
        trigger(player) { it.matches(item) }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val mapState: Optional<MapStatePredicate>
    ) : SimpleInstance {
        override fun player(): Optional<ContextAwarePredicate> = _player

        fun matches(item: ItemStack): Boolean {
            val id = item.get(DataComponents.MAP_ID)
            return (mapState.isEmpty || (id != null && mapState.get().matches(id)))
        }

        companion object {
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    MapStatePredicate.CODEC.optionalFieldOf("map_state")
                        .forGetter(Instance::mapState)
                ).apply(instance, ::Instance)
            }
        }
    }
}