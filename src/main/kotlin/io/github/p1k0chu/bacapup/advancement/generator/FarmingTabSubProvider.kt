package io.github.p1k0chu.bacapup.advancement.generator

import io.github.p1k0chu.bacapup.advancement.*
import io.github.p1k0chu.bacapup.advancement.triggers.BacapupTriggers
import io.github.p1k0chu.bacapup.advancement.triggers.SingleItemTrigger
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import java.util.*

object FarmingTabSubProvider : AdvancementSubProvider {
    const val TAB_NAME = "farming"

    const val ALTERNATIVE_FUEL = "alternative_fuel"
    const val SUPER_FUEL = "super_fuel"
    const val TRASH_BIN = "trash_bin"
    const val THIS_IS_NOT_COOKIE_CLICKER = "this_is_not_cookie_clicker"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        val altFuel = advancement(consumer, TAB_NAME, ALTERNATIVE_FUEL) {
            parent(createPlaceholder("blazeandcave:farming/aquatic_biofuel"))
            display {
                title = "Alternative Fuel"
                description = "Power a furnace with a kelp block"
                icon = ItemStackTemplate(Items.DRIED_KELP_BLOCK)
            }
            itemReward(ItemStackTemplate(Items.DRIED_KELP_BLOCK, 1))

            addCriterion(
                "fuel", BacapupTriggers.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemTrigger.Instance(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.DRIED_KELP_BLOCK)
                                .build()))))
        }

        advancement(consumer, TAB_NAME, SUPER_FUEL) {
            parent(altFuel)
            display {
                title = "Super Fuel"
                description = "Power a furnace with lava"
                icon = ItemStackTemplate(Items.LAVA_BUCKET)
            }
            addCriterion(
                "fuel", BacapupTriggers.FURNACE_FUEL_CONSUMED.createCriterion(
                    SingleItemTrigger.Instance(
                        Optional.empty(), listOf(
                            ItemPredicate.Builder.item()
                                .of(provider.lookupOrThrow(Registries.ITEM), Items.LAVA_BUCKET)
                                .build()))))
        }

        advancement(consumer, TAB_NAME, TRASH_BIN) {
            parent(createPlaceholder("blazeandcave:farming/spikey"))
            display {
                title = "Trash Bin"
                description = "Throw an item into a cactus"
                icon = ItemStackTemplate(Items.COMPOSTER)
            }
            addCriterion("trash_bin", BacapupTriggers.CACTUS_DESTROY_ITEM.createCriterion(SingleItemTrigger.Instance()))
        }

        advancement(consumer, TAB_NAME, THIS_IS_NOT_COOKIE_CLICKER) {
            parent(createPlaceholder("blazeandcave:farming/me_love_cookie"))

            display {
                title = "This Is Not Cookie Clicker"
                description = "Why did you double click an empty slot?"
                icon = ItemStackTemplate(
                    Items.COOKIE,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.HIDDEN
            }

            addCriterion(
                "are_you_looking_for_something",
                impossibleTrigger()
            )
        }
    }
}
