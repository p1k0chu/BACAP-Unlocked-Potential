package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net.minecraft.world.level.dimension.end.DragonRespawnAnimation$4")
public class EnderDragonSpawnStateAnon4Mixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/end/EndDragonFight;setRespawnStage(Lnet/minecraft/world/level/dimension/end/DragonRespawnAnimation;)V"))
    void tick(ServerLevel world, EndDragonFight fight, List<EndCrystal> crystals, int tick, BlockPos pos, CallbackInfo ci) {
        for (var player : ((EnderDragonFightAccessor) fight).getDragonEvent().getPlayers()) {
            Criteria.SPAWN_DRAGON_WITH_CRYSTALS.trigger(player, crystals.size());
        }
    }
}
