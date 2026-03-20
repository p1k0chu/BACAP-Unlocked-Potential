package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.constants.BlockPatternConstants;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.POST_TREE_TREE_FARM;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (placer instanceof ServerPlayer player
                && Objects.equals(Level.END, world.dimension())
                && BlockPatternConstants.MEME_TREE_FARM.find(world, pos) != null) {
            AdvancementUtils.grant(player, POST_TREE_TREE_FARM);
        }
    }
}
