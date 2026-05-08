package io.github.p1k0chu.bacapup.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SulfurCubeArchetype;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.ref.WeakReference;

@Mixin(SulfurCube.class)
abstract class SulfurCubeMixin extends AbstractCubeMob {
    @Unique
    private WeakReference<ServerPlayer> lastInteractor;

    protected SulfurCubeMixin(EntityType<? extends AbstractCubeMob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "playerTouch", at = @At("HEAD"))
    private void setPlayerTouched(Player player, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            lastInteractor = new WeakReference<>(serverPlayer);
        }
    }

    @Inject(method = "knockback", at = @At("HEAD"))
    private void setPlayerKnockbacked(double power, double xd, double zd, DamageSource source, float damage, CallbackInfo ci) {
        if (source.getEntity() instanceof ServerPlayer player) {
            lastInteractor = new WeakReference<>(player);
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void setPlayerInteracted(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player instanceof ServerPlayer serverPlayer) {
            lastInteractor = new WeakReference<>(serverPlayer);
        }
    }

    @Inject(method = "applyContactDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.AFTER))
    private void awardContactKill(Entity entity, CallbackInfo ci, @Local SulfurCubeArchetype.ContactDamage damage) {
        if (!entity.isAlive() && lastInteractor != null) {
            var player = lastInteractor.get();
            if (player != null) {
                BacapupTriggers.SULFUR_CUBE_CONTACT_KILL.trigger(player, entity, damage);
            }
        }
    }
}
