package io.github.p1k0chu.bacapup.mixin;

import io.github.p1k0chu.bacapup.imixin.MobFlattener;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(ChunkAccess.class)
abstract class ChunkAccessMixin implements MobFlattener {
    @Unique
    private final Long2ObjectMap<UUID> pos2uuid = new Long2ObjectArrayMap<>();

    @Override
    public @Nullable UUID bacapup$whoPlaced(BlockPos pos) {
        return pos2uuid.get(pos.asLong());
    }

    @Override
    public void bacapup$setPlaced(@NonNull BlockPos pos, @NonNull UUID placer) {
        pos2uuid.put(pos.asLong(), placer);
    }
}
