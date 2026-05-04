package io.github.p1k0chu.bacapup.mixin;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragonFight.class)
public interface EnderDragonFightAccessor {
    @Accessor
    ServerBossEvent getDragonEvent();
}
