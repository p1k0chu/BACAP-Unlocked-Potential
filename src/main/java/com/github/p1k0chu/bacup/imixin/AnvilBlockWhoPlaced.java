package com.github.p1k0chu.bacup.imixin;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AnvilBlockWhoPlaced {
    @Nullable UUID bacup$getPlacer();
}
