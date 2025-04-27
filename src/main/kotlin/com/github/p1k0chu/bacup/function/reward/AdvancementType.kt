package com.github.p1k0chu.bacup.function.reward

import net.minecraft.advancement.AdvancementFrame

enum class AdvancementType(val message: String, val titleColor: String, val descriptionColor: String) {
    TASK(
        "%1\$s has made the advancement %2\$s%3\$s%4\$s",
        "green",
        "#49DB49"
    ),
    GOAL(
        "%1\$s has reached the goal %2\$s%3\$s%4\$s",
        "#75E1FF",
        "#63BDD7"
    ),
    CHALLENGE(
        "%1\$s has completed the challenge %2\$s%3\$s%4\$s",
        "#FF2A2A",
        "#DC2727"
    ),
    SUPER_CHALLENGE(
        "%1\$s has completed the super challenge %2\$s%3\$s%4\$s",
        "dark_purple",
        "#49DB49"
    );

    fun from(frame: AdvancementFrame): AdvancementType {
        return when (frame) {
            AdvancementFrame.TASK -> TASK
            AdvancementFrame.GOAL -> GOAL
            AdvancementFrame.CHALLENGE -> CHALLENGE
        }
    }
}
