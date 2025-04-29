package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
public abstract class GrindstoneScreenHandlerAnon4Mixin {
    ///  instance of outer class
    @Shadow
    @Final
    GrindstoneScreenHandler field_16780;

    @Unique
    private boolean noExperience(ItemStack stack) {
        var enchantments = EnchantmentHelper.getEnchantments(stack);

        for (var entry : enchantments.getEnchantmentEntries()) {
            RegistryEntry<Enchantment> registryEntry = entry.getKey();

            if (!registryEntry.isIn(EnchantmentTags.CURSE)) {
                return false;
            }
        }

        return true;
    }

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity)) return;

        GrindstoneScreenHandlerAccessor accessor = (GrindstoneScreenHandlerAccessor) field_16780;

        if (noExperience(accessor.getInput().getStack(0))) {
            if (noExperience(accessor.getInput().getStack(1))) {
                return;
            }
        }

        Main.DISENCHANT_GRINDSTONE.trigger((ServerPlayerEntity) player);
    }
}
