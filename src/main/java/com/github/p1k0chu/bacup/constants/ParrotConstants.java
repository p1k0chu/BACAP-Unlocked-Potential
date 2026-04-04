package com.github.p1k0chu.bacup.constants;

import com.github.p1k0chu.bacup.mixin.ParrotAccessor;
import net.minecraft.world.entity.EntityType;

import java.util.Comparator;
import java.util.List;

public final class ParrotConstants {
    private ParrotConstants() {
    }

    private static final List<EntityType<?>> IMPOSSIBLE_TYPES = List.of(EntityType.ILLUSIONER);

    public static final List<EntityType<?>> IMITATING_TYPES = ParrotAccessor.getMobSoundMap()
            .keySet()
            .stream()
            .filter(i -> !IMPOSSIBLE_TYPES.contains(i))
            .sorted(Comparator.comparing(EntityType::toString))
            .toList();
}