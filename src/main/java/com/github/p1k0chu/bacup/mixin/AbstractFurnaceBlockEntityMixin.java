package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityWhoOpened;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityWhoOpened, AbstractFurnaceBlockEntityLastFuel {
    @Unique
    @Nullable
    private UUID lastPlayerOpened;

    @Unique
    @Nullable
    private Item lastConsumedFuel;

    @Override
    public void bacup$setPlayer(UUID player) {
        lastPlayerOpened = player;
    }

    @Override
    public UUID bacup$getPlayer() {
        return lastPlayerOpened;
    }

    @Override
    public @Nullable Item bacup$getLastFuel() {
        return lastConsumedFuel;
    }

    @Override
    public void bacup$setLastFuel(@Nullable Item fuel) {
        lastConsumedFuel = fuel;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static void tick(ServerWorld world,
                             BlockPos pos,
                             BlockState state,
                             AbstractFurnaceBlockEntity blockEntity,
                             CallbackInfo ci,
                             @Local(ordinal = 0) ItemStack fuel) {
        ((AbstractFurnaceBlockEntityLastFuel)blockEntity).bacup$setLastFuel(fuel.getItem());

        UUID whoOpened = ((AbstractFurnaceBlockEntityWhoOpened) blockEntity).bacup$getPlayer();

        MinecraftServer server = world.getServer();
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(whoOpened);

        if(player != null) {
            Criteria.FURNACE_FUEL_CONSUMED.trigger(player, fuel);
        }
    }
}
