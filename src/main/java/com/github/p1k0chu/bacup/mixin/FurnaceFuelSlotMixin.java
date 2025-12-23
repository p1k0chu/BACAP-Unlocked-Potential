package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin extends SlotMixin {
    @Override
    void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        super.onTake(player, itemStack, ci);

        if (player instanceof ServerPlayer serverPlayer && itemStack.is(Items.WATER_BUCKET)) {
            Criteria.FURNACE_TOOK_WATER_BUCKET_FUEL.trigger(serverPlayer);
        }
    }
}
