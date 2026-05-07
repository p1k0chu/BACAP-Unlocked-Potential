package io.github.p1k0chu.bacapup.advancement.predicate

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.predicates.MinMaxBounds
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.saveddata.maps.MapItemSavedData
import java.util.*

class MapStatePredicate(
    val scale: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val centerX: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val centerZ: MinMaxBounds.Ints = MinMaxBounds.Ints.ANY,
    val dimension: Optional<ResourceKey<Level>> = Optional.empty(),
    val locked: Optional<Boolean> = Optional.empty(),
    val colors: Optional<MapColorPredicate> = Optional.empty()
) {
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

    fun matches(mapState: MapItemSavedData): Boolean {
        return scale.matches(mapState.scale.toInt())
                && centerX.matches(mapState.centerX)
                && centerZ.matches(mapState.centerZ)
                && (dimension.isEmpty || dimension.get() == mapState.dimension)
                && (locked.isEmpty || locked.get() == mapState.locked)
                && (colors.isEmpty || colors.get().test(mapState.colors))
    }
}
