package com.github.p1k0chu.bacup.advancement

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextColor

enum class AdvancementType(val message: String, val titleColor: TextColor, val descriptionColor: TextColor) {
    TASK(
        $$"%1$s has made the advancement %2$s%3$s%4$s",
        TextColor.fromLegacyFormat(ChatFormatting.GREEN)!!,
        TextColor.fromRgb(0x49DB49),
    ),
    GOAL(
        $$"%1$s has reached the goal %2$s%3$s%4$s",
        TextColor.fromRgb(0x75E1FF),
        TextColor.fromRgb(0x63BDD7),
    ),
    CHALLENGE(
        $$"%1$s has completed the challenge %2$s%3$s%4$s",
        TextColor.fromLegacyFormat(ChatFormatting.DARK_PURPLE)!!,
        TextColor.fromRgb(0xC900C7)
    ),
    SUPER_CHALLENGE(
        $$"%1$s has completed the super challenge %2$s%3$s%4$s",
        TextColor.fromRgb(0xFF2A2A),
        TextColor.fromRgb(0xDC2727)
    ),
    HIDDEN(
        $$"%1$s has found the hidden advancement %2$s%3$s%4$s",
        TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE)!!,
        TextColor.fromRgb(0xDE4ADC)
    );

    companion object {
        fun getFrame(type: AdvancementType): net.minecraft.advancements.AdvancementType {
            return when (type) {
                TASK -> net.minecraft.advancements.AdvancementType.TASK
                GOAL -> net.minecraft.advancements.AdvancementType.GOAL
                CHALLENGE -> net.minecraft.advancements.AdvancementType.CHALLENGE
                SUPER_CHALLENGE -> net.minecraft.advancements.AdvancementType.CHALLENGE
                HIDDEN -> net.minecraft.advancements.AdvancementType.CHALLENGE
            }
        }
    }
}