package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.language.description
import com.github.p1k0chu.bacup.language.title
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.DisplayInfo
import net.minecraft.world.item.ItemStack
import net.minecraft.core.ClientAsset
import net.minecraft.resources.Identifier
import java.util.*

class AdvancementBuilder(val tab: String, val name: String) : Advancement.Builder() {
    inline fun Advancement.Builder.display(block: AdvancementDisplayBuilder.() -> Unit) {
        this.display(AdvancementDisplayBuilder().apply(block).build())
    }

    inner class AdvancementDisplayBuilder {
        lateinit var icon: ItemStack
        var background: ClientAsset? = null
        var frame: AdvancementType = AdvancementType.TASK
        var showToast: Boolean = true
        var announceToChat: Boolean = true
        var hidden: Boolean = false

        fun build(): DisplayInfo {
            return DisplayInfo(
                icon,
                title(tab, name),
                description(tab, name),
                Optional.ofNullable(background?.id()?.let(ClientAsset::ResourceTexture)),
                frame,
                showToast,
                announceToChat,
                hidden
            )
        }
    }
}

inline fun advancement(tab: String, name: String, block: AdvancementBuilder.() -> Unit): AdvancementHolder {
    val builder = AdvancementBuilder(tab, name)
    builder.apply(block)
    builder.rewards(AdvancementRewards.Builder.function(Identifier.parse(id(tab, name))))
    return builder.build(Identifier.parse(id(tab, name)))
}

