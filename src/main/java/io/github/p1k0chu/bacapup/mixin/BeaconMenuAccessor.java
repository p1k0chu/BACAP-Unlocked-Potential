package io.github.p1k0chu.bacapup.mixin;

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
