package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.AbstractFurnaceBlockEntityWhoOpened;
import com.github.p1k0chu.bacup.Main;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityWhoOpened {
    @Unique
    @Nullable
    private PlayerEntity lastPlayerOpened;

    @Override
    public void setPlayer(PlayerEntity player) {
        lastPlayerOpened = player;
    }

    @Override
    public PlayerEntity getPlayer() {
        return lastPlayerOpened;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static void tick(ServerWorld world,
                             BlockPos pos,
                             BlockState state,
                             AbstractFurnaceBlockEntity blockEntity,
                             CallbackInfo ci,
                             @Local(ordinal = 0) ItemStack fuel) {

        var whoOpened = (AbstractFurnaceBlockEntityWhoOpened) blockEntity;
        if(whoOpened.getPlayer() instanceof ServerPlayerEntity serverPlayerEntity) {
            Main.FURNACE_FUEL_CONSUMED.trigger(serverPlayerEntity, fuel);
        }
    }
}
