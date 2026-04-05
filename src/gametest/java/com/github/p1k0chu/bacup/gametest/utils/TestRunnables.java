package com.github.p1k0chu.bacup.gametest.utils;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public final class TestRunnables {
    private TestRunnables() {
    }

    public static Runnable assertAdvDone(GameTestHelper helper, ServerPlayer player, Identifier id) {
        var progress = TestUtils.getAdvProgress(helper, player, id);
        var msg = id + " is not done";
        return () -> helper.assertTrue(progress.isDone(), msg);
    }
}
