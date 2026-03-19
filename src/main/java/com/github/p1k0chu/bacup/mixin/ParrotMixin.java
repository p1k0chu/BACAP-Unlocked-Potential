package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(Parrot.class)
public class ParrotMixin {
    @Shadow
    @Final
    static Map<EntityType<?>, SoundEvent> MOB_SOUND_MAP;

    @Unique
    private final static Map<SoundEvent, EntityType<?>> MOB_SOUND_MAP_INVERSE = MOB_SOUND_MAP.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (l, r) -> l));

    @Inject(method = "getAmbientSound", at = @At("HEAD"))
    void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        var sound = cir.getReturnValue();

        if (((Parrot) (Object) this).getOwner() instanceof ServerPlayer player) {
            var mobType = MOB_SOUND_MAP_INVERSE.get(sound);
            if (mobType != null) {
                Criteria.PARROT_IMITATES.trigger(player, mobType);
            }
        }
    }

    @WrapOperation(method = "imitateNearbyMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/parrot/Parrot;getImitatedSound(Lnet/minecraft/world/entity/EntityType;)Lnet/minecraft/sounds/SoundEvent;"))
    private static SoundEvent parrotImitatesType(EntityType<?> entityType, Operation<SoundEvent> original, @Local Entity entity) {
        // entity is either a ServerPlayer or a parrot

        if (entity instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, entityType);
        } else if (((Parrot) entity).getOwner() instanceof ServerPlayer player) {
            Criteria.PARROT_IMITATES.trigger(player, entityType);
        }

        return original.call(entityType);
    }
}
