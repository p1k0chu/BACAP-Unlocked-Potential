package com.github.p1k0chu.bacup.constants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;

public final class BlockPatternConstants {
    private BlockPatternConstants() {
    }

    public static final BlockPattern MEME_TREE_FARM = BlockPatternBuilder.start()
            .aisle("     ", " S   ", "     ", "   S ", "     ")
            .aisle(" GGG ", "GDDDG", "GDGDG", "GDDDG", " GGG ")
            .where('G', BlockInWorld.hasState(BlockPredicate.forBlock(Blocks.GLOWSTONE)))
            .where('D', BlockInWorld.hasState(BlockPredicate.forBlock(Blocks.GRASS_BLOCK)))
            .where('S', BlockInWorld.hasState(BlockPredicate.forBlock(Blocks.OAK_SAPLING)))
            .build();
}
