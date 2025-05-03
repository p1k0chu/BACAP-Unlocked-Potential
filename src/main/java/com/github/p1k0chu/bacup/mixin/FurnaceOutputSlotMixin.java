package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceOutputSlot.class)
public class FurnaceOutputSlotMixin {
    @Inject(method = "onTakeItem", at = @At("RETURN"))
    void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(stack.isEmpty()) return;

        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (((FurnaceOutputSlot) (Object) this).inventory instanceof AbstractFurnaceBlockEntity furnace) {
                Item fuel = ((AbstractFurnaceBlockEntityLastFuel) furnace).bacup$getLastFuel();
                if (fuel != null) {
                    Main.COOKED_WITH_FUEL.trigger(serverPlayer, fuel, stack);
                }
            }
        }
    }
}
