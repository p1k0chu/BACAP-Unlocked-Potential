package io.github.p1k0chu.bacapup.constants;

import io.github.p1k0chu.bacapup.mixin.ParrotAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;

import java.util.Comparator;
import java.util.List;

public final class ParrotConstants {
    private static final List<EntityType<?>> IMPOSSIBLE_TYPES = List.of(EntityTypes.ILLUSIONER);

    public static final List<EntityType<?>> IMITATING_TYPES = ParrotAccessor.getMobSoundMap()
            .keySet()
            .stream()
            .filter(i -> !IMPOSSIBLE_TYPES.contains(i))
            .sorted(Comparator.comparing(EntityType::toString))
            .toList();

    private ParrotConstants() {
    }
}
