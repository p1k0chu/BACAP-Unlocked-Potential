package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.gametest.utils.TestRunnables;
import com.github.p1k0chu.bacup.gametest.utils.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class FarmingTabGameTest {
    @GameTest(maxTicks = 100)
    public void testTrashBin(GameTestHelper helper) {
        helper.setBlock(0, 0, 0, Blocks.BARRIER);
        helper.setBlock(0, 1, 0, Blocks.SAND);
        helper.setBlock(0, 2, 0, Blocks.CACTUS);

        var player = TestUtils.makeMockPlayer(helper, 0.5, 3, 0.5);
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.DIAMOND.getDefaultInstance());
        player.setXRot(90f);
        player.drop(true);
        helper.succeedWhen(TestRunnables.assertAdvDone(helper, player, TestAdvancementConstants.TRASH_BIN));
    }
}
