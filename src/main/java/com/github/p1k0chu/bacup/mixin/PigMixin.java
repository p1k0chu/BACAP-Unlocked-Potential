package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pig.class)
abstract class PigMixin extends LivingEntityMixin {
    private PigMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onDeath(DamageSource damageSource, CallbackInfo ci) {
        super.onDeath(damageSource, ci);

        if(damageSource.is(DamageTypes.FALL)) {
            if(getFirstPassenger() instanceof ServerPlayer player) {
                Criteria.WHEN_PIGS_FLY.trigger(player);
            }
        }
    }
}
