package com.github.p1k0chu.bacup.command

import com.github.p1k0chu.bacup.BacapupDataAttachments
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
                            val petsTamed = player.getAttached(BacapupDataAttachments.PETS_TAMED);
                            val text = Component.empty()
                            var nl = 0

                            if (petsTamed != null) {
                                nl = 2
                                text.append("Pets tamed:")

                                petsTamed.forEach { mob, count ->
                                    text.append(Component.literal("\n"))
                                        .append(Component.translatable(mob.descriptionId))
                                        .append(": ")
                                        .append(
                                            Component.literal("$count")
                                                .withColor(ChatFormatting.GOLD.color!!)
                                        )
                                }
                            }

                            val emeraldsObtained = player.getAttached(BacapupDataAttachments.EMERALDS_OBTAINED)
                            if (emeraldsObtained != null && emeraldsObtained > 0) {
                                if (nl > 0) {
                                    text.append(Component.literal("\n".repeat(nl)))
                                }
                                nl = 1
                                text.append("Bought emeralds: ")
                                    .append(
                                        Component.literal("$emeraldsObtained")
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            val glgltuCounter = player.getAttached(BacapupDataAttachments.GLGLTU_COUNTER);
                            if (glgltuCounter != null && glgltuCounter > 0) {
                                if (nl > 0) {
                                    text.append(Component.literal("\n".repeat(nl)))
                                }
                                text.append("глглту sent: ")
                                    .append(
                                        Component.literal("$glgltuCounter")
                                            .withColor(ChatFormatting.GOLD.color!!)
                                    )
                            }

                            if (text.siblings.isEmpty()) {
                                command.source.sendSystemMessage(Component.literal("No stats yet :("))
                                1
                            } else {
                                command.source.sendSystemMessage(text)
                                0
                            }
                        }
                ))
    }
}
