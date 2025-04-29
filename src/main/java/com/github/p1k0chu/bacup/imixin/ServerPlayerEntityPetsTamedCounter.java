package com.github.p1k0chu.bacup.imixin;

import net.minecraft.entity.EntityType;

public interface ServerPlayerEntityPetsTamedCounter {
    int bacup$increment(EntityType<?> entityType);
    int bacup$get(EntityType<?> entityType);
}
