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
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.core.component.predicates.DataComponentPredicate
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.server.MinecraftServer
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer


object Main : ModInitializer {
    const val MOD_ID: String = "bacapup"

    val mapStatePredicate: DataComponentPredicate.Type<MapStatePredicate> = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE,
        id("map_state_predicate"),
        DataComponentPredicate.Type(MapStatePredicate.CODEC)
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
        CommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<CommandSourceStack>, _, _ ->
            BacapupCommand.register(dispatcher)
        }
        ServerMessageEvents.CHAT_MESSAGE.register { msg: PlayerChatMessage, player: ServerPlayer, _ ->
            if(msg.decoratedContent().string.contains("глглту")) {
                val state = BacupPersistentState.getPlayerState(player)

                Criteria.GLGLTU.trigger(player, ++state.glgltuCounter)
            }
        }
    }

    fun id(id: String) = "$MOD_ID:$id"
}
