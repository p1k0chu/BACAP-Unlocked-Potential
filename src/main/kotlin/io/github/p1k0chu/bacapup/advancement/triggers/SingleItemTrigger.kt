package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.predicates.ItemPredicate
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.core.HolderLookup.Provider
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import java.util.*

class SingleItemTrigger : SimpleCriterionTrigger<SingleItemTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, item: ItemStack) {
        this.trigger(player) { conditions ->
            conditions.matches(item)
        }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val item: List<ItemPredicate>
    ) : SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(stack: ItemStack): Boolean {
            return item.isEmpty() || item.any { it.test(stack) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    ItemPredicate.CODEC.listOf().optionalFieldOf("item", emptyList())
                        .forGetter(Instance::item)
                ).apply(instance, SingleItemTrigger::Instance)
            }

            fun items(wrapperLookup: Provider, vararg items: ItemLike): Instance {
                return Instance(
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
