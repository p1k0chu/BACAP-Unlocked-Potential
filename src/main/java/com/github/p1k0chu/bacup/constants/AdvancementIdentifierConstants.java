package com.github.p1k0chu.bacup.constants;

import com.github.p1k0chu.bacup.advancement.generator.RedstoneTabGenerator;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.resources.Identifier;

public final class AdvancementIdentifierConstants {
    private AdvancementIdentifierConstants() {
    }

    public static final Identifier SMELT_EVERYTHING = AdvancementUtils.id(
            RedstoneTabGenerator.TAB_NAME, RedstoneTabGenerator.SMELT_EVERYTHING
    );
}
