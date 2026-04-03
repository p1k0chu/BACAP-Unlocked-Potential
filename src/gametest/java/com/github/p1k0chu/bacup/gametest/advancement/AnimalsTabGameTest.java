package com.github.p1k0chu.bacup.gametest.advancement;

import com.github.p1k0chu.bacup.gametest.TestUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("removal")
public class AnimalsTabGameTest {
    @GameTest(maxTicks = 100)
    public void testWololo(GameTestHelper helper) {
        var player = TestUtils.makeMockPlayer(helper, 0, 0, 0);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE));

        var pos = new BlockPos(5, 1, 5);
        var sheep = helper.spawnWithNoFreeWill(EntityType.SHEEP, pos);
        sheep.setColor(DyeColor.BLUE);
        helper.spawn(EntityType.EVOKER, pos);

        var progress = TestUtils.getAdvProgress(helper, player, TestAdvancementConstants.WOLOLO);
        helper.succeedWhen(() -> helper.assertTrue(progress.isDone(), "advancement not done"));
    }

    @GameTest(skyAccess = true, maxTicks = 100)
    public void testWhenPigsFly(GameTestHelper helper) {
        helper.setBlock(0, 0, 0, Blocks.STONE);
        var pig = helper.spawnWithNoFreeWill(EntityType.PIG, new BlockPos(0, 1, 0));
        pig.setItemSlot(EquipmentSlot.SADDLE, Items.SADDLE.getDefaultInstance());
        var player = helper.makeMockServerPlayerInLevel();
        helper.assertTrue(player.startRiding(pig, true, true), "failed to ride pig");

        TestUtils.teleportRelative(helper, pig, 0.5, 10, 0.5);
        var progress = TestUtils.getAdvProgress(helper, player, TestAdvancementConstants.WHEN_PIGS_FLY);
        helper.succeedWhen(() -> helper.assertTrue(progress.isDone(), "advancement not done"));
    }
}
