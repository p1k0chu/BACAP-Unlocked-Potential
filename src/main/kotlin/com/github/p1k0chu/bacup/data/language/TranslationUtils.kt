package com.github.p1k0chu.bacup.data.language

import com.github.p1k0chu.bacup.Main
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.text.MutableText
import net.minecraft.text.Text

fun title(tab: String, name: String): MutableText {
    return Text.translatable("${Main.MOD_ID}.advancement.$tab.$name.title")
}

fun description(tab: String, name: String): MutableText {
    return Text.translatable("${Main.MOD_ID}.advancement.$tab.$name.desc")
}

fun TranslationBuilder.advancementTab(name: String, block: AdvancementTabTranslationBuilder.() -> Unit) {
    AdvancementTabTranslationBuilder(this, name).block()
}

class AdvancementTabTranslationBuilder(val transBuilder: TranslationBuilder, val tab: String) {
    fun advancement(id: String, block: AdvancementTranslationBuilder.() -> Unit) {
        AdvancementTranslationBuilder(transBuilder, tab, id).block()
    }
}

class AdvancementTranslationBuilder(val transBuilder: TranslationBuilder, val tab: String, val id: String) {
    fun title(title: String) {
        transBuilder.add("${Main.MOD_ID}.advancement.$tab.$id.title", title)
    }

    fun description(description: String) {
        transBuilder.add("${Main.MOD_ID}.advancement.$tab.$id.desc", description)
    }
}
