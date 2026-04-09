package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.MobFlattener;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract @Nullable Player getLastHurtByPlayer();

    @ModifyArg(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"), index = 2)
    private Consumer<ItemStack> modifyLootConsumer(Consumer<ItemStack> lootConsumer) {
        if (getLastHurtByPlayer() instanceof ServerPlayer serverPlayerEntity) {
            return lootConsumer.andThen(stack -> Criteria.ENTITY_DROPPED_LOOT.trigger(serverPlayerEntity, (LivingEntity) (Object) this, stack));
        }
        return lootConsumer;
    }

    @Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;"))
    protected void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (level().isClientSide()) {
            return;
        }

        if(damageSource.getDirectEntity() instanceof FallingBlockEntity fallingBlockEntity) {
            if(fallingBlockEntity.getBlockState().is(Blocks.ANVIL)) {
                BlockPos startPos = fallingBlockEntity.getStartPos();
                UUID placer = ((MobFlattener) level().getChunkAt(startPos)).bacup$whoPlaced(startPos);

                if(placer != null) {
                    if(level().getPlayerByUUID(placer) instanceof ServerPlayer player) {
                        Criteria.ANVIL_KILL.trigger(player, (LivingEntity) (Object) this);
                    }
                }
            }
        }
    }
}
