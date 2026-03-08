package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.BiomesTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object BiomesTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(BiomesTabGenerator.TAB_NAME) {
            advancement(BiomesTabGenerator.CONDUEL) {
                title("Conduel")
                description("Make conduit kill a mob")
            }
        }
    }
}