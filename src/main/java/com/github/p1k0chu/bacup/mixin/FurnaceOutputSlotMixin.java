package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceResultSlot.class)
public class FurnaceOutputSlotMixin {
    @Inject(method = "onTake", at = @At("RETURN"))
    void onTakeItem(Player player, ItemStack stack, CallbackInfo ci) {
        if(stack.isEmpty()) return;

        if (player instanceof ServerPlayer serverPlayer) {
            if (((FurnaceResultSlot) (Object) this).container instanceof AbstractFurnaceBlockEntity furnace) {
                Item fuel = ((AbstractFurnaceBlockEntityLastFuel) furnace).bacup$getLastFuel();
                if (fuel != null) {
                    Criteria.COOKED_WITH_FUEL.trigger(serverPlayer, fuel, stack);
                }
            }
        }
    }
}
