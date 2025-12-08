package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.level.ItemLike
import net.minecraft.world.item.ItemStack
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup.Provider
import net.minecraft.server.level.ServerPlayer
import java.util.*

class SingleItemCriterion : SimpleCriterionTrigger<SingleItemCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, item: ItemStack) {
        this.trigger(player) { conditions ->
            conditions.matches(item)
        }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val item: List<ItemPredicate>
    ) : SimpleCriterionTrigger.SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(stack: ItemStack): Boolean {
            return item.isEmpty() || item.any { it.test(stack) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    ItemPredicate.CODEC.listOf().optionalFieldOf("item", emptyList())
                        .forGetter(Conditions::item)
                ).apply(instance, SingleItemCriterion::Conditions)
            }

            fun items(wrapperLookup: Provider, vararg items: ItemLike): Conditions {
                return Conditions(
                    Optional.empty(),
                    items.map { item ->
                        ItemPredicate.Builder.item()
                            .of(wrapperLookup.lookupOrThrow(Registries.ITEM), item)
                            .build()
                    }
                )
            }
        }
    }
}
