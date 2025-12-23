package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClearAllStatusEffectsConsumeEffect.class)
public class ClearAllEffectsConsumeEffectMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    void onConsume(Level world, ItemStack stack, LivingEntity user, CallbackInfoReturnable<Boolean> cir) {
        if(!(user instanceof ServerPlayer)) return;

        if(user.hasEffect(MobEffects.BAD_OMEN) || user.hasEffect(MobEffects.RAID_OMEN)) {
            Criteria.GET_RAID_OF_IT.trigger((ServerPlayer) user);
        }
    }
}
