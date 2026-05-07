package io.github.p1k0chu.bacapup.ducks;

import net.minecraft.world.entity.player.Player;

public interface RagebaiterStatus {
    void bacapup$setRagebaiter(Player entity, boolean status);
    boolean bacapup$isRagebaiter(Player entity);
}
