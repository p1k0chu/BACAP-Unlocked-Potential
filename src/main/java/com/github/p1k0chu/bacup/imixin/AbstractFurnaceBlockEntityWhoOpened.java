package com.github.p1k0chu.bacup.imixin;

import java.util.UUID;

public interface AbstractFurnaceBlockEntityWhoOpened {
    void setPlayer(UUID player);
    UUID getPlayer();
}
