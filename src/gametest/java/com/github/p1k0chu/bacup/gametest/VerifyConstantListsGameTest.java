package com.github.p1k0chu.bacup.gametest;

import com.github.p1k0chu.bacup.constants.CollectItemsConstants;
import com.github.p1k0chu.bacup.gametest.utils.LootTableUtils;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Verify item/mob/etc lists using vanilla datapack.
 */
public class VerifyConstantListsGameTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyConstantListsGameTest.class);

    private static final List<ResourceKey<LootTable>> ARCHEOLOGY_TABLES = List.of(
            BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
            BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON,
            BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,
            BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
            BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY
    );

    @GameTest
    public void testArcheologyList(GameTestHelper helper) {
        var provider = helper.getLevel().getServer().reloadableRegistries();
        List<Item> expected = new ArrayList<>();
        for (var tableKey : ARCHEOLOGY_TABLES) {
            expected.addAll(LootTableUtils.getItemsInLootTable(provider.getLootTable(tableKey)));
        }

        boolean fail = false;
        for (var item : CollectItemsConstants.SUS_LOOT) {
            if (!expected.removeIf(i -> Objects.equals(i, item))) {
                fail = true;
                LOGGER.error("Unexpected item: {}", item);
            }
        }
        if (fail) {
            helper.fail("Unexpected items.");
        }

        if (!expected.isEmpty()) {
            LOGGER.error("Missing items:");
            expected.forEach(i -> LOGGER.error("{}", i));
            helper.fail("Missing " + expected.size() + " items.");
        }
        helper.succeed();
    }
}
