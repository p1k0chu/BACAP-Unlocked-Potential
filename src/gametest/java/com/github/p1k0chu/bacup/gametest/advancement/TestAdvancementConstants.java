package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.advancement.generator.*;
import net.minecraft.resources.Identifier;

import static com.github.p1k0chu.bacup.utils.AdvancementUtils.id;

public final class TestAdvancementConstants {
    private TestAdvancementConstants() {
    }

    public static final Identifier WOLOLO = id(AnimalsTabGenerator.TAB_NAME, AnimalsTabGenerator.WOLOLO);
    public static final Identifier TRASH_BIN = id(FarmingTabGenerator.TAB_NAME, FarmingTabGenerator.TRASH_BIN);
    public static final Identifier WHEN_PIGS_FLY = id(AnimalsTabGenerator.TAB_NAME, AnimalsTabGenerator.WHEN_PIGS_FLY);
    public static final Identifier BEAM_ME_UP = id(MonstersTabGenerator.TAB_NAME, MonstersTabGenerator.BEAM_ME_UP);
    public static final Identifier CAN_YOU_HEAR_IT_FROM_HERE = id(AdventureTabGenerator.TAB_NAME, AdventureTabGenerator.CAN_YOU_HEAR_IT_FROM_HERE);
    public static final Identifier FIRE_TRICK = id(BuildingTabGenerator.TAB_NAME, BuildingTabGenerator.FIRE_TRICK);
}
