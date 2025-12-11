package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Evoker.EvokerWololoSpellGoal.class)
public class WololoGoalMixin {
    ///  istance of outer class
    @Shadow(aliases = "field_7268") @Final
    Evoker evoker;

    @Inject(method = "performSpellCasting", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/sheep/Sheep;setColor(Lnet/minecraft/world/item/DyeColor;)V"))
    void castSpell(CallbackInfo ci, @Local Sheep sheep) {
        AABB box = AABB.ofSize(sheep.position(), 16, 16, 16);
        //noinspection resource
        List<Player> players = evoker.level().getEntitiesOfClass(Player.class, box);

        for(var player : players) {
            if(player instanceof ServerPlayer serverPlayer) {
                Criteria.WOLOLO.trigger(serverPlayer);
            }
        }
    }
}
