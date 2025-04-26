package com.github.p1k0chu.bacup.data.language

import com.github.p1k0chu.bacup.data.advancement.AdventureTabAdvancementProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EnglishLanguageProvider(
    dataOutput: FabricDataOutput?,
    wrapperLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricLanguageProvider(dataOutput, "en_us", wrapperLookup) {
    override fun generateTranslations(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: TranslationBuilder
    ) {
        translationBuilder.advancementTab(AdventureTabAdvancementProvider.TAB_NAME) {
            advancement(AdventureTabAdvancementProvider.CAT_GIFT) {
                title("Where have you been?")
                description("Receive a gift from a tamed cat in the morning.")
            }

            advancement(AdventureTabAdvancementProvider.ALL_CAT_GIFTS) {
                title("A Meow Massages The Heart")
                description("Receive every kind of gifts from your cat")
            }
        }
    }
}
