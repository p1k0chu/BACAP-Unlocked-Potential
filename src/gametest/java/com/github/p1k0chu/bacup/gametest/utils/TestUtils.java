package com.github.p1k0chu.bacup.gametest.utils;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
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

    private static void forceLoadChunk(GameTestHelper helper, ChunkPos absolutePos) {
        helper.getLevel().setChunkForced(absolutePos.x(), absolutePos.z(), true);
    }

    public static void forceLoadChunk(GameTestHelper helper, BlockPos pos) {
        forceLoadChunk(helper, ChunkPos.containing(helper.absolutePos(pos)));
    }

    public static void forceLoadChunk(GameTestHelper helper, double x, double z) {
        forceLoadChunk(helper, BlockPos.containing(x, 0, z));
    }

    public static AbstractArrow spawnArrow(GameTestHelper helper, LivingEntity owner) {
        var arrowStack = Items.ARROW.getDefaultInstance();
        return Projectile.spawnProjectile(
                ((ArrowItem) Items.ARROW).createArrow(helper.getLevel(), arrowStack, owner, owner.getItemInHand(InteractionHand.MAIN_HAND)),
                helper.getLevel(),
                arrowStack
        );
    }
}
