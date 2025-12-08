package com.github.p1k0chu.bacup.advancement.predicate

import com.github.p1k0chu.bacup.Main
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.DataComponents
import net.minecraft.world.level.saveddata.maps.MapId
import net.minecraft.world.level.saveddata.maps.MapItemSavedData
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.advancements.critereon.SingleComponentItemPredicate
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import java.util.*

class MapStatePredicate(
    val scale: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val centerX: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val centerZ: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val dimension: Optional<ResourceKey<Level>> = Optional.empty(),
    val locked: Optional<Boolean> = Optional.empty(),
    val colors: Optional<MapColorPredicate> = Optional.empty()
) : SingleComponentItemPredicate<MapId> {
    companion object {
        val CODEC: Codec<MapStatePredicate> = RecordCodecBuilder.create { instance ->
            instance.group(
                MinMaxBounds.Ints.CODEC.optionalFieldOf("scale", MinMaxBounds.Ints.ANY)
                    .forGetter(MapStatePredicate::scale),
                MinMaxBounds.Ints.CODEC.optionalFieldOf("centerX", MinMaxBounds.Ints.ANY)
                    .forGetter(MapStatePredicate::centerX),
                MinMaxBounds.Ints.CODEC.optionalFieldOf("centerZ", MinMaxBounds.Ints.ANY)
                    .forGetter(MapStatePredicate::centerZ),
                Level.RESOURCE_KEY_CODEC.optionalFieldOf("dimension")
                    .forGetter(MapStatePredicate::dimension),
                Codec.BOOL.optionalFieldOf("locked")
                    .forGetter(MapStatePredicate::locked),
                MapColorPredicate.CODEC.optionalFieldOf("colors")
                    .forGetter(MapStatePredicate::colors)
            ).apply(instance, ::MapStatePredicate)
        }
    }

    override fun matches(component: MapId): Boolean {
        val world = Main.serverInstance?.overworld()
        val state = world?.getMapData(component) ?: return false

        return test(state)
    }

    private fun test(mapState: MapItemSavedData): Boolean {
        return scale.matches(mapState.scale.toInt())
                && centerX.matches(mapState.centerX)
                && centerZ.matches(mapState.centerZ)
                && (dimension.isEmpty || dimension.get() == mapState.dimension)
                && (locked.isEmpty || locked.get() == mapState.locked)
                && (colors.isEmpty || colors.get().test(mapState.colors))
    }

    override fun componentType(): DataComponentType<MapId> {
        return DataComponents.MAP_ID
    }
}
