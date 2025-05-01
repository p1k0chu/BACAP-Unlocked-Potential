package com.github.p1k0chu.bacup.utils

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.LoreComponent
import net.minecraft.item.ItemStack
import net.minecraft.text.Style
import net.minecraft.text.Text

fun ItemStack.setCustomName(name: String, color: Int? = null) {
    var customName = Text.of(name)

    if (color != null) {
        customName = customName.getWithStyle(
                Style.EMPTY.withColor(color)
            ).first()
    }

    set(DataComponentTypes.CUSTOM_NAME, customName)
}

fun ItemStack.setLore(lore: String) {
    set(
        DataComponentTypes.LORE, LoreComponent(
            listOf(
                Text.of(lore)
            )
        )
    )
}