package com.github.p1k0chu.bacup.language

import com.github.p1k0chu.bacup.language.generators.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EnglishLanguageProvider(
    dataOutput: FabricDataOutput?,
    wrapperLookup: CompletableFuture<RegistryWrapper.WrapperLookup>,
    private val generators: Collection<TranslationGenerator>
) : FabricLanguageProvider(dataOutput, "en_us", wrapperLookup) {
    override fun generateTranslations(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: TranslationBuilder
    ) {
        generators.forEach { it.accept(wrapperLookup, translationBuilder) }
    }
}

fun createTranslationProvider(
    output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
): FabricLanguageProvider {
    return EnglishLanguageProvider(
        output, registriesFuture, listOf(
            AdventureTabTranslationGenerator,
            AnimalsTabTranslationGenerator,
            EnchantingTabTranslationGenerator,
            FarmingTabTranslationGenerator,
            MiningTabTranslationGenerator,
            MonstersTabTranslationGenerator,
            StatisticsTabTranslationGenerator,
            EndTabTranslationGenerator,
            BuildingTabTranslationGenerator,
            ChallengesTabTranslationGenerator,
            MessagesTranslationGenerator,
        )
    )
}
