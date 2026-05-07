package io.github.p1k0chu.bacapup.mixin;

import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers;
import io.github.p1k0chu.bacapup.ducks.AbstractFurnaceBlockEntityLastFuel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceResultSlot.class)
class FurnaceResultSlotMixin {
    @Inject(method = "onTake", at = @At("RETURN"))
    private void onTakeItem(Player player, ItemStack stack, CallbackInfo ci) {
        if(stack.isEmpty()) return;

        if (player instanceof ServerPlayer serverPlayer) {
            if (((FurnaceResultSlot) (Object) this).container instanceof AbstractFurnaceBlockEntity furnace) {
                Item fuel = ((AbstractFurnaceBlockEntityLastFuel) furnace).bacapup$getLastFuel();
                if (fuel != null) {
                    BacapupTriggers.COOKED_WITH_FUEL.trigger(serverPlayer, fuel, stack);
                }
            }
        }
    }
}
