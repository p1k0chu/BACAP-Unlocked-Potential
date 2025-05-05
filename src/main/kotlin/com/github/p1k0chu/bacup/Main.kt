package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicateTypes
import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.predicate.component.ComponentPredicate
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.MinecraftServer


object Main : ModInitializer {
    const val MOD_ID: String = "bacapup"

    val mapStatePredicate: ComponentPredicate.Type<MapStatePredicate> = Registry.register(
        Registries.DATA_COMPONENT_PREDICATE_TYPE,
        id("map_state_predicate"),
        ComponentPredicate.Type(MapStatePredicate.CODEC)
    )

    var serverInstance: MinecraftServer? = null

    init {
        // touch these so java loads the classes
        MapColorPredicateTypes
        Criteria
    }

    override fun onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register { server ->
            serverInstance = server
        }
        ServerLifecycleEvents.SERVER_STOPPED.register {
            serverInstance = null
        }
    }

    fun id(id: String) = "$MOD_ID:$id"
}
