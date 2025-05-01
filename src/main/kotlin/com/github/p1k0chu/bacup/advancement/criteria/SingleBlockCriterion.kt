package com.github.p1k0chu.bacup.advancement.criteria

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.block.Block
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.predicate.BlockPredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LootContextPredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SingleBlockCriterion : AbstractCriterion<SingleBlockCriterion.Conditions>() {
    override fun getConditionsCodec() = Conditions.CODEC

    fun trigger(player: ServerPlayerEntity, world: ServerWorld, blockPos: BlockPos) {
        this.trigger(player) { conditions ->
            conditions.matches(world, blockPos)
        }
    }

    class Conditions(
        private val _player: Optional<LootContextPredicate>,
        private val block: List<BlockPredicate>
    ) : AbstractCriterion.Conditions {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(world: ServerWorld, blockPos: BlockPos): Boolean {
            return this.block.any { it.test(world, blockPos) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Conditions> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player")
                        .forGetter(Conditions::player),
                    BlockPredicate.CODEC.listOf().optionalFieldOf("block", emptyList())
                        .forGetter(Conditions::block)
                ).apply(instance, SingleBlockCriterion::Conditions)
            }

            fun blocks(wrapperLookup: WrapperLookup, vararg blocks: Block): Conditions {
                return Conditions(
                    Optional.empty(),
                    blocks.map { block ->
                        BlockPredicate.Builder.create()
                            .blocks(wrapperLookup.getOrThrow(RegistryKeys.BLOCK), block)
                            .build()
                    }
                )
            }
        }
    }
}
