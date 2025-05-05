package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EvokerEntity.WololoGoal.class)
public class WololoGoalMixin {
    ///  istance of outer class
    @Shadow @Final
    EvokerEntity field_7268;

    @Inject(method = "castSpell", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;setColor(Lnet/minecraft/util/DyeColor;)V"))
    void castSpell(CallbackInfo ci, @Local SheepEntity sheep) {
        Box box = Box.of(sheep.getPos(), 16, 16, 16);
        List<PlayerEntity> players = field_7268.getWorld().getNonSpectatingEntities(PlayerEntity.class, box);

        for(var player : players) {
            if(player instanceof ServerPlayerEntity serverPlayer) {
                Criteria.WOLOLO.trigger(serverPlayer);
            }
        }
    }
}
