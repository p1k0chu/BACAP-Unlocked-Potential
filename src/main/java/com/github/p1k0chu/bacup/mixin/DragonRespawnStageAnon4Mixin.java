package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net.minecraft.world.level.dimension.end.DragonRespawnStage$4")
class DragonRespawnStageAnon4Mixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/end/EnderDragonFight;setRespawnStage(Lnet/minecraft/world/level/dimension/end/DragonRespawnStage;)V"))
    private void tick(ServerLevel level, EnderDragonFight fight, List<EndCrystal> crystals, int time, CallbackInfo ci) {
        for (var player : ((EnderDragonFightAccessor) fight).getDragonEvent().getPlayers()) {
            Criteria.SPAWN_DRAGON_WITH_CRYSTALS.trigger(player, crystals.size());
        }
    }
}
