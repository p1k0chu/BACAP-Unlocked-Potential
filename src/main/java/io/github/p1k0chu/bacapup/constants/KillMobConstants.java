package io.github.p1k0chu.bacapup.constants;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public final class KillMobConstants {
    private KillMobConstants() {
    }

    private static final List<EntityType<?>> EXCEPTIONS = List.of(EntityTypes.GIANT, EntityTypes.ILLUSIONER);
    private static final List<EntityType<?>> MISC_BUT_SHOULDNT_BE = List.of(EntityTypes.IRON_GOLEM, EntityTypes.SNOW_GOLEM, EntityTypes.VILLAGER);
    private static final List<MobCategory> IGNORE_CATEGORIES = List.of(MobCategory.MISC);
    private static final List<EntityType<?>> EXCEPTIONS_ANVIL = List.of(EntityTypes.CREAKING, EntityTypes.ENDER_DRAGON, EntityTypes.WITHER);

    private static <T extends Entity> List<EntityType<T>> getAllMobs(HolderLookup<EntityType<T>> lookup) {
        return lookup.listElements()
                .map(Holder.Reference::value)
                .filter(i -> MISC_BUT_SHOULDNT_BE.contains(i) || !IGNORE_CATEGORIES.contains(i.getCategory()))
                .filter(Predicate.not(EXCEPTIONS::contains))
                .toList();
    }

    @SuppressWarnings("unchecked")
    public static List<EntityType<Entity>> getAnvilMobsToKill(HolderLookup<EntityType<? extends Entity>> lookup) {
        return getAllMobs((HolderLookup<EntityType<Entity>>) (Object) lookup).stream()
                .filter(Predicate.not(EXCEPTIONS_ANVIL::contains))
                .sorted(Comparator.comparing(EntityType::toString))
                .toList();
    }
}
