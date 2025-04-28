package com.github.p1k0chu.bacup.language

import com.github.p1k0chu.bacup.advancement.generator.*
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
        translationBuilder.advancementTab(AdventureTabGenerator.TAB_NAME) {
            advancement(AdventureTabGenerator.CAT_GIFT) {
                title("Where have you been?")
                description("Receive a gift from a tamed cat in the morning.")
            }

            advancement(AdventureTabGenerator.ALL_CAT_GIFTS) {
                title("A Meow Massages The Heart")
                description("Receive every kind of gifts from your cat")
            }

            advancement(AdventureTabGenerator.LOCK_MAP) {
                title("Do It For The Frame")
                description("Use a glass pane in a cartography table to lock a map, preventing it from updating")
            }
        }

        translationBuilder.advancementTab(AnimalsTabGenerator.TAB_NAME) {
            advancement(AnimalsTabGenerator.WHEN_PIGS_FLY) {
                title("When Pigs Fly")
                description("Ride a pig off a cliff")
            }
        }

        translationBuilder.advancementTab(EnchantingTabGenerator.TAB_NAME) {
            advancement(EnchantingTabGenerator.DISENCHANTED) {
                title("Disenchanted")
                description("Use a Grindstone to get experience from an enchanted item.")
            }
        }

        translationBuilder.advancementTab(FarmingTabGenerator.TAB_NAME) {
            advancement(FarmingTabGenerator.ALTERNATIVE_FUEL) {
                title("Alternative Fuel")
                description("Power a furnace with a kelp block")
            }

            advancement(FarmingTabGenerator.SUPER_FUEL) {
                title("Super Fuel")
                description("Power a furnace with lava")
            }
        }

        translationBuilder.advancementTab(MonstersTabGenerator.TAB_NAME) {
            advancement(MonstersTabGenerator.BEAM_ME_UP) {
                title("Beam Me Up")
                description("Teleport over 100 meters from a single throw of an Ender Pearl")
            }
        }
    }
}
