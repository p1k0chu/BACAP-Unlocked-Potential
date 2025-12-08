package com.github.p1k0chu.bacup.language

import com.github.p1k0chu.bacup.Main
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Component

fun title(tab: String, name: String): MutableComponent {
    return Component.translatable("${Main.MOD_ID}.advancement.$tab.$name.title")
}

fun description(tab: String, name: String): MutableComponent {
    return Component.translatable("${Main.MOD_ID}.advancement.$tab.$name.desc")
}

inline fun TranslationBuilder.advancementTab(name: String, block: AdvancementTabTranslationBuilder.() -> Unit) {
    AdvancementTabTranslationBuilder(this, name).block()
}

class AdvancementTabTranslationBuilder(val transBuilder: TranslationBuilder, val tab: String) {
    inline fun advancement(id: String, block: AdvancementBuilder.() -> Unit) {
        AdvancementBuilder(id).block()
    }

    inner class AdvancementBuilder(val id: String) {
        fun title(title: String) {
            transBuilder.add("${Main.MOD_ID}.advancement.$tab.$id.title", title)
        }

        fun description(description: String) {
            transBuilder.add("${Main.MOD_ID}.advancement.$tab.$id.desc", description)
        }
    }
}

