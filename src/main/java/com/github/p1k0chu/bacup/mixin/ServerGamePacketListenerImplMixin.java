package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleSetBeaconPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/BeaconMenu;updateEffects(Ljava/util/Optional;Ljava/util/Optional;)V"))
    void updateBeaconEffects(ServerboundSetBeaconPacket serverboundSetBeaconPacket, CallbackInfo ci, @Local BeaconMenu menu) {
        ItemStack stack = ((BeaconMenuAccessor) menu).getPaymentSlot().getItem();

        if (!stack.isEmpty()) {
            Criteria.BEACON_CONSUMED_PAYMENT.trigger(player, stack.copyWithCount(1));
        }
    }
}
