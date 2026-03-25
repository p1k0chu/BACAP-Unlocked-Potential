package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.AdvancementConsumer
import com.github.p1k0chu.bacup.advancement.AdvancementGenerator
import com.github.p1k0chu.bacup.advancement.AdvancementType
import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponents
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions
import java.util.*

object StatisticsTabGenerator : AdvancementGenerator {
    const val TAB_NAME = "statistics"
    const val ROOT = "blazeandcave:statistics/root"

    const val EMERALD_PORTFOLIO = "emerald_portfolio"
    const val THE_STOCK_MARKET = "the_stock_market"
    const val SMALL_BUSINESS = "small_business"
    const val SMALL_INDIE_COMPANY = "small_indie_company"

    const val GLGLTU = "glgltu"
    const val GLGLTU_CULT = "glgltu_cult"
    const val GLGLTU_3 = "glgltu_3"

    override fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer) {
        val emeraldPortfolio = advancement(consumer, TAB_NAME, EMERALD_PORTFOLIO) {
            parent(createPlaceholder(ROOT))
            display {
                title = "Emerald Portfolio"
                description = "Obtain 200 emeralds through trade"
                icon = ItemStackTemplate(Items.EMERALD)
            }
            addCriterion(
                "200", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(200))
                    )
                )
            )
        }

        val stockMarket = advancement(consumer, TAB_NAME, THE_STOCK_MARKET) {
            parent(emeraldPortfolio)
            display {
                title = "The Stock Market"
                description = "Obtain a stack of emerald blocks through trading"
                icon = ItemStackTemplate(
                    Items.EMERALD,
                    DataComponentPatch.builder()
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
                )
                type = AdvancementType.GOAL
            }
            addCriterion(
                "576", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(576)),
                    )
                )
            )
        }

        val smallBusiness = advancement(consumer, TAB_NAME, SMALL_BUSINESS) {
            parent(stockMarket)
            display {
                title = "Small Business"
                description = "Buy 1000 emeralds in total"
                icon = ItemStackTemplate(Items.EMERALD_BLOCK)
                type = AdvancementType.CHALLENGE
            }
            itemReward(ItemStackTemplate(Items.EMERALD, 8))
            exp = 50

            addCriterion(
                "1000", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(1000)),
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, SMALL_INDIE_COMPANY) {
            parent(smallBusiness)
            display {
                title = "Small Indie Company"
                description = "Make 27 stacks of emerald blocks on sales"
                icon = ItemStackTemplate(Items.DEEPSLATE_EMERALD_ORE)
                type = AdvancementType.HIDDEN
                hidden = true
            }
            exp = 200

            addCriterion(
                "15552", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(15552)),
                    )
                )
            )
        }

        val glgltu = advancement(consumer, TAB_NAME, GLGLTU) {
            parent(createPlaceholder(ROOT))
            display {
                title = "глглту"
                description = "Send your first глглту in chat"
                icon = ItemStackTemplate(
                    Items.TIPPED_ARROW,
                    DataComponentPatch.builder()
                        .set(DataComponents.POTION_CONTENTS, PotionContents(Potions.STRENGTH))
                        .build()
                )
            }
            addCriterion(
                "glgltu", Criteria.GLGLTU.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            MinMaxBounds.Ints.atLeast(1)
                        )
                    )
                )
            )
        }

        val glgltuCult = advancement(consumer, TAB_NAME, GLGLTU_CULT) {
            parent(glgltu)
            display {
                title = "глглту cult"
                description = "Send 50 глглту in chat"
                icon = ItemStackTemplate(Items.SPECTRAL_ARROW)
                type = AdvancementType.GOAL
            }
            addCriterion(
                "glgltu_cult", Criteria.GLGLTU.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            MinMaxBounds.Ints.atLeast(50)
                        )
                    )
                )
            )
        }

        advancement(consumer, TAB_NAME, GLGLTU_3) {
            parent(glgltuCult)
            display {
                title = "You Can Mute Me, But You Can't Mute глглту"
                description = "Send 200 глглту in chat"
                icon = ItemStackTemplate(Items.WRITABLE_BOOK)
                type = AdvancementType.CHALLENGE
            }
            addCriterion(
                "glgltu_3", Criteria.GLGLTU.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(
                            MinMaxBounds.Ints.atLeast(200)
                        )
                    )
                )
            )
        }
    }
}
