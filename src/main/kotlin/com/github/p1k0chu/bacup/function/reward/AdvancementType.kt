package com.github.p1k0chu.bacup.function.reward

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
        "dark_purple",
        "#C900C7"
    ),
    SUPER_CHALLENGE(
        "%1\$s has completed the super challenge %2\$s%3\$s%4\$s",
        "#FF2A2A",
        "#DC2727"
    ),
    HIDDEN(
        "%1\$s has found the hidden advancement %2\$s%3\$s%4\$s",
        "light_purple",
        "#DE4ADC"
    );
}
