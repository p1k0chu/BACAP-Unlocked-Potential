package com.github.p1k0chu.bacup.command

import com.github.p1k0chu.bacup.BacupPersistentState
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object BacapupCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            literal<ServerCommandSource>("bacapup")
                .then(
                    literal<ServerCommandSource>("stats")
                        .executes { command: CommandContext<ServerCommandSource> ->
                            val player = command.source.player ?: return@executes 0
                            val state = BacupPersistentState.getPlayerState(player)
                            val text = Text.empty()

                            if (state.petsTamed.isNotEmpty()) {
                                text.append(Text.literal("Pets tamed:\n"))

                                state.petsTamed.forEach { (mob, count) ->
                                    text.append(Text.translatable(mob.translationKey))
                                        .append(": ")
                                        .append(
                                            Text.literal("$count\n")
                                                .withColor(Formatting.GOLD.colorValue!!)
                                        )
                                }
                            }

                            if (state.emeraldsObtained != 0) {
                                text.append("\nBought emeralds: ")
                                    .append(
                                        Text.literal("${state.emeraldsObtained}")
                                            .withColor(Formatting.GOLD.colorValue!!)
                                    )
                            }

                            player.sendMessage(text)

                            0
                        }
                ))
    }
}