package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.R_I_P;

@Mixin(BlockItem.class)
abstract class BlockItemMixin {

    @Shadow
    @Final
    @Deprecated
    private Block block;

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/criterion/ItemUsedOnLocationTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemInstance;)V"))
    private void onBlockPlaced(BlockPlaceContext placeContext, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "player") Player player, @Local BlockPos pos) {
        if (block == Blocks.WITHER_ROSE) {
            final Optional<GlobalPos> lastDeathPos = player.getLastDeathLocation();
            if (lastDeathPos.isEmpty()) return;
            final BlockPos deathPos = lastDeathPos.get().pos();
            if (lastDeathPos.get().dimension().identifier() == placeContext.getLevel().dimension().identifier() &&
                    deathPos.getX() == pos.getX() && deathPos.getY() == pos.getY() && deathPos.getZ() == pos.getZ()) {
                AdvancementUtils.grant((ServerPlayer) player, R_I_P);
            }
        }
    }
}
