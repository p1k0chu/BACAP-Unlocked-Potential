package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderEntityMixin extends MobEntityMixin {
    @Override
    void dropEquipmentDropStack(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci, ItemStack itemStack) {
        super.dropEquipmentDropStack(world, source, causedByPlayer, ci, itemStack);

        if (source.getAttacker() instanceof ServerPlayerEntity player) {
            Criteria.WANDERING_TRADER_DROPPED_ITEM.trigger(player, (LivingEntity) (Object) this, itemStack);
        }
    }
}
