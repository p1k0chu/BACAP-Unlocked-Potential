package com.github.p1k0chu.bacup.utils

import com.github.p1k0chu.bacup.language.title
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.ItemStack
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor
import net.minecraft.util.CommonColors
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.component.CustomModelData
import net.minecraft.world.item.component.TooltipDisplay

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

fun makeTrophyItemStack(
    item: Item,
    advTab: String,
    advName: String,
    name: String,
    lore: String,
    loreColor: TextColor?
): ItemStack =
    item.defaultInstance.apply {
        set(
            DataComponents.CUSTOM_NAME,
            Component.literal(name).withStyle(Style.EMPTY.withBold(true).withItalic(true))
        )
        set(DataComponents.CUSTOM_MODEL_DATA, CustomModelData(listOf(131f), listOf(), listOf(), listOf()))
        set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay(false, ReferenceSortedSets.emptySet()))
        CompoundTag().let { customData ->
            customData.putInt("Trophy", 1)
            set(DataComponents.CUSTOM_DATA, CustomData.of(customData))
        }
        set(
            DataComponents.LORE, ItemLore(
                listOf(
                    Component.literal(lore)
                        .withStyle(Style.EMPTY.withBold(false).withItalic(true).withColor(loreColor)),
                    Component.empty(),
                    Component.literal("Awarded for achieving").withStyle(Style.EMPTY.withColor(CommonColors.GRAY)),
                    title(advTab, advName)
                )
            )
        )
    }

