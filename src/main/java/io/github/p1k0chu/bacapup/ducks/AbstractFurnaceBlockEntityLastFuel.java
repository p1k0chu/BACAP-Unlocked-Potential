package io.github.p1k0chu.bacapup.ducks;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface AbstractFurnaceBlockEntityLastFuel {
    @Nullable
    Item bacapup$getLastFuel();
    void bacapup$setLastFuel(@Nullable Item fuel);
}
