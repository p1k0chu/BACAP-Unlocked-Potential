package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.Main
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import com.google.common.collect.Multimaps
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.authlib.properties.PropertyMap
import net.minecraft.Util
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.ResolvableProfile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
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
    val propertyMap = PropertyMap(ImmutableMultimap.of("textures", Property("textures", texture)))

    val playerHead = Items.PLAYER_HEAD.defaultInstance
    playerHead.set(DataComponents.PROFILE, ResolvableProfile.createResolved(GameProfile(Util.NIL_UUID, "", propertyMap)))

    return playerHead
}
