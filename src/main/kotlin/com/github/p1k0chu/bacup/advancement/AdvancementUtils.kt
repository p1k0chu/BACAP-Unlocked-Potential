package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.Main
import com.mojang.authlib.properties.Property
import com.mojang.authlib.properties.PropertyMap
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ProfileComponent
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import java.util.*

fun id(tab: String, name: String): String {
    return Main.id("$tab/$name")
}

/**
 * Construct a custom player head without profile name nor id, but with texture.
 * @param texture a string to be put inside of head's properties, in 'textures' field
 * @return a player head
 */
fun getPlayerHead(texture: String): ItemStack {
    val propertyMap = PropertyMap()
    propertyMap.put("textures", Property("textures", texture))

    val playerHead = Items.PLAYER_HEAD.defaultStack
    playerHead.set(DataComponentTypes.PROFILE, ProfileComponent(Optional.empty(), Optional.empty(), propertyMap))

    return playerHead
}
