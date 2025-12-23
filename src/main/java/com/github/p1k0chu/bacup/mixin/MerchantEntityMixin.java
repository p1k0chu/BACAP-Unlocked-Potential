package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityTradedEmeralds;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class MerchantEntityMixin {
    @Shadow
    public abstract @Nullable Player getTradingPlayer();

    @Inject(method = "notifyTrade", at = @At("HEAD"))
    void trade(MerchantOffer offer, CallbackInfo ci) {
        if (getTradingPlayer() instanceof ServerPlayer player) {
            ItemStack item = offer.getResult();

            if (item.is(Items.EMERALD)) {
                int total = ((ServerPlayerEntityTradedEmeralds) player).bacup$incrementEmeraldsObtained(item.getCount());
                Criteria.TRADED_FOR_EMERALDS.trigger(player, total);
            }
        }
    }
}
