package io.github.p1k0chu.bacapup.mixin;

import io.github.p1k0chu.bacapup.ducks.RagebaiterStatus;
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
    public void bacapup$setRagebaiter(Player entity, boolean status) {
        ragebaiters.put(entity, status);
    }

    @Override
    public boolean bacapup$isRagebaiter(Player entity) {
        return ragebaiters.getOrDefault(entity, true);
    }
}
