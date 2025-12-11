package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.data.advancements.AdvancementSubProvider.createPlaceholder
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.HolderLookup
import java.util.*
import java.util.function.Consumer

object StatisticsTabGenerator : AdvancementSubProvider {
    const val TAB_NAME = "statistics"
    const val ROOT = "blazeandcave:statistics/root"

    const val EMERALD_PORTFOLIO = "emerald_portfolio"
    const val THE_STOCK_MARKET = "the_stock_market"
    const val SMALL_BUSINESS = "small_business"
    const val SMALL_INDIE_COMPANY = "small_indie_company"

    const val GLGLTU = "glgltu"
    const val GLGLTU_CULT = "glgltu_cult"
    const val GLGLTU_3 = "glgltu_3"

    override fun generate(wrapperLookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        val emeraldPortfolio = advancement(TAB_NAME, EMERALD_PORTFOLIO) {
            parent(createPlaceholder(ROOT))
            display {
                icon = Items.EMERALD.defaultInstance
            }
            addCriterion("200", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(MinMaxBounds.Ints.atLeast(200))
                )
            ))
        }.also(consumer::accept)

        val stockMarket = advancement(TAB_NAME, THE_STOCK_MARKET) {
            parent(emeraldPortfolio)
            display {
                icon = Items.EMERALD.defaultInstance.apply {
                    set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                frame = AdvancementType.GOAL
            }
            addCriterion(
                "576", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(576)),
                    )
                )
            )
        }.also(consumer::accept)

        val smallBusiness = advancement(TAB_NAME, SMALL_BUSINESS) {
            parent(stockMarket)
            display {
                icon = Items.EMERALD_BLOCK.defaultInstance
                frame = AdvancementType.CHALLENGE
            }
            addCriterion(
                "1000", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(1000)),
                    )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, SMALL_INDIE_COMPANY) {
            parent(smallBusiness)
            display {
                icon = Items.DEEPSLATE_EMERALD_ORE.defaultInstance
                frame =  AdvancementType.CHALLENGE
                hidden = true
            }
            addCriterion(
                "15552", Criteria.TRADED_FOR_EMERALDS.createCriterion(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(MinMaxBounds.Ints.atLeast(15552)),
                    )
                )
            )
        }.also(consumer::accept)

        val glgltu = advancement(TAB_NAME, GLGLTU) {
            parent(createPlaceholder(ROOT))
            display {
                icon = Items.TIPPED_ARROW.defaultInstance.apply {
                    set(DataComponents.POTION_CONTENTS, PotionContents(Potions.STRENGTH))
                }
            }
            addCriterion("glgltu", Criteria.GLGLTU.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        MinMaxBounds.Ints.atLeast(1)
                    )
                )
            ))
        }.also(consumer::accept)

        val glgltuCult = advancement(TAB_NAME, GLGLTU_CULT) {
            parent(glgltu)
            display {
                icon = Items.SPECTRAL_ARROW.defaultInstance
                frame = AdvancementType.GOAL
            }
            addCriterion("glgltu_cult", Criteria.GLGLTU.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        MinMaxBounds.Ints.atLeast(50)
                    )
                )
            ))
        }.also(consumer::accept)

        advancement(TAB_NAME, GLGLTU_3) {
            parent(glgltuCult)
            display {
                icon = Items.WRITABLE_BOOK.defaultInstance
                frame = AdvancementType.CHALLENGE
            }
            addCriterion("glgltu_3", Criteria.GLGLTU.createCriterion(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        MinMaxBounds.Ints.atLeast(200)
                    )
                )
            ))
        }.also(consumer::accept)
    }
}
