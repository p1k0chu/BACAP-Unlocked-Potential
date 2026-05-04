package io.github.p1k0chu.bacapup.imixin;

import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface MobFlattener {
    @Nullable UUID bacapup$whoPlaced(BlockPos pos);
    void bacapup$setPlaced(@NonNull BlockPos pos, @NonNull UUID placer);
}
