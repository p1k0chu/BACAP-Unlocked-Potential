package com.github.p1k0chu.bacup

import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.predicate.MapColorPredicateTypes
import com.github.p1k0chu.bacup.command.BacapupCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object Main : ModInitializer {
    const val MOD_ID: String = "bacapup"
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    var serverInstance: MinecraftServer? = null
        private set

    init {
        // touch these so java loads the classes
        MapColorPredicateTypes
        Criteria
        BacapupDataAttachments.doNothing()
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
                val i: Int = player.getAttachedOrCreate(BacapupDataAttachments.GLGLTU_COUNTER) + 1
                player.setAttached(BacapupDataAttachments.GLGLTU_COUNTER, i)

                Criteria.GLGLTU.trigger(player, i)
            }
        }
    }

    fun id(id: String) = "$MOD_ID:$id"
}
