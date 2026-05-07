package io.github.p1k0chu.bacapup.advancement.triggers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.BlockPredicate
import net.minecraft.advancements.predicates.ContextAwarePredicate
import net.minecraft.advancements.predicates.entity.EntityPredicate
import net.minecraft.advancements.triggers.SimpleCriterionTrigger
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup.Provider
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import java.util.*

class SingleBlockTrigger : SimpleCriterionTrigger<SingleBlockTrigger.Instance>() {
    override fun codec() = Instance.CODEC

    fun trigger(player: ServerPlayer, world: ServerLevel, blockPos: BlockPos) {
        this.trigger(player) { conditions ->
            conditions.matches(world, blockPos)
        }
    }

    class Instance(
        private val _player: Optional<ContextAwarePredicate>,
        private val block: List<BlockPredicate>
    ) : SimpleInstance {
        constructor() : this(Optional.empty(), listOf())

        override fun player() = _player

        fun matches(world: ServerLevel, blockPos: BlockPos): Boolean {
            return this.block.isEmpty() || this.block.any { it.matches(world, blockPos) }
        }

        companion object {
            @JvmStatic
            val CODEC: Codec<Instance> = RecordCodecBuilder.create { instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(Instance::player),
                    BlockPredicate.CODEC.listOf().optionalFieldOf("block", emptyList())
                        .forGetter(Instance::block)
                ).apply(instance, SingleBlockTrigger::Instance)
            }

            fun blocks(wrapperLookup: Provider, vararg blocks: Block): Instance {
                return Instance(
                    Optional.empty(),
                    blocks.map { block ->
                        BlockPredicate.Builder.block()
                            .of(wrapperLookup.lookupOrThrow(Registries.BLOCK), block)
                            .build()
                    }
                )
            }

            fun tags(wrapperLookup: Provider, vararg tags: TagKey<Block>): Instance {
                return Instance(
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
