package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityWhoOpened;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenHandlerMixin {
    @Shadow
    @Final
    Inventory inventory;

    @Inject(method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/recipe/book/RecipeBookType;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V", at = @At("RETURN"))
    void init(ScreenHandlerType<?> type,
              RecipeType<?> recipeType,
              RegistryKey<?> recipePropertySetKey,
              RecipeBookType category,
              int syncId,
              PlayerInventory playerInventory,
              Inventory inventory,
              PropertyDelegate propertyDelegate,
              CallbackInfo ci) {

        if (inventory instanceof AbstractFurnaceBlockEntity furnace) {
            ((AbstractFurnaceBlockEntityWhoOpened) furnace).bacup$setPlayer(playerInventory.player.getUuid());
        }
    }

    @Inject(method = "quickMove", at = @At("RETURN"))
    void quickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot != 2) return;

        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (this.inventory instanceof AbstractFurnaceBlockEntity furnace) {
                ItemStack itemStack = cir.getReturnValue();
                Item fuel = ((AbstractFurnaceBlockEntityLastFuel) furnace).bacup$getLastFuel();

                if (fuel != null) {
                    Criteria.COOKED_WITH_FUEL.trigger(serverPlayer, fuel, itemStack);
                }
            }
        }
    }
}
