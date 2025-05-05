package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "spawnItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BrushableBlockEntity;generateItem(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.AFTER))
    void spawnItem(ServerWorld world, LivingEntity brusher, ItemStack brush, CallbackInfo ci) {
        if (brusher instanceof ServerPlayerEntity player) {
            ItemStack item = getItem();
            Criteria.SUS_BLOCK_GOT_ITEM.trigger(player, item);
        }
    }
}
