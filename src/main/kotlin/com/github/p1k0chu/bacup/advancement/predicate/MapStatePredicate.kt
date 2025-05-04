package com.github.p1k0chu.bacup.advancement.predicate

import com.github.p1k0chu.bacup.Main
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.component.ComponentType
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.MapIdComponent
import net.minecraft.item.map.MapState
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.component.ComponentSubPredicate
import net.minecraft.registry.RegistryKey
import net.minecraft.world.World
import java.util.*

class MapStatePredicate(
    val scale: NumberRange.IntRange = NumberRange.IntRange.ANY,
    val centerX: NumberRange.IntRange = NumberRange.IntRange.ANY,
    val centerZ: NumberRange.IntRange = NumberRange.IntRange.ANY,
    val dimension: Optional<RegistryKey<World>> = Optional.empty(),
    val locked: Optional<Boolean> = Optional.empty()
) : ComponentSubPredicate<MapIdComponent> {
    companion object {
        val CODEC = RecordCodecBuilder.create { instance ->
            instance.group(
                NumberRange.IntRange.CODEC.optionalFieldOf("scale", NumberRange.IntRange.ANY)
                    .forGetter(MapStatePredicate::scale),
                NumberRange.IntRange.CODEC.optionalFieldOf("centerX", NumberRange.IntRange.ANY)
                    .forGetter(MapStatePredicate::centerX),
                NumberRange.IntRange.CODEC.optionalFieldOf("centerZ", NumberRange.IntRange.ANY)
                    .forGetter(MapStatePredicate::centerZ),
                World.CODEC.optionalFieldOf("dimension")
                    .forGetter(MapStatePredicate::dimension),
                Codec.BOOL.optionalFieldOf("locked")
                    .forGetter(MapStatePredicate::locked)
            ).apply(instance, ::MapStatePredicate)
        }
    }

    override fun test(component: MapIdComponent): Boolean {
        val world = Main.serverInstance!!.overworld
        val state = world!!.getMapState(component) ?: return false

        return test(state)
    }

    private fun test(mapState: MapState): Boolean {
        return scale.test(mapState.scale.toInt())
                && centerX.test(mapState.centerX)
                && centerZ.test(mapState.centerZ)
                && (dimension.isEmpty || dimension.get() == mapState.dimension)
                && (locked.isEmpty || locked.get() == mapState.locked)
    }

    override fun getComponentType(): ComponentType<MapIdComponent> {
        return DataComponentTypes.MAP_ID
    }
}
