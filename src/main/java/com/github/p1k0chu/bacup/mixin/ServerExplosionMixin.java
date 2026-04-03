package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.utils.AdvancementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static com.github.p1k0chu.bacup.constants.AdvancementIdentifierConstants.SHORT_CIRCUIT;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.DOUBLE_BLOCK_HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

@Mixin(ServerExplosion.class)
abstract class ServerExplosionMixin {
    @Shadow
    public abstract boolean canTriggerBlocks();

    @Shadow
    @Final
    private ServerLevel level;

    @Shadow
    public abstract Map<Player, Vec3> getHitPlayers();

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void interactWithBlocks(List<BlockPos> list, CallbackInfo ci) {
        if (this.canTriggerBlocks() && countTriggeredRedstoneComponents(list) >= 9) {
            for (var player : this.getHitPlayers().keySet()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    AdvancementUtils.grant(serverPlayer, SHORT_CIRCUIT);
                }
            }
        }
    }

    @Unique
    private int countTriggeredRedstoneComponents(List<BlockPos> list) {
        int i = 0;
        for (var blockPos : list) {
            var blockState = this.level.getBlockState(blockPos);
            var block = blockState.getBlock();

            if ((block instanceof ButtonBlock || block instanceof FenceGateBlock)
                    && !blockState.getValue(POWERED)) {
                ++i;
            } else if (block instanceof DoorBlock doorBlock
                    && blockState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER
                    && doorBlock.type().canOpenByWindCharge()
                    && !(Boolean) blockState.getValue(POWERED)) {
                ++i;
            } else if (block instanceof TrapDoorBlockAccessor trapDoorBlock
                    && trapDoorBlock.getType().canOpenByWindCharge()
                    && !(Boolean) blockState.getValue(POWERED)) {
                ++i;
            } else if (block instanceof LeverBlock || block instanceof BellBlock) {
                ++i;
            }
        }
        return i;
    }
}
