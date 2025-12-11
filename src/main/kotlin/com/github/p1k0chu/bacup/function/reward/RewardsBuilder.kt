package com.github.p1k0chu.bacup.function.reward

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.function.MCFunction
import com.github.p1k0chu.bacup.function.reward.BacConstants.TAB_COLOR
import com.github.p1k0chu.bacup.function.reward.BacConstants.bac_teams
import com.github.p1k0chu.bacup.function.reward.BacConstants.tab_titles
import com.github.p1k0chu.bacup.language.title
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.CustomModelData
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.Component
import net.minecraft.util.CommonColors
import net.minecraft.resources.Identifier
import java.util.function.Consumer

class RewardsBuilder(
    val consumer: Consumer<MCFunction>
) {
    inline fun tab(tab: String, block: RewardTabBuilder.() -> Unit) {
        RewardTabBuilder(tab).block()
    }

    inner class RewardTabBuilder(private val tab: String) {
        inline fun advancement(id: String, block: RewardAdvancementBuilder.() -> Unit = {}) {
            RewardAdvancementBuilder(id).apply(block).build()
        }

        inner class RewardAdvancementBuilder(private val name: String) {
            private val itemRewards: MutableList<ItemStack> = mutableListOf()
            var trophy: ItemStack? = null
            var type: AdvancementType = AdvancementType.TASK
            var exp: Int? = null

            fun item(item: ItemStack) {
                itemRewards.add(item)
            }

            fun build() {
                // the message function
                consumer.accept(
                    MCFunction(
                        Identifier.fromNamespaceAndPath(Main.MOD_ID, "msg/$tab/$name"), messageGen(
                            tab,
                            "${Main.MOD_ID}.advancement.$tab.$name.title",
                            "${Main.MOD_ID}.advancement.$tab.$name.desc",
                            type
                        )
                    )
                )

                // item reward function
                consumer.accept(
                    MCFunction(
                        Identifier.fromNamespaceAndPath(Main.MOD_ID, "reward/$tab/$name"),
                        itemRewards.joinToString(separator = "\n") { giveGen(it) })
                )

                // main reward function
                consumer.accept(
                    MCFunction(
                        Identifier.fromNamespaceAndPath(Main.MOD_ID, "$tab/$name"),
                        mainRewardFunctionGen(Identifier.fromNamespaceAndPath(Main.MOD_ID, "$tab/$name"))
                    )
                )

                consumer.accept(
                    MCFunction(
                        Identifier.fromNamespaceAndPath(Main.MOD_ID, "exp/$tab/$name"), expGen(exp)
                    )
                )

                consumer.accept(
                    MCFunction(
                        Identifier.fromNamespaceAndPath(Main.MOD_ID, "trophy/$tab/$name"), trophyGen(tab, name, trophy)
                    )
                )
            }
        }
    }
}

fun rewardsBuilder(consumer: Consumer<MCFunction>, block: RewardsBuilder.() -> Unit) {
    return RewardsBuilder(consumer).block()
}

fun giveGen(item: ItemStack): String {
    return """
        give @s ${item.itemHolder.registeredName} ${item.count}
        tellraw @s {"color":"green","text":" +${item.count} ","extra":[{"translate":"${item.item.descriptionId}"}]}
    """.trimIndent()
}


fun messageGen(tab: String, title: String, description: String, type: AdvancementType): String {
    return """
        tellraw @a {"translate":"${type.message}","with":[{"selector":"@s"},{"color":"${type.titleColor}","text":"["},{"color":"${type.titleColor}","translate":"$title","hover_event":{"action":"show_text","value":{"color":"${type.titleColor}","translate":"$title","extra":[{"text":"\n"},{"color":"${type.descriptionColor}","translate":"$description"},{"text":"\n\n"},{"color":"$TAB_COLOR","italic":true,"translate":"%1${'$'}s tab","with":[{"translate":"${tab_titles[tab]}"}]}]}}},{"color":"${type.titleColor}","text":"]"}]}
    """.trimIndent()
}

fun expGen(amount: Int?): String {
    if (amount == null) return ""

    return """
        xp add @s $amount
        tellraw @s {"color":"blue","text":" +$amount ","extra":[{"translate":"Experience"}]}
    """.trimIndent()
}

// hell.
fun mainRewardFunctionGen(advancementId: Identifier): String {
    val namespace: String = advancementId.namespace
    val path: String = advancementId.path

    val functionBody = StringBuilder()
    functionBody.appendLine(
        """
        execute if score goal bac_settings matches 1 run function $namespace:msg/$path
        execute if score goal bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:msg/$path
        
        execute if score reward bac_settings matches 1 run function $namespace:reward/$path
        execute if score reward bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:reward/$path
        
        execute if score exp bac_settings matches 1 run function $namespace:exp/$path
        execute if score exp bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:exp/$path

        execute if score trophy bac_settings matches 1 run function $namespace:trophy/$path
        execute if score trophy bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:trophy/$path
    """.trimIndent()
    )

    bac_teams.forEach { team ->
        functionBody.appendLine(
            """
            execute if score goal bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:msg/$path
            execute if score reward bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:reward/$path
            execute if score exp bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:exp/$path
            execute if score trophy bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:trophy/$path
        """.trimIndent()
        )
    }

    functionBody.appendLine(
        """
        function bacap_rewards:score_add
        execute unless score $advancementId bac_obtained matches 1.. run function bacap_rewards:first_score_add
        scoreboard players add $advancementId bac_obtained 1
        execute if score coop bac_settings matches 1 run advancement grant @a only $advancementId
    """.trimIndent()
    )

    bac_teams.forEach { team ->
        functionBody.appendLine(
            """
            execute if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function bacap_rewards:first_team_score_add
            execute if entity @s[team=bac_team_$team] run scoreboard players add $advancementId bac_obtained_$team 1
            execute if score coop bac_settings matches 2 if entity @s[team=bac_team_$team] run advancement grant @a[team=bac_team_$team] only $advancementId
        """.trimIndent()
        )
    }

    functionBody.append("function #bacapup_fanpacks:$path")

    return functionBody.toString()
}

/**
 * Returns a minecraft function for a trophy
 */
fun trophyGen(tab: String, name: String, item: ItemStack?): String {
    if (item == null) return ""

    // setup useful trophy data
    item.set(DataComponents.CUSTOM_MODEL_DATA, CustomModelData(listOf(131f), listOf(), listOf(), listOf()))
    item.set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay(false, ReferenceSortedSets.emptySet()))

    CompoundTag().let { customData ->
        customData.putInt("Trophy", 1)
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(customData))
    }

    item.customName?.toFlatList(
        Style.EMPTY
            .withBold(true)
            .withItalic(false)
    )
        ?.firstOrNull()
        ?.let { customName ->
            item.set(DataComponents.CUSTOM_NAME, customName)
        }

    // change lore formatting and
    // add extra lines to lore
    val lore = item.get(DataComponents.LORE)
    val lines: List<Component>? = lore?.lines?.flatMap { line ->
        line.toFlatList(
            Style.EMPTY.withBold(false).withItalic(true).withColor(item.customName?.style?.color)
        )
    }

    item.set(
        DataComponents.LORE, ItemLore(lines ?: listOf()).withLineAdded(Component.empty()).withLineAdded(
            Component.nullToEmpty("Awarded for achieving").toFlatList(
                Style.EMPTY.withColor(CommonColors.GRAY)
            ).first()
        ).withLineAdded(
            title(tab, name)
        )
    )

    val functionBody = StringBuilder()
    functionBody.append("give @s ${item.itemHolder.registeredName}[")

    item.components.mapNotNull { component ->
        if (!item.hasNonDefault(component.type)) return@mapNotNull null

        "${component.type}=${component.encodeValue(NbtOps.INSTANCE).orThrow}"
    }.joinTo(functionBody, separator = ",")

    functionBody.append(
        """
        ] 1
        tellraw @s {"color": "${item.customName?.style?.color ?: "white"}", "text": " +${item.count} ", "extra": [{"translate": "${item.customName?.string ?: item.item.descriptionId}"}]}
        """.trimIndent()
    )

    return functionBody.toString()
}
