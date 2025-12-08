package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.world.level.block.Block
import net.minecraft.advancements.critereon.BlockPredicate
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup.Provider
import net.minecraft.tags.TagKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.level.ServerLevel
import net.minecraft.core.BlockPos
import java.util.*

class SingleBlockCriterion : SimpleCriterionTrigger<SingleBlockCriterion.Conditions>() {
    override fun codec() = Conditions.CODEC

    fun trigger(player: ServerPlayer, world: ServerLevel, blockPos: BlockPos) {
        this.trigger(player) { conditions ->
            conditions.matches(world, blockPos)
        }
    }

    class Conditions(
        private val _player: Optional<ContextAwarePredicate>,
        private val block: List<BlockPredicate>
    ) : SimpleCriterionTrigger.SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(world: ServerLevel, blockPos: BlockPos): Boolean {
            return this.block.isEmpty() || this.block.any { it.matches(world, blockPos) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    BlockPredicate.CODEC.listOf().optionalFieldOf("block", emptyList())
                        .forGetter(Conditions::block)
                ).apply(instance, SingleBlockCriterion::Conditions)
            }

            fun blocks(wrapperLookup: Provider, vararg blocks: Block): Conditions {
                return Conditions(
                    Optional.empty(),
                    blocks.map { block ->
                        BlockPredicate.Builder.block()
                            .of(wrapperLookup.lookupOrThrow(Registries.BLOCK), block)
                            .build()
                    }
                )
            }

            fun tags(wrapperLookup: Provider, vararg tags: TagKey<Block>): Conditions {
                return Conditions(
                    Optional.empty(),
                    tags.map { tag ->
                        BlockPredicate.Builder.block()
                            .of(wrapperLookup.lookupOrThrow(Registries.BLOCK), tag)
                            .build()
                    }
                )
            }
        }
    }
}
