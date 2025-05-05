package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BellBlock.class)
public class BellBlockMixin {
    @Inject(method = "onProjectileHit", at = @At(value = "RETURN"))
    void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci, @Local(ordinal = 0) PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity)) return;

        double distance = hit.getPos().distanceTo(player.getPos());
        Criteria.BELL_SHOT_FROM_DISTANCE.trigger((ServerPlayerEntity) player, (int) distance);
    }
}
