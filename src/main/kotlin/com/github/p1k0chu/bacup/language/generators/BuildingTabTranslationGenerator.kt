package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.BuildingTabGenerator
import com.github.p1k0chu.bacup.advancement.generator.EnchantingTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper

object BuildingTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(BuildingTabGenerator.TAB_NAME) {
            advancement(BuildingTabGenerator.FIRE_TRICK) {
                title("Fire Trick")
                description("Light a campfire and a candle by shooting an arrow at them")
            }
        }
    }
}