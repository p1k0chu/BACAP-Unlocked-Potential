package com.github.p1k0chu.bacup.gametest;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public final class TestUtils {
    private TestUtils() {
    }

    public static @Nullable AdvancementHolder getAdvHolder(GameTestHelper helper, Identifier id) {
        return helper.getLevel().getServer().getAdvancements().get(id);
    }

    public static AdvancementProgress getAdvProgress(GameTestHelper helper, ServerPlayer player, Identifier id) {
        var holder = TestUtils.getAdvHolder(helper, id);
        helper.assertFalse(holder == null, "holder is null");
        assert holder != null : "holder is null";
        return player.getAdvancements().getOrStartProgress(holder);
    }

    public static void teleportRelative(GameTestHelper helper, Entity entity, double x, double y, double z) {
        var pos = helper.absoluteVec(new Vec3(x, y, z));
        entity.teleportTo(pos.x, pos.y, pos.z);
    }

    @SuppressWarnings("removal")
    public static ServerPlayer makeMockPlayer(GameTestHelper helper, double x, double y, double z) {
        var player = helper.makeMockServerPlayerInLevel();
        teleportRelative(helper, player, x, y, z);
        return player;
    }
}
