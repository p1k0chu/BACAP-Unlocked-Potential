package com.github.p1k0chu.bacup.command

import com.github.p1k0chu.bacup.BacupPersistentState
import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.language.generators.MessagesTranslationGenerator
import com.github.p1k0chu.bacup.language.generators.MessagesTranslationGenerator.BOUGHT_EMERALDS
import com.github.p1k0chu.bacup.language.generators.MessagesTranslationGenerator.GLGLTU_COUNTER
import com.github.p1k0chu.bacup.language.generators.MessagesTranslationGenerator.PETS_TAMED
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.ChatFormatting

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

                            if (state.petsTamed.isNotEmpty()) {
                                text.append(Component.translatable(PETS_TAMED))
                                    .append(Component.literal(":\n"))

                                state.petsTamed.forEach { (mob, count) ->
                                    text.append(Component.translatable(mob.descriptionId))
                                        .append(": ")
                                        .append(
                                            Component.literal("$count\n")
                                                .withColor(ChatFormatting.GOLD.color!!)
                                        )
                                }
                            }

                            if (state.emeraldsObtained != 0) {
                                text.append("\n")
                                    .append(Component.translatable(BOUGHT_EMERALDS))
                                    .append(": ")
                                    .append(
                                        Component.literal("${state.emeraldsObtained}")
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            if (state.glgltuCounter > 0) {
                                text.append("\n")
                                    .append(Component.translatable(GLGLTU_COUNTER))
                                    .append(": ")
                                    .append(
                                        Component.literal(state.glgltuCounter.toString())
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            player.sendSystemMessage(text)

                            0
                        }
                ))
    }
}
