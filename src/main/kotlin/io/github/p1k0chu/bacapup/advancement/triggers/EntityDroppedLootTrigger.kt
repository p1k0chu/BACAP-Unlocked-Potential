package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.criterion.ContextAwarePredicate
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.SimpleCriterionTrigger
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import java.util.*

class EntityDroppedLootTrigger : SimpleCriterionTrigger<EntityDroppedLootTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, entity: LivingEntity, stack: ItemStack) {
        super.trigger(player) { conditions -> conditions.match(player, entity, stack) }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val entity: Optional<EntityPredicate>,
        private val item: Optional<ItemPredicate>
    ) : SimpleInstance {
        override fun player() = _player

        fun match(player: ServerPlayer, entity: LivingEntity, stack: ItemStack): Boolean {
            return (this.entity.isEmpty || this.entity.get().matches(player, entity))
                    && (this.item.isEmpty || this.item.get().test(stack))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    ContextAwarePredicate.CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    EntityPredicate.CODEC.optionalFieldOf("entity")
                        .forGetter(Instance::entity),
                    ItemPredicate.CODEC
                        .optionalFieldOf("item")
                        .forGetter(Instance::item)
                ).apply(instance, EntityDroppedLootTrigger::Instance)
            }
        }
    }
}
