package com.github.p1k0chu.bacup.utils;

import com.github.p1k0chu.bacup.mixin.LootItemAccessor;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LootTableUtils {
    private LootTableUtils() {
    }

    @SuppressWarnings("UnstableApiUsage")
    public static List<Item> getItemsInLootTable(LootTable table) {
        List<LootPool> pools = ((LootTableAccessor) table).fabric_getPools();
        List<Item> list = new ArrayList<>();

        for (LootPool pool : pools) {
            for (var entry : pool.entries) {
                if (entry instanceof LootItem lootItem) {
                    list.add(((LootItemAccessor) lootItem).getItem().value());
                }
            }
        }

        return Collections.unmodifiableList(list);
    }
}
