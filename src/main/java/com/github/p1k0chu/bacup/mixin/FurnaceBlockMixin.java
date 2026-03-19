package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.SMELT_EVERYTHING;

@Mixin(FurnaceBlock.class)
public class FurnaceBlockMixin {
    @Inject(method = "openContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;"))
    void checkSmeltEverything(Level level, BlockPos blockPos, Player player, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (countConnectedChests(level, blockPos) >= 3) {
                AdvancementUtils.grant(serverPlayer, SMELT_EVERYTHING);
            }
        }
    }

    @Unique
    private static int countConnectedChests(Level level, BlockPos pos) {
        int count = 0;

        for (var i : Direction.values()) {
            var hopperPos = pos.relative(i);
            var state = level.getBlockState(hopperPos);
            if (state.is(Blocks.HOPPER)) {
                var dir = state.getValue(BlockStateProperties.FACING_HOPPER);
                if (Objects.equals(i.getOpposite(), dir)) {
                    if (level.getBlockState(hopperPos.above()).is(Blocks.CHEST)) {
                        ++count;
                    }
                }
            }
        }

        return count;
    }
}
