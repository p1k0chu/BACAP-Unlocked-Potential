package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.StatisticsTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup

object StatisticsTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: HolderLookup.Provider,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(StatisticsTabGenerator.TAB_NAME) {
            advancement(StatisticsTabGenerator.EMERALD_PORTFOLIO) {
                title("Emerald Portfolio")
                description("Obtain 200 emeralds through trade")
            }
            advancement(StatisticsTabGenerator.THE_STOCK_MARKET) {
                title("The Stock Market")
                description("Obtain a stack of emerald blocks through trading")
            }
            advancement(StatisticsTabGenerator.SMALL_BUSINESS) {
                title("Small Business")
                description("Buy 1000 emeralds in total")
            }
            advancement(StatisticsTabGenerator.SMALL_INDIE_COMPANY) {
                title("Small Indie Company")
                description("Make 27 stacks of emerald blocks on sales")
            }
            advancement(StatisticsTabGenerator.GLGLTU) {
                title("глглту")
                description("Send your first глглту in chat")
            }
            advancement(StatisticsTabGenerator.GLGLTU_CULT) {
                title("глглту cult")
                description("Send 50 глглту in chat")
            }
            advancement(StatisticsTabGenerator.GLGLTU_3) {
                title("You Can Mute Me, But You Can't Mute глглту")
                description("Send 200 глглту in chat")
            }
        }
    }
}