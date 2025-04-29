package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.BacupPersistentState;
import com.github.p1k0chu.bacup.PlayerData;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityPetsTamedCounter;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import kotlin.collections.MapsKt;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.TradeOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityPetsTamedCounter {
    @Override
    public int bacup$increment(EntityType<?> entityType) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        return state.getPetsTamed().compute(entityType, (key, value) -> value == null ? 1 : value + 1);
    }

    @Override
    public int bacup$get(EntityType<?> entityType) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        Map<EntityType<?>, Integer> x = state.getPetsTamed();

        return x.computeIfAbsent(entityType, i -> 0);
    }
}
