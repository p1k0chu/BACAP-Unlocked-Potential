package com.github.p1k0chu.bacup.utils;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public final class AdvancementUtils {
    private AdvancementUtils() {
    }

    ///  grant one criterion
    public static boolean grant(ServerPlayer player, Identifier advancement, String criterion) {
        var advHolder = player.level().getServer().getAdvancements().get(advancement);
        if (advHolder == null) {
            return false;
        }

        var playerAdvancements = player.getAdvancements();
        return playerAdvancements.award(advHolder, criterion);
    }

    ///  grant full advancement
    public static boolean grant(ServerPlayer player, Identifier advancement) {
        var advHolder = player.level().getServer().getAdvancements().get(advancement);
        if (advHolder == null) {
            return false;
        }

        var playerAdvancements = player.getAdvancements();
        var progress = playerAdvancements.getOrStartProgress(advHolder);
        if (progress.isDone()) {
            return false;
        }
        for (var string : progress.getRemainingCriteria()) {
            playerAdvancements.award(advHolder, string);
        }
        return true;
    }

    public static Identifier id(String tab, String adv) {
        return Identifier.fromNamespaceAndPath(Main.MOD_ID, String.format("%s/%s", tab, adv));
    }
}
