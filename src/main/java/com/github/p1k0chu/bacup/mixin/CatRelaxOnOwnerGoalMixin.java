package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
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

    // giveMorningGift -> dropFromGiftLootTable() -> lambda
    @Inject(method = "method_64176", at = @At("HEAD"))
    void awardCriteria(BlockPos.MutableBlockPos mutable, ServerLevel world, ItemStack stack, CallbackInfo ci) {
        if(cat.getOwner() instanceof ServerPlayer player) {
            Criteria.CAT_GIFT_RECEIVED.trigger(player, stack);
        }
    }
}
