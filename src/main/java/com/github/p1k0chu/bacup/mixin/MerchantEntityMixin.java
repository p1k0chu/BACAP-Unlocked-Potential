package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityTradedEmeralds;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.TradeOffer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin {
    @Shadow
    public abstract @Nullable PlayerEntity getCustomer();

    @Inject(method = "trade", at = @At("HEAD"))
    void trade(TradeOffer offer, CallbackInfo ci) {
        if (getCustomer() instanceof ServerPlayerEntity player) {
            ItemStack item = offer.getSellItem();

            if (item.isOf(Items.EMERALD)) {
                int total = ((ServerPlayerEntityTradedEmeralds) player).bacup$incrementEmeraldsObtained(item.getCount());
                Criteria.TRADED_FOR_EMERALDS.trigger(player, total);
            }
        }
    }
}
