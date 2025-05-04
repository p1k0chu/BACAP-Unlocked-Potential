package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.AnvilBlockWhoPlaced;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        this.placer = placer.getUuid();

        super.onPlaced(world, pos, state, placer, itemStack, ci);
    }
}