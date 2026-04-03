package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.ConduitPowerGetter;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.CONDUEL;

@Mixin(ConduitBlockEntity.class)
class ConduitBlockEntityMixin implements ConduitPowerGetter {
    @Shadow
    @Final
    private List<BlockPos> effectBlocks;

    @Inject(method = "updateAndAttackTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.AFTER))
    private static void hurtTarget(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, ConduitBlockEntity conduitBlockEntity, boolean bl, CallbackInfo ci, @Local(name = "targetEntity") LivingEntity target) {
        if (target.isDeadOrDying()) {
            int power = ((ConduitPowerGetter) conduitBlockEntity).bacup$getPower();
            var players = getPlayersAffected(serverLevel, blockPos, power);
            for (ServerPlayer player : players) {
                AdvancementUtils.grant(player, CONDUEL);
            }
        }
    }

    ///  returns player in range of conduit effects
    @Unique
    private static List<ServerPlayer> getPlayersAffected(Level level, BlockPos conduitPos, int power) {
        int distance = power / 7 * 16;
        AABB box = new AABB(conduitPos).inflate(distance).expandTowards(0.0, level.getHeight(), 0.0);
        return level.getEntitiesOfClass(ServerPlayer.class, box);
    }

    @Override
    public int bacup$getPower() {
        return effectBlocks.size();
    }
}
