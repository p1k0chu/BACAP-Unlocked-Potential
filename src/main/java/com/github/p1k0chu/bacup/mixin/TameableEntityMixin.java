package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityPetsTamedCounter;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
public class TameableEntityMixin {
    @Inject(method = "tame", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/criterion/TameAnimalTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/animal/Animal;)V"))
    void setTamedBy(Player player, CallbackInfo ci, @Local ServerPlayer serverPlayerEntity) {
        TamableAnimal tamed = (TamableAnimal) (Object) this;
        EntityType<?> type = (tamed).getType();

        if(!ServerPlayerEntityPetsTamedCounter.isTracked(type)) {
            return;
        }

        int total = ((ServerPlayerEntityPetsTamedCounter) serverPlayerEntity).bacup$increment(type);

        Criteria.PET_TAMED.trigger(serverPlayerEntity, tamed, total);
    }
}