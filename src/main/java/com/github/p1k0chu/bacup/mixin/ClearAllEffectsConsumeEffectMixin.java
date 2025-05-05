package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ClearAllEffectsConsumeEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClearAllEffectsConsumeEffect.class)
public class ClearAllEffectsConsumeEffectMixin {
    @Inject(method = "onConsume", at = @At("HEAD"))
    void onConsume(World world, ItemStack stack, LivingEntity user, CallbackInfoReturnable<Boolean> cir) {
        if(!(user instanceof ServerPlayerEntity)) return;

        if(user.hasStatusEffect(StatusEffects.BAD_OMEN) || user.hasStatusEffect(StatusEffects.RAID_OMEN)) {
            Criteria.GET_RAID_OF_IT.trigger((ServerPlayerEntity) user);
        }
    }
}
