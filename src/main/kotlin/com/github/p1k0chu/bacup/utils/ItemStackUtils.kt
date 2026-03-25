package com.github.p1k0chu.bacup.utils

import it.unimi.dsi.fastutil.objects.ReferenceSortedSets
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.util.CommonColors
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.component.CustomModelData
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.component.TooltipDisplay

fun makeTrophyItemStack(
    item: Item,
    advName: String,
    name: String,
    lore: String,
    loreColor: TextColor?,
    components: DataComponentPatch.Builder = DataComponentPatch.builder()
): ItemStackTemplate = ItemStackTemplate(
    item, components.set(
        DataComponents.CUSTOM_NAME, Component.literal(name).withStyle(Style.EMPTY.withBold(true).withItalic(true))
    ).set(DataComponents.CUSTOM_MODEL_DATA, CustomModelData(listOf(131f), listOf(), listOf(), listOf()))
        .set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay(false, ReferenceSortedSets.emptySet()))
        .set(DataComponents.CUSTOM_DATA, CustomData.of(CompoundTag().apply {
            putInt("Trophy", 1)
        })).set(
            DataComponents.LORE, ItemLore(
                listOf(
                    Component.literal(lore)
                        .withStyle(Style.EMPTY.withBold(false).withItalic(true).withColor(loreColor)),
                    Component.empty(),
                    Component.literal("Awarded for achieving").withStyle(Style.EMPTY.withColor(CommonColors.GRAY)),
                    Component.translatable(advName)
                )
            )
        ).build()
)

