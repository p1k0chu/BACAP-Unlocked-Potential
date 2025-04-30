package com.github.p1k0chu.bacup.language.generators

import com.github.p1k0chu.bacup.advancement.generator.AdventureTabGenerator
import com.github.p1k0chu.bacup.language.TranslationGenerator
import com.github.p1k0chu.bacup.language.advancementTab
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper

object AdventureTabTranslationGenerator : TranslationGenerator {
    override fun accept(
        wrapperLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: FabricLanguageProvider.TranslationBuilder
    ) {
        translationBuilder.advancementTab(AdventureTabGenerator.TAB_NAME) {
            advancement(AdventureTabGenerator.CAT_GIFT) {
                title("Where have you been?")
                description("Receive a gift from a tamed cat in the morning.")
            }

            advancement(AdventureTabGenerator.PLETHORA_OF_CATS) {
                title("Plethora of Cats")
                description("Befriend twenty stray cats")
            }

            advancement(AdventureTabGenerator.ALL_CAT_GIFTS) {
                title("A Meow Massages The Heart")
                description("Receive every kind of gifts from your cat")
            }

            advancement(AdventureTabGenerator.LOCK_MAP) {
                title("Do It For The Frame")
                description("Use a glass pane in a cartography table to lock a map, preventing it from updating")
            }

            advancement(AdventureTabGenerator.GET_RAID_OF_IT) {
                title("Get Raid Of It")
                description("Drink milk to remove the Bad Omen effect")
            }

            advancement(AdventureTabGenerator.CAN_YOU_HEAR_IT_FROM_HERE) {
                title("Can you hear it from here?")
                description("Shoot a bell from more than 50 blocks away")
            }
        }
    }
}