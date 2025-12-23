package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.MonstersTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object MonstersTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(MonstersTabGenerator.TAB_NAME) {
            advancement(MonstersTabGenerator.BEAM_ME_UP) {
                title("Beam Me Up")
                description("Teleport over 100 meters from a single throw of an Ender Pearl")
            }
            advancement(MonstersTabGenerator.FORGED_BY_FLESH) {
                title("Forged by Flesh")
                description("Retrieve an iron ingot from a defeated zombie")
            }
        }
    }
}