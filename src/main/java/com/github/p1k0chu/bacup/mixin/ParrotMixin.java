package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.github.p1k0chu.bacup.BacapupScopedValues.parrotImitationScopedValue;

@Mixin(Parrot.class)
class ParrotMixin {
    @WrapOperation(method = "getAmbientSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getAmbient(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent getAmbientSound(Level level, RandomSource random, Operation<SoundEvent> original) {
        return ScopedValue.where(parrotImitationScopedValue, Optional.of((Parrot) (Object) this)).call(() -> original.call(level, random));
    }

    @Inject(method = "getImitatedSound", at = @At("HEAD"))
    private static void getImitatedSound(EntityType<?> id, CallbackInfoReturnable<SoundEvent> cir) {
        Optional<LivingEntity> optional = parrotImitationScopedValue.orElse(Optional.empty());
        optional.ifPresent(entity -> {
            if (entity instanceof ServerPlayer player) {
                Criteria.PARROT_IMITATES.trigger(player, id);
            } else if (entity instanceof Parrot parrot) {
                if (parrot.getOwner() instanceof ServerPlayer player) {
                    Criteria.PARROT_IMITATES.trigger(player, id);
                }
            } else {
                Main.LOGGER.error("During parrot's imitation entity was neigher a Parrot or a Player (is {}). " +
                        "This is a bug that needs to be reported.", entity.getType());
            }
        });
    }

    @Inject(method = "imitateNearbyMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getImitatedSound(Lnet/minecraft/world/entity/EntityType;)Lnet/minecraft/sounds/SoundEvent;"))
    private static void parrotImitatesType(Level level, Entity entity, CallbackInfoReturnable<Boolean> cir, @Local(name = "mob") Mob mob) {
        // entity is either a ServerPlayer or a parrot
        if (entity instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, mob.getType());
        } else if (((Parrot) entity).getOwner() instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, mob.getType());
        }
    }
}
