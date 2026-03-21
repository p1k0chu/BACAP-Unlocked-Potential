package com.github.p1k0chu.bacup.command

import com.github.p1k0chu.bacup.BacupPersistentState
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

object BacapupCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal<CommandSourceStack>("bacapup")
                .then(
                    literal<CommandSourceStack>("stats")
                        .executes { command: CommandContext<CommandSourceStack> ->
                            val player = command.source.player ?: return@executes 0
                            val state = BacupPersistentState.getPlayerState(player)
                            val text = Component.empty()
                            var nl = 0

                            if (state.petsTamed.isNotEmpty()) {
                                nl = 2
                                text.append(Component.literal("Pets tamed:"))

                                state.petsTamed.forEach { (mob, count) ->
                                    text.append(Component.literal("\n"))
                                        .append(Component.translatable(mob.descriptionId))
                                        .append(": ")
                                        .append(
                                            Component.literal("$count")
                                                .withColor(ChatFormatting.GOLD.color!!)
                                        )
                                }
                            }

                            if (state.emeraldsObtained != 0) {
                                if (nl > 0) {
                                    text.append(Component.literal("\n".repeat(nl)))
                                }
                                nl = 1
                                text.append(Component.literal("Bought emeralds: "))
                                    .append(
                                        Component.literal("${state.emeraldsObtained}")
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            if (state.glgltuCounter > 0) {
                                if (nl > 0) {
                                    text.append(Component.literal("\n".repeat(nl)))
                                }
                                text.append(Component.literal("глглту sent: "))
                                    .append(
                                        Component.literal(state.glgltuCounter.toString())
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            if (text.siblings.isEmpty()) {
                                command.source.sendFailure(Component.literal("No stats yet :("))
                                1
                            } else {
                                command.source.sendSuccess({ text }, false)
                                0
                            }
                        }
                ))
    }
}
