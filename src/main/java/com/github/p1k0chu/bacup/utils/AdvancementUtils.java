package com.github.p1k0chu.bacup.utils;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public final class AdvancementUtils {
    private AdvancementUtils() {
    }

    public static boolean grant(ServerPlayer player, Identifier advancement, String criterion) {
        var advHolder = player.level().getServer().getAdvancements().get(advancement);
        if (advHolder != null) {
            var playerAdvancements = player.getAdvancements();
            return playerAdvancements.award(advHolder, criterion);
        }
        return false;
    }

    public static Identifier id(String tab, String adv) {
        return Identifier.fromNamespaceAndPath(Main.MOD_ID, String.format("%s/%s", tab, adv));
    }
}
