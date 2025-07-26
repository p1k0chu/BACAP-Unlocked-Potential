package com.github.p1k0chu.bacup.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;dropStack(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;"))
    void dropEquipmentDropStack(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci, @Local ItemStack itemStack) {
    }
}
