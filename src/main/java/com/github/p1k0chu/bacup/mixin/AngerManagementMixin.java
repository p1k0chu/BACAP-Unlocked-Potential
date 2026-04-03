package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.RagebaiterStatus;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AngerManagement.class)
class AngerManagementMixin implements RagebaiterStatus {
    @Unique
    private final Object2BooleanMap<Player> ragebaiters = new Object2BooleanOpenHashMap<>();


    @Override
    public void bacup$setRagebaiter(Player entity, boolean status) {
        ragebaiters.put(entity, status);
    }

    @Override
    public boolean bacup$isRagebaiter(Player entity) {
        return ragebaiters.getOrDefault(entity, true);
    }
}
