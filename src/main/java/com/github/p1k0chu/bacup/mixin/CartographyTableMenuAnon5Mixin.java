package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.inventory.CartographyTableMenu$5")
public abstract class CartographyTableMenuAnon5Mixin {
    ///  instance of outer class
    @Shadow(aliases = "field_17303")
    @Final
    CartographyTableMenu this$0;

    @Inject(method = "onTake", at = @At("HEAD"))
    void onTakeItem(Player player, ItemStack stack, CallbackInfo ci) {
        if(!(player instanceof ServerPlayer)) {
            return;
        }

        var second = this$0.slots.get(1).getItem();

        if(second.is(Items.GLASS_PANE)) {
            Criteria.MAP_LOCKED.trigger((ServerPlayer) player);
        }
    }
}
