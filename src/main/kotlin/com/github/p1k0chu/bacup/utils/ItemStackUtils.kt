package com.github.p1k0chu.bacup.utils

import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.ItemStack
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.Component

fun ItemStack.setCustomName(name: String, color: Int? = null) {
    var customName = Component.nullToEmpty(name)

    if (color != null) {
        customName = customName.toFlatList(
                Style.EMPTY.withColor(color)
            ).first()
    }

    set(DataComponents.CUSTOM_NAME, customName)
}

fun ItemStack.setLore(lore: String) {
    set(
        DataComponents.LORE, ItemLore(
            listOf(
                Component.nullToEmpty(lore)
            )
        )
    )
}