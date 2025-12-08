package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$4")
public abstract class GrindstoneMenuAnon4Mixin {
    ///  instance of outer class
    @Shadow(aliases = "field_16780")
    @Final
    GrindstoneMenu outerThis;

    @Unique
    private boolean noExperience(ItemStack stack) {
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);

        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> registryEntry = entry.getKey();

            if (!registryEntry.is(EnchantmentTags.CURSE)) {
                return false;
            }
        }

        return true;
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    void onTakeItem(Player player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayer)) return;

        GrindstoneMenuAccessor accessor = (GrindstoneMenuAccessor) outerThis;

        if (noExperience(accessor.getRepairSlots().getItem(0))) {
            if (noExperience(accessor.getRepairSlots().getItem(1))) {
                return;
            }
        }

        Criteria.DISENCHANT_GRINDSTONE.trigger((ServerPlayer) player);
    }
}
