package io.github.p1k0chu.bacapup.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GrindstoneMenu.class)
public interface GrindstoneMenuAccessor {
    @Accessor
    Container getRepairSlots();
}
