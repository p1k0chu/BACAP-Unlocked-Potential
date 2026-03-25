package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import com.github.p1k0chu.bacup.utils.ItemStackUtilsKt;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.THIS_IS_NOT_COOKIE_CLICKER;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
    @Unique
    private static final ItemStack COOKIE_CLICKER_COOKIE = ItemStackUtilsKt.makeTrophyItemStack(
            Items.COOKIE,
            "This Is Not Cookie Clicker",
            "Cookie Clicker Cookie",
            "Where did this cookie come from???",
            TextColor.fromRgb(CommonColors.COSMOS_PINK)
    );

    static {
        COOKIE_CLICKER_COOKIE.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }

    @Shadow
    public abstract void setCarried(ItemStack itemStack);

    @Unique
    private long lastClick = 0;

    @Inject(method = "doClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;updateTutorialInventoryAction(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/ClickAction;)V"))
    void doClick(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci, @Local(name = "clicked") ItemStack slotItem, @Local(name = "carried") ItemStack carriedItem) {
        if (player instanceof ServerPlayer sPlayer) {
            if (slotItem.isEmpty() && carriedItem.isEmpty()) {
                long time = System.currentTimeMillis();
                if (time - lastClick < 300) {
                    if (AdvancementUtils.grant(sPlayer, THIS_IS_NOT_COOKIE_CLICKER)) {
                        setCarried(COOKIE_CLICKER_COOKIE.copy());
                    }
                } else {
                    lastClick = time;
                }
            }
        }
    }
}
