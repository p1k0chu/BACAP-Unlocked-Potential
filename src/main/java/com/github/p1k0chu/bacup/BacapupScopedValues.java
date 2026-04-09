package com.github.p1k0chu.bacup;

import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public final class BacapupScopedValues {
    private BacapupScopedValues() {
    }

    /// parrot or player (owner)
    public static final ScopedValue<Optional<LivingEntity>> parrotImitationScopedValue = ScopedValue.newInstance();
}
