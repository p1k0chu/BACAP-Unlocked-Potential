package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.RagebaiterStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin {
    @Shadow
    public abstract AngerManagement getAngerManagement();

    @Inject(method = "doPush", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/warden/Warden;increaseAngerAt(Lnet/minecraft/world/entity/Entity;)V"))
    void pushedInvalidateRagebaiter(Entity entity, CallbackInfo ci) {
        if (entity instanceof Player player) {
            var anger = getAngerManagement();
            ((RagebaiterStatus) anger).bacup$setRagebaiter(player, false);
        }
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/warden/Warden;increaseAngerAt(Lnet/minecraft/world/entity/Entity;IZ)V"))
    void hurtServerInvalidateRagebaiter(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.getEntity() instanceof Player player) {
            var anger = getAngerManagement();
            ((RagebaiterStatus) anger).bacup$setRagebaiter(player, false);
        }
    }

}
