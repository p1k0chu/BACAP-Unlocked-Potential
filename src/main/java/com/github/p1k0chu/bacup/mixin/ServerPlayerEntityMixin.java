package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityPetsTamedCounter;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityPetsTamedCounter {
    @Unique
    private final Object2IntOpenHashMap<EntityType<?>> petsTamed = new Object2IntOpenHashMap<>();

    @Override
    public int bacup$increment(EntityType<?> entityType) {
        petsTamed.addTo(entityType, 1);
        return petsTamed.getInt(entityType);
    }

    @Override
    public int bacup$get(EntityType<?> entityType) {
        return petsTamed.getInt(entityType);
    }
}
