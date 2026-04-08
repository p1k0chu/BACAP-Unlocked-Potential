package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.imixin.MobFlattener;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilBlock.class)
abstract class AnvilBlockMixin extends BlockMixin {
    @Override
    protected void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        super.onPlaced(world, pos, state, placer, itemStack, ci);

        if (!world.isClientSide()) {
            ((MobFlattener) world.getChunkAt(pos)).bacup$setPlaced(pos, placer.getUUID());
        }
    }
}
