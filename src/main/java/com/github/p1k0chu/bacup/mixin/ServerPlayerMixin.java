package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.github.p1k0chu.bacup.BacapupScopedValues.parrotImitationScopedValue;
import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.INTENTIONAL_ADVANCEMENT_DESIGN;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
    @Inject(method = "die", at = @At("HEAD"))
    private void intentionalAdvancementDesignDeath(DamageSource source, CallbackInfo ci) {
        if (source.is(DamageTypes.BAD_RESPAWN_POINT)) {
            AdvancementUtils.grant((ServerPlayer) (Object) this, INTENTIONAL_ADVANCEMENT_DESIGN);
        }
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void intentionalAdvancementDesignHardcore(ServerLevel level, DamageSource source, float f, CallbackInfoReturnable<Boolean> cir) {
        if (level.getLevelData().isHardcore() && cir.getReturnValue() && source.is(DamageTypes.BAD_RESPAWN_POINT)) {
            AdvancementUtils.grant((ServerPlayer) (Object) this, INTENTIONAL_ADVANCEMENT_DESIGN);
        }
    }

    @WrapOperation(method = "playShoulderEntityAmbientSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getAmbient(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent parrotImitatesAmbient(Level level, RandomSource random, Operation<SoundEvent> original) {
        return ScopedValue.where(parrotImitationScopedValue, Optional.of((ServerPlayer) (Object) this)).call(() -> original.call(level, random));
    }
}
