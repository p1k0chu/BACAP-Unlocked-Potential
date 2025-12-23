package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.BacupPersistentState;
import com.github.p1k0chu.bacup.PlayerData;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityPetsTamedCounter;
import com.github.p1k0chu.bacup.imixin.ServerPlayerEntityTradedEmeralds;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityPetsTamedCounter, ServerPlayerEntityTradedEmeralds {
    @Override
    public int bacup$increment(EntityType<?> entityType) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        return state.getPetsTamed().compute(entityType, (key, value) -> value == null ? 1 : value + 1);
    }

    @Override
    public int bacup$incrementEmeraldsObtained(int amount) {
        PlayerData state = BacupPersistentState.getPlayerState((LivingEntity) (Object) this);

        state.setEmeraldsObtained(state.getEmeraldsObtained() + amount);

        return state.getEmeraldsObtained();
    }
}
