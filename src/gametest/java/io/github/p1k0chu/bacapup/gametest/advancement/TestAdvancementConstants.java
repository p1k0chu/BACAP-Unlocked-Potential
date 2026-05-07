package io.github.p1k0chu.bacapup.gametest.advancement;

import io.github.p1k0chu.bacapup.advancement.generator.*;
import net.minecraft.resources.Identifier;

import static io.github.p1k0chu.bacapup.utils.AdvancementUtils.id;

public final class TestAdvancementConstants {
    private TestAdvancementConstants() {
    }

    public static final Identifier WOLOLO = id(AnimalsTabSubProvider.TAB_NAME, AnimalsTabSubProvider.WOLOLO);
    public static final Identifier TRASH_BIN = id(FarmingTabSubProvider.TAB_NAME, FarmingTabSubProvider.TRASH_BIN);
    public static final Identifier WHEN_PIGS_FLY = id(AnimalsTabSubProvider.TAB_NAME, AnimalsTabSubProvider.WHEN_PIGS_FLY);
    public static final Identifier BEAM_ME_UP = id(MonstersTabSubProvider.TAB_NAME, MonstersTabSubProvider.BEAM_ME_UP);
    public static final Identifier CAN_YOU_HEAR_IT_FROM_HERE = id(AdventureTabSubProvider.TAB_NAME, AdventureTabSubProvider.CAN_YOU_HEAR_IT_FROM_HERE);
    public static final Identifier FIRE_TRICK = id(BuildingTabSubProvider.TAB_NAME, BuildingTabSubProvider.FIRE_TRICK);
}
