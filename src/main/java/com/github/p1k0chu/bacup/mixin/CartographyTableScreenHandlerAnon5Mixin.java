package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/screen/CartographyTableScreenHandler$5")
public class CartographyTableScreenHandlerAnon5Mixin {
    @Shadow()
    @Final
    CartographyTableScreenHandler field_17303;

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(!(player instanceof ServerPlayerEntity)) {
            return;
        }

        var second = field_17303.slots.get(1).getStack();

        if(second.isOf(Items.GLASS_PANE)) {
            Main.MAP_LOCKED.trigger((ServerPlayerEntity) player);
        }
    }
}
