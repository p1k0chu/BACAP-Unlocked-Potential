package com.github.p1k0chu.bacup.imixin;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public interface AbstractFurnaceBlockEntityLastFuel {
    @Nullable
    Item bacup$getLastFuel();
    void bacup$setLastFuel(@Nullable Item fuel);
}
