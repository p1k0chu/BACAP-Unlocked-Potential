package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
public abstract class GrindstoneScreenHandlerAnon4Mixin {
    ///  instance of outer class
    @Shadow
    @Final
    GrindstoneScreenHandler field_16780;

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(!(player instanceof ServerPlayerEntity)) return;

        GrindstoneScreenHandlerAccessor accessor = (GrindstoneScreenHandlerAccessor) field_16780;

        boolean hasEnchantments = EnchantmentHelper.hasEnchantments(accessor.getInput().getStack(0));
        hasEnchantments = hasEnchantments || EnchantmentHelper.hasEnchantments(accessor.getInput().getStack(1));

        if (hasEnchantments) {
            Main.DISENCHANT_GRINDSTONE.trigger((ServerPlayerEntity) player);
        }
    }
}
