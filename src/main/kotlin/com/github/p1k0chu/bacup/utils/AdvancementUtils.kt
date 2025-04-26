package com.github.p1k0chu.bacup.utils

import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.util.Identifier

fun advancement(id: String, block: Advancement.Builder.() -> Unit): AdvancementEntry {
    val builder = Advancement.Builder.createUntelemetered()
    builder.apply(block)
    return builder.build(Identifier.of(id))
}

fun Advancement.Builder.functionReward(function: String) = this.rewards(AdvancementRewards.Builder.function(Identifier.of(function)))

@Deprecated(message = "Uses deprecated minecraft method")
fun Advancement.Builder.parent(parentId: String) = this.parent(Identifier.of(parentId))