package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    void destroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack handItem, @Local(ordinal = 0) BlockState brokenBlock) {
        if (handItem.isEmpty() && brokenBlock.is(Blocks.NETHERITE_BLOCK)) {
            Criteria.PLAYER_BREAK_NETHERITE_BLOCK_WITH_FIST.trigger(player);
        }
    }
}
