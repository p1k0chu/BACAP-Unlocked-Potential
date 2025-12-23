package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BellBlock.class)
public class BellBlockMixin {
    @Inject(method = "onProjectileHit", at = @At(value = "RETURN"))
    void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo ci, @Local(ordinal = 0) Player player) {
        if (!(player instanceof ServerPlayer)) return;

        double distance = hit.getLocation().distanceTo(player.position());
        Criteria.BELL_SHOT_FROM_DISTANCE.trigger((ServerPlayer) player, (int) distance);
    }
}
