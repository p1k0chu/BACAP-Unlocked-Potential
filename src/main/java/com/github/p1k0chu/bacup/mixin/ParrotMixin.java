package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Parrot.class)
class ParrotMixin {
    @Unique
    private static final ScopedValue<Optional<Parrot>> parrotScopedValue = ScopedValue.newInstance();

    @WrapOperation(method = "getAmbientSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getAmbient(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent getAmbientSound(Level keys, RandomSource level, Operation<SoundEvent> original) {
        return ScopedValue.where(parrotScopedValue, Optional.of((Parrot) (Object) this)).call(() -> original.call(keys, level));
    }

    @Inject(method = "getImitatedSound", at = @At("HEAD"))
    private static void getImitatedSound(EntityType<?> id, CallbackInfoReturnable<SoundEvent> cir) {
        Optional<Parrot> parrot = parrotScopedValue.orElse(Optional.empty());
        if (parrot.isPresent() && parrot.get().getOwner() instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, id);
        }
    }

    @WrapOperation(method = "imitateNearbyMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getImitatedSound(Lnet/minecraft/world/entity/EntityType;)Lnet/minecraft/sounds/SoundEvent;"))
    private static SoundEvent parrotImitatesType(EntityType<?> entityType, Operation<SoundEvent> original, @Local(argsOnly = true) Entity entity) {
        // entity is either a ServerPlayer or a parrot

        if (entity instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, entityType);
        } else if (((Parrot) entity).getOwner() instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, entityType);
        }

        return original.call(entityType);
    }
}
