package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.advancement.generator.AnimalsTabGenerator;
import com.github.p1k0chu.bacup.advancement.generator.FarmingTabGenerator;
import com.github.p1k0chu.bacup.advancement.generator.MonstersTabGenerator;
import net.minecraft.resources.Identifier;

import static com.github.p1k0chu.bacup.utils.AdvancementUtils.id;

public final class TestAdvancementConstants {
    private TestAdvancementConstants() {
    }

    public static final Identifier WOLOLO = id(AnimalsTabGenerator.TAB_NAME, AnimalsTabGenerator.WOLOLO);
    public static final Identifier TRASH_BIN = id(FarmingTabGenerator.TAB_NAME, FarmingTabGenerator.TRASH_BIN);
    public static final Identifier WHEN_PIGS_FLY = id(AnimalsTabGenerator.TAB_NAME, AnimalsTabGenerator.WHEN_PIGS_FLY);
    public static final Identifier BEAM_ME_UP = id(MonstersTabGenerator.TAB_NAME, MonstersTabGenerator.BEAM_ME_UP);
}
