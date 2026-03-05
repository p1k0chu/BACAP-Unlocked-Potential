package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.advancement.generator.AdventureTabGenerator;
import com.github.p1k0chu.bacup.utils.ItemStackUtilsKt;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
    @Unique
    private static final ItemStack COOKIE_CLICKER_COOKIE = ItemStackUtilsKt.makeTrophyItemStack(
            Items.COOKIE,
            AdventureTabGenerator.TAB_NAME,
            AdventureTabGenerator.THIS_IS_NOT_COOKIE_CLICKER,
            "Cookie Clicker Cookie",
            "Where did this cookie come from???",
            TextColor.fromRgb(CommonColors.COSMOS_PINK)
    );

    @Unique
    private static final Identifier THIS_IS_NOT_COOKIE_CLICKER = Identifier.fromNamespaceAndPath(Main.MOD_ID, String.format("%s/%s", AdventureTabGenerator.TAB_NAME, AdventureTabGenerator.THIS_IS_NOT_COOKIE_CLICKER));

    static {
        COOKIE_CLICKER_COOKIE.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }

    @Shadow
    public abstract void setCarried(ItemStack itemStack);

    @Unique
    private long lastClick = 0;

    @Inject(method = "doClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;updateTutorialInventoryAction(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/ClickAction;)V"), cancellable = true)
    void doClick(int i, int j, ClickType clickType, Player player, CallbackInfo ci, @Local(ordinal = 0) ItemStack slotItem, @Local(ordinal = 1) ItemStack carriedItem) {
        if (player instanceof ServerPlayer sPlayer) {
            if (slotItem.isEmpty() && carriedItem.isEmpty()) {
                long time = System.currentTimeMillis();
                if (time - lastClick < 300) {
                    var advHolder = sPlayer.level().getServer().getAdvancements().get(THIS_IS_NOT_COOKIE_CLICKER);
                    if (advHolder != null) {
                        var playerAdvancements = sPlayer.getAdvancements();
                        if (playerAdvancements.award(advHolder, AdventureTabGenerator.THIS_IS_NOT_COOKIE_CLICKER_CRITERION)) {
                            setCarried(COOKIE_CLICKER_COOKIE.copy());
                            ci.cancel();
                        }
                    }
                } else {
                    lastClick = time;
                }
            }
        }
    }
}
