package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityLastFuel;
import com.github.p1k0chu.bacup.imixin.AbstractFurnaceBlockEntityWhoOpened;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityWhoOpened, AbstractFurnaceBlockEntityLastFuel {
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

    @Unique
    private static final ScopedValue<AbstractFurnaceBlockEntity> blockEntityScoped = ScopedValue.newInstance();

    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;consumeFuel(Lnet/minecraft/core/NonNullList;Lnet/minecraft/world/item/ItemStack;)V"))
    private static void consumeFuelWithBlockEntity(NonNullList<ItemStack> remainder, ItemStack items, Operation<Void> original, @Local(argsOnly = true) AbstractFurnaceBlockEntity entity) {
        ScopedValue.where(blockEntityScoped, entity).run(() -> original.call(remainder, items));
    }

    @Inject(method = "consumeFuel", at = @At("HEAD"))
    private static void consumeFuel(NonNullList<ItemStack> items, ItemStack fuel, CallbackInfo ci) {
        AbstractFurnaceBlockEntity blockEntity = blockEntityScoped.get();
        if (blockEntity == null || blockEntity.getLevel() == null) {
            return;
        }

        ((AbstractFurnaceBlockEntityLastFuel) blockEntity).bacup$setLastFuel(fuel.getItem());
        UUID whoOpened = ((AbstractFurnaceBlockEntityWhoOpened) blockEntity).bacup$getPlayer();

        var server = blockEntity.getLevel().getServer();
        if (server == null) {
            return;
        }
        ServerPlayer player = server.getPlayerList().getPlayer(whoOpened);

        if (player != null) {
            Criteria.FURNACE_FUEL_CONSUMED.trigger(player, fuel);
        }
    }
}
