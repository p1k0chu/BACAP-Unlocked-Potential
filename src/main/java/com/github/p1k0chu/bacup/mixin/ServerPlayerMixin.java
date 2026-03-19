package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.BacupPersistentState;
import com.github.p1k0chu.bacup.PlayerData;
import com.github.p1k0chu.bacup.advancement.generator.EndTabGenerator;
import com.github.p1k0chu.bacup.imixin.ServerPlayerPetsTamedCounter;
import com.github.p1k0chu.bacup.imixin.ServerPlayerTradedEmeralds;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements ServerPlayerPetsTamedCounter, ServerPlayerTradedEmeralds {
    @Unique
    private static final Identifier INTENTIONAL_ADVANCEMENT_DESIGN = AdvancementUtils.id(
            EndTabGenerator.TAB_NAME, EndTabGenerator.INTENTIONAL_ADVANCEMENT_DESIGN
    );

    @Override
    public int bacup$increment(EntityType<?> entityType) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        return state.getPetsTamed().compute(entityType, (key, value) -> value == null ? 1 : value + 1);
    }

    @Override
    public int bacup$incrementEmeraldsObtained(int amount) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        state.setEmeraldsObtained(state.getEmeraldsObtained() + amount);

        return state.getEmeraldsObtained();
    }

    @Inject(method = "die", at = @At("HEAD"))
    void intentionalAdvancementDesignDeath(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.is(DamageTypes.BAD_RESPAWN_POINT)) {
            AdvancementUtils.grant((ServerPlayer) (Object) this, INTENTIONAL_ADVANCEMENT_DESIGN);
        }
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    void intentionalAdvancementDesignHardcore(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (serverLevel.getLevelData().isHardcore() && cir.getReturnValue() && damageSource.is(DamageTypes.BAD_RESPAWN_POINT)) {
            AdvancementUtils.grant((ServerPlayer) (Object) this, INTENTIONAL_ADVANCEMENT_DESIGN);
        }
    }
}
