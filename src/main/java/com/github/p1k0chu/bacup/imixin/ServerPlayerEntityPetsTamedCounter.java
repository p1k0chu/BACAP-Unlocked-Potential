package com.github.p1k0chu.bacup.imixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Set;

public interface ServerPlayerEntityPetsTamedCounter {
    Set<EntityType<?>> trackedTypes = Set.of(
            EntityType.CAT
    );

    int bacup$increment(EntityType<?> entityType);

    static <T extends Entity> boolean isTracked(EntityType<T> entityType) {
        return trackedTypes.contains(entityType);
    }
}
