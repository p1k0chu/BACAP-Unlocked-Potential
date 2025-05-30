package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicateTypes
import com.github.p1k0chu.bacup.advancement.predicate.MapStatePredicate
import com.github.p1k0chu.bacup.command.BacapupCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.network.message.MessageType
import net.minecraft.network.message.SignedMessage
import net.minecraft.predicate.component.ComponentPredicate
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity


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
        CommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<ServerCommandSource>, _, _ ->
            BacapupCommand.register(dispatcher)
        }
        ServerMessageEvents.CHAT_MESSAGE.register { msg: SignedMessage, player: ServerPlayerEntity, _ ->
            if(msg.content.string.contains("глглту")) {
                val state = BacupPersistentState.getPlayerState(player)

                Criteria.GLGLTU.trigger(player, ++state.glgltuCounter)
            }
        }
    }

    fun id(id: String) = "$MOD_ID:$id"
}
