package io.github.p1k0chu.bacapup.mixin;

import io.github.p1k0chu.bacapup.BacapupDataAttachments;
import io.github.p1k0chu.bacapup.BacapupPetsTamed;
import io.github.p1k0chu.bacapup.advancement.criteria.Criteria;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
class TamableAnimalMixin {
    @Inject(method = "tame", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/criterion/TameAnimalTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/animal/Animal;)V"))
    private void setTamedBy(Player player, CallbackInfo ci, @Local ServerPlayer serverPlayer) {
        TamableAnimal tamed = (TamableAnimal) (Object) this;
        EntityType<?> type = (tamed).getType();

        BacapupPetsTamed.TrackedEntityType trackedEntityType = BacapupPetsTamed.TrackedEntityType.BY_TYPE_MAP.get(type);
        if (trackedEntityType != null) {
            BacapupPetsTamed petsTamed = serverPlayer.getAttachedOrCreate(BacapupDataAttachments.PETS_TAMED);
            petsTamed = trackedEntityType.apply(petsTamed, i -> i + 1);
            serverPlayer.setAttached(BacapupDataAttachments.PETS_TAMED, petsTamed);
            Criteria.PET_TAMED.trigger(serverPlayer, tamed, trackedEntityType.getter.apply(petsTamed));
        }
    }
}
