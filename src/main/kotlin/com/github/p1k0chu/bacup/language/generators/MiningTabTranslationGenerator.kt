package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.MiningTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper

object MiningTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(MiningTabGenerator.TAB_NAME) {
            advancement(MiningTabGenerator.LEAFTERALLY) {
                title("Leafterally")
                description("Smelt leaf litter using leaf litter")
            }
        }
    }
}