package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.NetherTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object NetherTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(NetherTabGenerator.TAB_NAME) {
            advancement(NetherTabGenerator.EXTREME_BREAK_RISK) {
                title("Extreme Break Risk")
                description("Break a block of netherite with your bare fist.")
            }
            advancement(NetherTabGenerator.BEACON_ALL_ITEMS) {
                title("Thanos Beacon")
                description("Power a beacon with every mineral")
            }
        }
    }
}