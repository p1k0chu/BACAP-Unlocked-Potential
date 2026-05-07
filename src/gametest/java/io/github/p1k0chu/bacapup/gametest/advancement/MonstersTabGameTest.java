package io.github.p1k0chu.bacapup.gametest.advancement;

import io.github.p1k0chu.bacapup.gametest.utils.TestRunnables;
import io.github.p1k0chu.bacapup.gametest.utils.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
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

        helper.startSequence()
                .thenWaitUntil(() -> {
                    var pearl = helper.findOneEntity(EntityTypes.ENDER_PEARL);
                    TestUtils.forceLoadChunk(helper, 105.5, 0.5);
                    TestUtils.teleportRelative(helper, pearl, 105.5, 0.5, 0.5);
                }).thenWaitUntil(TestRunnables.assertAdvDone(helper, player, TestAdvancementConstants.BEAM_ME_UP))
                .thenSucceed();
    }
}
