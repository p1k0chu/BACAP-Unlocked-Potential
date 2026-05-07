package io.github.p1k0chu.bacapup.command

import io.github.p1k0chu.bacapup.BacapupDataAttachments
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor
import net.minecraft.util.CommonColors

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
                                                .withColor(TextColor.GOLD)
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
                                            .withColor(TextColor.GOLD)
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
                                            .withColor(TextColor.GOLD)
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
