package com.github.p1k0chu.bacup.advancement.generator

import com.github.p1k0chu.bacup.advancement.advancement
import com.github.p1k0chu.bacup.advancement.criteria.Criteria
import com.github.p1k0chu.bacup.advancement.criteria.SingleIntRangeCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.component.DataComponentTypes
import net.minecraft.data.advancement.AdvancementTabGenerator
import net.minecraft.data.advancement.AdvancementTabGenerator.reference
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.registry.RegistryWrapper
import java.util.*
import java.util.function.Consumer

object StatisticsTabGenerator : AdvancementTabGenerator {
    const val TAB_NAME = "statistics"
    const val ROOT = "blazeandcave:statistics/root"

    const val EMERALD_PORTFOLIO = "emerald_portfolio"
    const val THE_STOCK_MARKET = "the_stock_market"
    const val SMALL_BUSINESS = "small_business"
    const val SMALL_INDIE_COMPANY = "small_indie_company"

    const val GLGLTU = "glgltu"
    const val GLGLTU_CULT = "glgltu_cult"
    const val GLGLTU_3 = "glgltu_3"

    override fun accept(wrapperLookup: RegistryWrapper.WrapperLookup, consumer: Consumer<AdvancementEntry>) {
        val emeraldPortfolio = advancement(TAB_NAME, EMERALD_PORTFOLIO) {
            parent(reference(ROOT))
            display {
                icon = Items.EMERALD.defaultStack
            }
            criterion("200", Criteria.TRADED_FOR_EMERALDS.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(NumberRange.IntRange.atLeast(200))
                )
            ))
        }.also(consumer::accept)

        val stockMarket = advancement(TAB_NAME, THE_STOCK_MARKET) {
            parent(emeraldPortfolio)
            display {
                icon = Items.EMERALD.defaultStack.apply {
                    set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                }
                frame = AdvancementFrame.GOAL
            }
            criterion(
                "576", Criteria.TRADED_FOR_EMERALDS.create(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(NumberRange.IntRange.atLeast(576)),
                    )
                )
            )
        }.also(consumer::accept)

        val smallBusiness = advancement(TAB_NAME, SMALL_BUSINESS) {
            parent(stockMarket)
            display {
                icon = Items.EMERALD_BLOCK.defaultStack
                frame = AdvancementFrame.CHALLENGE
            }
            criterion(
                "1000", Criteria.TRADED_FOR_EMERALDS.create(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(NumberRange.IntRange.atLeast(1000)),
                    )
                )
            )
        }.also(consumer::accept)

        advancement(TAB_NAME, SMALL_INDIE_COMPANY) {
            parent(smallBusiness)
            display {
                icon = Items.DEEPSLATE_EMERALD_ORE.defaultStack
                frame =  AdvancementFrame.CHALLENGE
                hidden = true
            }
            criterion(
                "15552", Criteria.TRADED_FOR_EMERALDS.create(
                    SingleIntRangeCriterion.Conditions(
                        Optional.empty(),
                        Optional.of(NumberRange.IntRange.atLeast(15552)),
                    )
                )
            )
        }.also(consumer::accept)

        val glgltu = advancement(TAB_NAME, GLGLTU) {
            parent(reference(ROOT))
            display {
                icon = Items.FEATHER.defaultStack
            }
            criterion("glgltu", Criteria.GLGLTU.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        NumberRange.IntRange.atLeast(1)
                    )
                )
            ))
        }.also(consumer::accept)

        val glgltuCult = advancement(TAB_NAME, GLGLTU_CULT) {
            parent(glgltu)
            display {
                icon = Items.FEATHER.defaultStack
                frame = AdvancementFrame.GOAL
            }
            criterion("glgltu_cult", Criteria.GLGLTU.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        NumberRange.IntRange.atLeast(50)
                    )
                )
            ))
        }.also(consumer::accept)

        advancement(TAB_NAME, GLGLTU_3) {
            parent(glgltuCult)
            display {
                icon = Items.WRITABLE_BOOK.defaultStack
                frame = AdvancementFrame.CHALLENGE
            }
            criterion("glgltu_3", Criteria.GLGLTU.create(
                SingleIntRangeCriterion.Conditions(
                    Optional.empty(),
                    Optional.of(
                        NumberRange.IntRange.atLeast(200)
                    )
                )
            ))
        }.also(consumer::accept)
    }
}
