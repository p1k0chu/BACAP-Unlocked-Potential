package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.gametest.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class MonstersTabGameTest {
    @GameTest(skyAccess = true)
    public void testBeamMeUp(GameTestHelper helper) {
        helper.setBlock(0, 0, 0, Blocks.STONE);
        helper.setBlock(105, 0, 0, Blocks.STONE);

        var player = TestUtils.makeMockPlayer(helper, 0.5, 1, 0.5);
        player.setXRot(-90f);
        var stack = Items.ENDER_PEARL.getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        player.gameMode.useItem(player, helper.getLevel(), stack, InteractionHand.MAIN_HAND);

        var progress = TestUtils.getAdvProgress(helper, player, TestAdvancementConstants.BEAM_ME_UP);
        helper.startSequence()
                .thenWaitUntil(() -> {
                    var pearl = helper.findOneEntity(EntityType.ENDER_PEARL);
                    // Teleporting the pearl into unloaded chunks will
                    // not work, so teleport player.
                    TestUtils.teleportRelative(helper, player, 105.5, 1, 0.5);
                    pearl.setDeltaMovement(0, -1, 0);
                }).thenWaitUntil(() -> helper.assertTrue(progress.isDone(), "advancement not done"))
                .thenSucceed();
    }
}
