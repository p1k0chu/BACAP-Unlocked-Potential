package com.github.p1k0chu.bacup.constants;

import com.github.p1k0chu.bacup.advancement.generator.*;
import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.resources.Identifier;

public final class AdvancementIdentifierConstants {
    private AdvancementIdentifierConstants() {
    }

    public static final Identifier SMELT_EVERYTHING = AdvancementUtils.id(RedstoneTabGenerator.TAB_NAME, RedstoneTabGenerator.SMELT_EVERYTHING);
    public static final Identifier SHORT_CIRCUIT = AdvancementUtils.id(AdventureTabGenerator.TAB_NAME, AdventureTabGenerator.SHORT_CIRCUIT);
    public static final Identifier CONDUEL = AdvancementUtils.id(BiomesTabGenerator.TAB_NAME, BiomesTabGenerator.CONDUEL);
    public static final Identifier RAGE_BAITER = AdvancementUtils.id(MiningTabGenerator.TAB_NAME, MiningTabGenerator.RAGE_BAITER);
    public static final Identifier INTENTIONAL_ADVANCEMENT_DESIGN = AdvancementUtils.id(EndTabGenerator.TAB_NAME, EndTabGenerator.INTENTIONAL_ADVANCEMENT_DESIGN);
    public static final Identifier THIS_IS_NOT_COOKIE_CLICKER = AdvancementUtils.id(AdventureTabGenerator.TAB_NAME, AdventureTabGenerator.THIS_IS_NOT_COOKIE_CLICKER);
}
