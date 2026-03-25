package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.animal.feline.Cat$CatRelaxOnOwnerGoal")
public abstract class CatRelaxOnOwnerGoalMixin {
    @Shadow
    @Final
    private Cat cat;

    @Inject(method = "lambda$giveMorningGift$0", at = @At("HEAD"))
    void awardCriteria(BlockPos.MutableBlockPos mutable, ServerLevel world, ItemStack stack, CallbackInfo ci) {
        if(cat.getOwner() instanceof ServerPlayer player) {
            Criteria.CAT_GIFT_RECEIVED.trigger(player, stack);
        }
    }
}
