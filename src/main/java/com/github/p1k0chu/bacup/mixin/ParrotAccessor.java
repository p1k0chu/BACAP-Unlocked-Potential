package com.github.p1k0chu.bacup.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Parrot.class)
public interface ParrotAccessor {
    @Accessor("MOB_SOUND_MAP")
    static Map<EntityType<?>, SoundEvent> getMobSoundMap() {
        throw new AssertionError();
    }
}
