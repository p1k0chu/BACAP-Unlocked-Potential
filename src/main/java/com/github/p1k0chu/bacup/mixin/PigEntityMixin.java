package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pig.class)
public abstract class PigEntityMixin extends LivingEntityMixin {
    @Override
    void damage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source.is(DamageTypes.FALL)) {
            Entity passenger = ((Pig)(Object)this).getFirstPassenger();

            if(passenger instanceof ServerPlayer player) {
                Criteria.WHEN_PIGS_FLY.trigger(player);
            }
        }
    }
}