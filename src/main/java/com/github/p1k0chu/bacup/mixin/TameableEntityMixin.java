package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityPetsTamedCounter;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public class TameableEntityMixin {
    @Inject(method = "setTamedBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/TameAnimalCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/passive/AnimalEntity;)V"))
    void setTamedBy(PlayerEntity player, CallbackInfo ci, @Local ServerPlayerEntity serverPlayerEntity) {
        TameableEntity tamed = (TameableEntity) (Object) this;
        EntityType<?> type = (tamed).getType();

        if(!ServerPlayerEntityPetsTamedCounter.isTracked(type)) {
            return;
        }

        int total = ((ServerPlayerEntityPetsTamedCounter) serverPlayerEntity).bacup$increment(type);

        Main.PET_TAMED.trigger(serverPlayerEntity, tamed, total);
    }
}