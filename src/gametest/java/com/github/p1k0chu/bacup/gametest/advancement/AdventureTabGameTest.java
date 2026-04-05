package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.gametest.utils.TestRunnables;
import com.github.p1k0chu.bacup.gametest.utils.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class AdventureTabGameTest {
    @GameTest(skyAccess = true)
    public void testCanYouHearItFromHere(GameTestHelper helper) {
        helper.setBlock(60, 0, 0, Blocks.BELL);
        TestUtils.forceLoadChunk(helper, new BlockPos(60, 0, 0));
        TestUtils.forceLoadChunk(helper, 59, 0.5);

        var player = TestUtils.makeMockPlayer(helper, 0, 0, 0);
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());

        var arrow = TestUtils.spawnArrow(helper, player);
        TestUtils.teleportRelative(helper, arrow, 59.5, 0.5, 0.5);
        arrow.setDeltaMovement(1, 0, 0);
        helper.succeedWhen(TestRunnables.assertAdvDone(helper, player, TestAdvancementConstants.CAN_YOU_HEAR_IT_FROM_HERE));
    }
}
