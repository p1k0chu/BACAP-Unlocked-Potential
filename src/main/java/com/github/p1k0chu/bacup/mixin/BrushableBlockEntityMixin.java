package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "dropContent",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;unpackLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V",
                    shift = At.Shift.AFTER))
    void spawnItem(ServerLevel world, LivingEntity brusher, ItemStack brush, CallbackInfo ci) {
        if (brusher instanceof ServerPlayer player) {
            ItemStack item = getItem();
            if (!item.isEmpty())
                Criteria.SUS_BLOCK_GOT_ITEM.trigger(player, item);
        }
    }
}
