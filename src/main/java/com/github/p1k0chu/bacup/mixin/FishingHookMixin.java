package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.RagebaiterStatus;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.RAGE_BAITER;

@Mixin(FishingHook.class)
class FishingHookMixin {
    @Inject(method = "pullEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void checkRagebaiter(Entity pulledEntity, CallbackInfo ci, @Local(name = "owner") Entity owner) {
        if (pulledEntity instanceof Warden warden && owner instanceof ServerPlayer player) {
            var anger = warden.getAngerManagement();
            if (anger.getActiveAnger(player) >= 150 && ((RagebaiterStatus) anger).bacup$isRagebaiter(player)) {
                AdvancementUtils.grant(player, RAGE_BAITER);
            }
        }
    }
}
