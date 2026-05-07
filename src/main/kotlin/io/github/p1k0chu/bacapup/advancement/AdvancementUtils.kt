package io.github.p1k0chu.bacapup.advancement

import io.github.p1k0chu.bacapup.Main
import com.google.common.collect.ImmutableMultimap
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.authlib.properties.PropertyMap
import net.minecraft.advancements.triggers.CriteriaTriggers
import net.minecraft.advancements.triggers.ImpossibleTrigger
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.util.Util
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ResolvableProfile

fun id(tab: String, name: String): String {
    return Main.id("$tab/$name")
}

/**
 * Construct a custom player head without profile name nor id, but with texture.
 * @param texture a string to be put inside of head's properties, in 'textures' field
 * @return a player head
 */
fun getPlayerHead(texture: String): ItemStackTemplate {
    val propertyMap = PropertyMap(ImmutableMultimap.of("textures", Property("textures", texture)))

    val components = DataComponentPatch.builder()
        .set(DataComponents.PROFILE, ResolvableProfile.createResolved(GameProfile(Util.NIL_UUID, "", propertyMap)))
        .build()

    return ItemStackTemplate(Items.PLAYER_HEAD, components)
}

fun impossibleTrigger() = CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance())
