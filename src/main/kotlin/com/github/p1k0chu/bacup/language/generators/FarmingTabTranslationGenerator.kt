package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.FarmingTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object FarmingTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(FarmingTabGenerator.TAB_NAME) {
            advancement(FarmingTabGenerator.ALTERNATIVE_FUEL) {
                title("Alternative Fuel")
                description("Power a furnace with a kelp block")
            }

            advancement(FarmingTabGenerator.SUPER_FUEL) {
                title("Super Fuel")
                description("Power a furnace with lava")
            }

            advancement(FarmingTabGenerator.TRASH_BIN) {
                title("Trash Bin")
                description("Throw an item into a cactus")
            }
        }
    }
}