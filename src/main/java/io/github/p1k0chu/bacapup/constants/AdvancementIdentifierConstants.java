package io.github.p1k0chu.bacapup.constants;

import io.github.p1k0chu.bacapup.advancement.generator.*;
import io.github.p1k0chu.bacapup.utils.AdvancementUtils;
import net.minecraft.resources.Identifier;

public final class AdvancementIdentifierConstants {
    private AdvancementIdentifierConstants() {
    }

    public static final Identifier SMELT_EVERYTHING = AdvancementUtils.id(RedstoneTabSubProvider.TAB_NAME, RedstoneTabSubProvider.SMELT_EVERYTHING);
    public static final Identifier SHORT_CIRCUIT = AdvancementUtils.id(AdventureTabSubProvider.TAB_NAME, AdventureTabSubProvider.SHORT_CIRCUIT);
    public static final Identifier CONDUEL = AdvancementUtils.id(BiomesTabSubProvider.TAB_NAME, BiomesTabSubProvider.CONDUEL);
    public static final Identifier RAGE_BAITER = AdvancementUtils.id(MiningTabSubProvider.TAB_NAME, MiningTabSubProvider.RAGE_BAITER);
    public static final Identifier INTENTIONAL_ADVANCEMENT_DESIGN = AdvancementUtils.id(EndTabSubProvider.TAB_NAME, EndTabSubProvider.INTENTIONAL_ADVANCEMENT_DESIGN);
    public static final Identifier THIS_IS_NOT_COOKIE_CLICKER = AdvancementUtils.id(FarmingTabSubProvider.TAB_NAME, FarmingTabSubProvider.THIS_IS_NOT_COOKIE_CLICKER);
    public static final Identifier R_I_P = AdvancementUtils.id(AdventureTabSubProvider.TAB_NAME, AdventureTabSubProvider.R_I_P);
}
