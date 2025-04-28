package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.AbstractFurnaceBlockEntityWhoOpened;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenHandlerMixin {
    @Inject(method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/recipe/book/RecipeBookType;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V", at = @At("RETURN"))
    void init(ScreenHandlerType type,
           RecipeType recipeType,
           RegistryKey recipePropertySetKey,
           RecipeBookType category,
           int syncId,
           PlayerInventory playerInventory,
           Inventory inventory,
           PropertyDelegate propertyDelegate,
           CallbackInfo ci) {

        if (inventory instanceof AbstractFurnaceBlockEntity furnace) {
            ((AbstractFurnaceBlockEntityWhoOpened) furnace).setPlayer(playerInventory.player);
        }
    }
}
