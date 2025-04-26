package com.github.p1k0chu.bacup.advancement.criteria

import com.github.p1k0chu.bacup.Main
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.item.ItemStack
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class CatGiftReceivedCriterion : AbstractCriterion<CatGiftReceivedCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, stack: ItemStack) {
        super.trigger(player) { conditions -> conditions.match(stack) }
    }

    class Conditions(private val _player: Optional<LootContextPredicate>, private val giftItem: List<ItemPredicate>) : AbstractCriterion.Conditions {
        override fun player() = _player

        fun match(stack: ItemStack): Boolean {
            return giftItem.isEmpty() || giftItem.stream().anyMatch { itemPredicate -> itemPredicate.test(stack) }
        }

        class Builder {
            private var player: LootContextPredicate? = null
            private val giftItem: MutableList<ItemPredicate> = mutableListOf()

            fun build(): AdvancementCriterion<Conditions> {
                return Main.CAT_GIFT_RECEIVED.create(
                    Conditions(
                        Optional.ofNullable(
                            player
                        ), giftItem
                    )
                )
            }

            fun items(vararg items: ItemPredicate): Builder {
                giftItem.addAll(items)
                return this
            }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    LootContextPredicate.CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    ItemPredicate.CODEC
                        .listOf()
                        .optionalFieldOf("gift_item", Collections.emptyList())
                        .forGetter(Conditions::giftItem)
                ).apply(instance, CatGiftReceivedCriterion::Conditions)
            }

            fun builder(block: Builder.() -> Unit): AdvancementCriterion<Conditions> {
                return Builder().apply(block).build()
            }
        }
    }
}
