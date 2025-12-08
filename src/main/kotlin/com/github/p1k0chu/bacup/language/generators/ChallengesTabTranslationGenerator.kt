package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.ChallengesTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object ChallengesTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(ChallengesTabGenerator.TAB_NAME) {
            advancement(ChallengesTabGenerator.MOB_FLATTENER_9000) {
                title("Mob Flattener 9000")
                description("Crush all mobs with an anvil")
            }
        }
    }
}