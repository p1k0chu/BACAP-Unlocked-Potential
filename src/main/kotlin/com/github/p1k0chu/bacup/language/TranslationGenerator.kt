package com.github.p1k0chu.bacup.language

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.core.HolderLookup.Provider

interface TranslationGenerator {
    fun accept(wrapperLookup: Provider, translationBuilder: TranslationBuilder)
}