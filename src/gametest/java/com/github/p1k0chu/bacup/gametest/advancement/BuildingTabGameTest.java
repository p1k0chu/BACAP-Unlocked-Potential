package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.gametest.utils.TestRunnables;
import com.github.p1k0chu.bacup.gametest.utils.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleCakeBlock;

public class BuildingTabGameTest {
    @GameTest
    public void testFireTrick(GameTestHelper helper) {
        helper.setBlock(0, 0, 0, Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false));
        helper.setBlock(1, 0, 0, Blocks.CANDLE_CAKE.defaultBlockState().setValue(CandleCakeBlock.LIT, false));

        var bow = Items.BOW.getDefaultInstance();
        bow.enchant(helper.getLevel().registryAccess().getOrThrow(Enchantments.FLAME), 1);

        var player = TestUtils.makeMockPlayer(helper, 0.5, 0.5, 0.5);
        player.setItemInHand(InteractionHand.MAIN_HAND, bow);
        TestUtils.spawnArrow(helper, player).setDeltaMovement(0, -2, 0);
        TestUtils.teleportRelative(helper, player, 1.5, 0.5, 0.5);
        TestUtils.spawnArrow(helper, player).setDeltaMovement(0, -2, 0);
        helper.succeedWhen(TestRunnables.assertAdvDone(helper, player, TestAdvancementConstants.FIRE_TRICK));
    }
}
