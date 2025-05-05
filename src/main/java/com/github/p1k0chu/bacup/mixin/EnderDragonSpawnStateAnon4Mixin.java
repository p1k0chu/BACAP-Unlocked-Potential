package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net/minecraft/entity/boss/dragon/EnderDragonSpawnState$4")
public class EnderDragonSpawnStateAnon4Mixin {
    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;setSpawnState(Lnet/minecraft/entity/boss/dragon/EnderDragonSpawnState;)V"))
    void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int tick, BlockPos pos, CallbackInfo ci) {
        for (var player : ((EnderDragonFightAccessor) fight).getBossBar().getPlayers()) {
            Criteria.SPAWN_DRAGON_WITH_CRYSTALS.trigger(player, crystals.size());
        }
    }
}
