package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.AnvilBlockWhoPlaced;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin extends BlockMixin implements AnvilBlockWhoPlaced {
    @Unique
    private UUID placer;

    @Override
    public @Nullable UUID bacup$getPlacer() {
        return placer;
    }

    @Override
    void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        this.placer = placer.getUUID();

        super.onPlaced(world, pos, state, placer, itemStack, ci);
    }
}