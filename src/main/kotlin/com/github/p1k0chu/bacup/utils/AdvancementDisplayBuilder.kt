package com.github.p1k0chu.bacup.utils

import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementDisplay
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.AssetInfo
import java.util.*

class AdvancementDisplayBuilder {
    // boolean showToast, boolean announceToChat, boolean hidden
    lateinit var icon: ItemStack
    var title = Text.of("")
    var description = Text.of("")
    var background: AssetInfo? = null
    var frame: AdvancementFrame = AdvancementFrame.TASK
    var showToast: Boolean = true
    var announceToChat: Boolean = true
    var hidden: Boolean = false

    fun build(): AdvancementDisplay {
        return AdvancementDisplay(
            icon, title, description, Optional.ofNullable(background), frame, showToast, announceToChat, hidden
        )
    }
}

fun Advancement.Builder.display(block: AdvancementDisplayBuilder.() -> Unit): Advancement.Builder {
    val builder = AdvancementDisplayBuilder()
    builder.block()
    return this.display(builder.build())
}
