package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.language.description
import com.github.p1k0chu.bacup.language.title
import net.minecraft.advancement.*
import net.minecraft.item.ItemStack
import net.minecraft.util.AssetInfo
import net.minecraft.util.Identifier
import java.util.*

class AdvancementBuilder(val tab: String, val name: String) : Advancement.Builder() {
    inline fun Advancement.Builder.display(block: AdvancementDisplayBuilder.() -> Unit) {
        this.display(AdvancementDisplayBuilder().apply(block).build())
    }

    inner class AdvancementDisplayBuilder {
        lateinit var icon: ItemStack
        var background: AssetInfo? = null
        var frame: AdvancementFrame = AdvancementFrame.TASK
        var showToast: Boolean = true
        var announceToChat: Boolean = true
        var hidden: Boolean = false

        fun build(): AdvancementDisplay {
            return AdvancementDisplay(
                icon,
                title(tab, name),
                description(tab, name),
                Optional.ofNullable(background),
                frame,
                showToast,
                announceToChat,
                hidden
            )
        }
    }
}

inline fun advancement(tab: String, name: String, block: AdvancementBuilder.() -> Unit): AdvancementEntry {
    val builder = AdvancementBuilder(tab, name)
    builder.apply(block)
    builder.rewards(AdvancementRewards.Builder.function(Identifier.of(id(tab, name))))
    return builder.build(Identifier.of(id(tab, name)))
}

