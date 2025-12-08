package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.server.level.ServerPlayer
import java.util.*

class EntityDroppedLootCriterion : SimpleCriterionTrigger<EntityDroppedLootCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, entity: LivingEntity, stack: ItemStack) {
        super.trigger(player) { conditions -> conditions.match(player, entity, stack) }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val entity: Optional<EntityPredicate>,
        private val item: Optional<ItemPredicate>
    ) : SimpleCriterionTrigger.SimpleInstance {
        override fun player() = _player

        fun match(player: ServerPlayer, entity: LivingEntity, stack: ItemStack): Boolean {
            return (this.entity.isEmpty || this.entity.get().matches(player, entity))
                    && (this.item.isEmpty || this.item.get().test(stack))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    ContextAwarePredicate.CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    EntityPredicate.CODEC.optionalFieldOf("entity")
                        .forGetter(Conditions::entity),
                    ItemPredicate.CODEC
                        .optionalFieldOf("item")
                        .forGetter(Conditions::item)
                ).apply(instance, EntityDroppedLootCriterion::Conditions)
            }
        }
    }
}
