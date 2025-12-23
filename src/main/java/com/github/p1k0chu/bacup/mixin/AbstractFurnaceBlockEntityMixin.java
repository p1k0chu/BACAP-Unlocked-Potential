package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityWhoOpened;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
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

    @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private static void tick(ServerLevel world,
                             BlockPos pos,
                             BlockState state,
                             AbstractFurnaceBlockEntity blockEntity,
                             CallbackInfo ci,
                             @Local(ordinal = 0) ItemStack fuel) {
        ((AbstractFurnaceBlockEntityLastFuel)blockEntity).bacup$setLastFuel(fuel.getItem());

        UUID whoOpened = ((AbstractFurnaceBlockEntityWhoOpened) blockEntity).bacup$getPlayer();

        MinecraftServer server = world.getServer();
        ServerPlayer player = server.getPlayerList().getPlayer(whoOpened);

        if(player != null) {
            Criteria.FURNACE_FUEL_CONSUMED.trigger(player, fuel);
        }
    }
}
