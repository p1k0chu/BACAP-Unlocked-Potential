package com.github.p1k0chu.bacup.language

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.registry.RegistryWrapper.WrapperLookup

interface TranslationGenerator {
    fun accept(wrapperLookup: WrapperLookup, translationBuilder: TranslationBuilder)
}