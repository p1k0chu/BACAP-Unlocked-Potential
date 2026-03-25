package com.github.p1k0chu.bacup.utils

import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.component.ItemLore

fun DataComponentPatch.Builder.setCustomName(name: String, color: Int? = null): DataComponentPatch.Builder {
    var customName = Component.nullToEmpty(name)

    if (color != null) {
        customName = customName.toFlatList(
            Style.EMPTY.withColor(color)
        ).first()
    }

    return set(DataComponents.CUSTOM_NAME, customName)
}

fun DataComponentPatch.Builder.setLore(lore: String): DataComponentPatch.Builder {
    return set(
        DataComponents.LORE, ItemLore(
            listOf(
                Component.nullToEmpty(lore)
            )
        )
    )
}
