package com.github.p1k0chu.bacup.advancement.predicate

import com.github.p1k0chu.bacup.Main
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.minecraft.predicate.NumberRange
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier
import java.util.function.Predicate

interface MapColorPredicate : Predicate<ByteArray> {
    companion object {
        val CODEC: Codec<MapColorPredicate> = MapColorPredicateType.REGISTRY.codec
            .dispatch("type", MapColorPredicate::getType) { it.codec }
    }

    fun getType(): MapColorPredicateType<*>

    class AllSame(val colors: List<NumberRange.IntRange>) : MapColorPredicate {
        companion object {
            val CODEC: MapCodec<AllSame> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    NumberRange.IntRange.CODEC.listOf()
                        .fieldOf("color")
                        .forGetter(AllSame::colors)
                ).apply(instance, ::AllSame)
            }
        }

        override fun getType(): MapColorPredicateType<*> {
            return MapColorPredicateTypes.ALL_SAME
        }

        override fun test(colors: ByteArray): Boolean = colors.all { color -> this.colors.any { it.test(color.toInt()) } }
    }

    class IsFilled(val isFilled: Boolean) : MapColorPredicate {
        companion object {
            val CODEC: MapCodec<IsFilled> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Codec.BOOL.fieldOf("is_filled")
                        .forGetter(IsFilled::isFilled)
                ).apply(instance, ::IsFilled)
            }
        }

        override fun getType(): MapColorPredicateType<*> {
            return MapColorPredicateTypes.IS_FILLED
        }

        override fun test(colors: ByteArray): Boolean = colors.none { it == 0.toByte() } == isFilled
    }
}

class MapColorPredicateType<T : MapColorPredicate>(val codec: MapCodec<T>) {
    companion object {
        val REGISTRY_KEY: RegistryKey<Registry<MapColorPredicateType<*>>> =
            RegistryKey.ofRegistry(Identifier.of(Main.MOD_ID, "map_color_predicate_type"))
        val REGISTRY: Registry<MapColorPredicateType<*>> =
            FabricRegistryBuilder.createSimple(REGISTRY_KEY)
                .buildAndRegister()
    }
}

object MapColorPredicateTypes {
    val ALL_SAME = register("all_same", MapColorPredicateType(MapColorPredicate.AllSame.CODEC))
    val IS_FILLED = register("is_filled", MapColorPredicateType(MapColorPredicate.IsFilled.CODEC))

    private fun <T : MapColorPredicate> register(id: String, type: MapColorPredicateType<T>): MapColorPredicateType<T> {
        return Registry.register(MapColorPredicateType.REGISTRY, Identifier.of(Main.MOD_ID, id), type)
    }
}
