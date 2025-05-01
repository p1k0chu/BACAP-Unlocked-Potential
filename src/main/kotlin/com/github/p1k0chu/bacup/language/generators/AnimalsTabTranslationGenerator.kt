package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.AnimalsTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper

object AnimalsTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(AnimalsTabGenerator.TAB_NAME) {
            advancement(AnimalsTabGenerator.WHEN_PIGS_FLY) {
                title("When Pigs Fly")
                description("Ride a pig off a cliff")
            }
            advancement(AnimalsTabGenerator.DOG_ARMY) {
                title("Dog Army")
                description("Befriend twenty wolves")
            }
            advancement(AnimalsTabGenerator.WOLOLO) {
                title("Wololo")
                description("Watch evoker recolor blue sheep in red")
            }
        }
    }
}
