package com.github.p1k0chu.bacup.mixin;

import com.github.p1k0chu.bacup.advancement.criteria.Criteria;
import com.github.p1k0chu.bacup.imixin.AnvilBlockWhoPlaced;
import net.minecraft.block.AnvilBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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
    public abstract @Nullable PlayerEntity getAttackingPlayer();

    @Inject(method = "damage", at = @At(value = "HEAD"))
    void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
    }

    @ModifyArg(method = "dropLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;JLjava/util/function/Consumer;)V"), index = 2)
    Consumer<ItemStack> modifyLootConsumer(Consumer<ItemStack> lootConsumer) {
        if (getAttackingPlayer() instanceof ServerPlayerEntity serverPlayerEntity) {
            return lootConsumer.andThen(stack -> Criteria.ENTITY_DROPPED_LOOT.trigger(serverPlayerEntity, (LivingEntity) (Object) this, stack));
        }
        return lootConsumer;
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    void onDeath(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        Entity source = damageSource.getSource();

        if(source instanceof FallingBlockEntity fallingBlockEntity) {
            if(fallingBlockEntity.getBlockState().getBlock() instanceof AnvilBlock anvil) {
                UUID placer = ((AnvilBlockWhoPlaced) anvil).bacup$getPlacer();

                if(placer != null) {
                    if(entity.getWorld().getPlayerByUuid(placer) instanceof ServerPlayerEntity player) {
                        Criteria.ANVIL_KILL.trigger(player, entity);
                    }
                }
            }
        }
    }
}
