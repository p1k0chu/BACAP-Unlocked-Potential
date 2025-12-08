package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityWhoOpened;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceMenu.class)
public class AbstractFurnaceMenuMixin {
    @Shadow
    @Final
    Container container;

    @Inject(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At("RETURN"))
    void init(MenuType<?> type,
              RecipeType<?> recipeType,
              ResourceKey<?> recipePropertySetKey,
              RecipeBookType category,
              int syncId,
              Inventory playerInventory,
              Container inventory,
              ContainerData propertyDelegate,
              CallbackInfo ci) {

        if (inventory instanceof AbstractFurnaceBlockEntity furnace) {
            ((AbstractFurnaceBlockEntityWhoOpened) furnace).bacup$setPlayer(playerInventory.player.getUUID());
        }
    }

    @Inject(method = "quickMoveStack", at = @At("RETURN"))
    void quickMove(Player player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot != 2) return;

        if (player instanceof ServerPlayer serverPlayer) {
            if (this.container instanceof AbstractFurnaceBlockEntity furnace) {
                ItemStack itemStack = cir.getReturnValue();
                Item fuel = ((AbstractFurnaceBlockEntityLastFuel) furnace).bacup$getLastFuel();

                if (fuel != null) {
                    Criteria.COOKED_WITH_FUEL.trigger(serverPlayer, fuel, itemStack);
                }
            }
        }
    }
}
