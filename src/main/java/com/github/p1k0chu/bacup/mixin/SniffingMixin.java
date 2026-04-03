package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.RagebaiterStatus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.Sniffing;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sniffing.class)
class SniffingMixin {
    @Inject(method = "lambda$stop$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/warden/Warden;increaseAngerAt(Lnet/minecraft/world/entity/Entity;)V"))
    private static void invalidateRagebaiter(Warden warden, LivingEntity livingEntity, CallbackInfo ci) {
        if (livingEntity instanceof Player player) {
            var anger = warden.getAngerManagement();
            ((RagebaiterStatus) anger).bacup$setRagebaiter(player, false);
        }
    }
}
