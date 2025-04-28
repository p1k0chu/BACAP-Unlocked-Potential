package com.github.p1k0chu.bacup;

import net.minecraft.entity.player.PlayerEntity;

public interface AbstractFurnaceBlockEntityWhoOpened {
    void setPlayer(PlayerEntity player);
    PlayerEntity getPlayer();
}
