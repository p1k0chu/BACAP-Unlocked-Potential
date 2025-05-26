package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.language.TranslationGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper

object MessagesTranslationGenerator : TranslationGenerator {
    const val PETS_TAMED = "${Main.MOD_ID}.messages.pets_tamed"
    const val BOUGHT_EMERALDS = "${Main.MOD_ID}.messages.bought_emeralds"
    const val GLGLTU_COUNTER = "${Main.MOD_ID}.messages.glgltu_counter"

    override fun accept(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.add(PETS_TAMED, "Pets tamed")
        translationBuilder.add(BOUGHT_EMERALDS, "Bought emeralds")
        translationBuilder.add(GLGLTU_COUNTER, "глглту sent")
    }
}