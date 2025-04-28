package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.Main;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.passive.CatEntity$SleepWithOwnerGoal")
public abstract class CatEntitySleepWithOwnerGoalMixin {
    @Shadow
    @Final
    private CatEntity cat;

    @Inject(method = "method_64176", at = @At("HEAD"))
    void awardCriteria(BlockPos.Mutable mutable, ServerWorld world, ItemStack stack, CallbackInfo ci) {
        if(cat.getOwner() instanceof ServerPlayerEntity player) {
            Main.CAT_GIFT_RECEIVED.trigger(player, stack);
        }
    }
}
