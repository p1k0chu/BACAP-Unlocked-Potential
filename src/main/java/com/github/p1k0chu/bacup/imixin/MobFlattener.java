package com.github.p1k0chu.bacup.imixin;

import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface MobFlattener {
    @Nullable UUID bacup$whoPlaced(BlockPos pos);
    void bacup$setPlaced(@NonNull BlockPos pos, @NonNull UUID placer);
}
