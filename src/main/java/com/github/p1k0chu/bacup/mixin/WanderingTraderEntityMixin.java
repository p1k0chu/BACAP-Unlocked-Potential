package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTrader.class)
public class WanderingTraderEntityMixin extends MobEntityMixin {
    @Override
    void dropEquipmentDropStack(ServerLevel world, DamageSource source, boolean causedByPlayer, CallbackInfo ci, ItemStack itemStack) {
        super.dropEquipmentDropStack(world, source, causedByPlayer, ci, itemStack);

        if (source.getEntity() instanceof ServerPlayer player) {
            Criteria.WANDERING_TRADER_DROPPED_ITEM.trigger(player, (LivingEntity) (Object) this, itemStack);
        }
    }
}
