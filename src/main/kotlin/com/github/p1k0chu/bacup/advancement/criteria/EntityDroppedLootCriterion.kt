package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class EntityDroppedLootCriterion : AbstractCriterion<EntityDroppedLootCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, entity: LivingEntity, stack: ItemStack) {
        super.trigger(player) { conditions -> conditions.match(player, entity, stack) }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val entity: Optional<EntityPredicate>,
        private val item: Optional<ItemPredicate>
    ) : AbstractCriterion.Conditions {
        override fun player() = _player

        fun match(player: ServerPlayerEntity, entity: LivingEntity, stack: ItemStack): Boolean {
            return (this.entity.isEmpty || this.entity.get().test(player, entity))
                    && (this.item.isEmpty || this.item.get().test(stack))
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    LootContextPredicate.CODEC.optionalFieldOf("player")
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
