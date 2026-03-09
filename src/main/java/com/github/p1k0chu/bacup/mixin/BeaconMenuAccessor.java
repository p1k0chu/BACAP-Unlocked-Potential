package com.github.p1k0chu.bacup.mixin;

import net.minecraft.world.inventory.BeaconMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconMenu.class)
public interface BeaconMenuAccessor {
    @Accessor
    @NotNull
    BeaconMenu.PaymentSlot getPaymentSlot();
}
