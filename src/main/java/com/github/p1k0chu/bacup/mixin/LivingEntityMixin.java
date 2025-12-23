package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AnvilBlockWhoPlaced;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract @Nullable Player getLastHurtByPlayer();

    @Inject(method = "hurtServer", at = @At(value = "HEAD"))
    void damage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
    }

    @ModifyArg(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"), index = 2)
    Consumer<ItemStack> modifyLootConsumer(Consumer<ItemStack> lootConsumer) {
        if (getLastHurtByPlayer() instanceof ServerPlayer serverPlayerEntity) {
            return lootConsumer.andThen(stack -> Criteria.ENTITY_DROPPED_LOOT.trigger(serverPlayerEntity, (LivingEntity) (Object) this, stack));
        }
        return lootConsumer;
    }

    @Inject(method = "die", at = @At("HEAD"))
    void onDeath(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        Entity source = damageSource.getDirectEntity();

        if(source instanceof FallingBlockEntity fallingBlockEntity) {
            if(fallingBlockEntity.getBlockState().getBlock() instanceof AnvilBlock anvil) {
                UUID placer = ((AnvilBlockWhoPlaced) anvil).bacup$getPlacer();

                if(placer != null) {
                    // no need to close the level 😭
                    //noinspection resource
                    if(entity.level().getPlayerByUUID(placer) instanceof ServerPlayer player) {
                        Criteria.ANVIL_KILL.trigger(player, entity);
                    }
                }
            }
        }
    }
}
