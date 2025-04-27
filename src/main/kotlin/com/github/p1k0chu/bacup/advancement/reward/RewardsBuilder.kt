package com.github.p1k0chu.bacup.advancement.reward

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.data.function.MCFunction
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import java.util.function.Consumer

class RewardsBuilder(
    val consumer: Consumer<MCFunction>
) {
    fun tab(tab: String, block: RewardTabBuilder.() -> Unit) {
        RewardTabBuilder(tab).block()
    }

    inner class RewardTabBuilder(private val tab: String) {
        fun advancement(id: String, block: RewardAdvancementBuilder.() -> Unit = {}) {
            RewardAdvancementBuilder(id).apply(block).build()
        }

        inner class RewardAdvancementBuilder(val name: String) {
            private val itemRewards: MutableList<ItemStack> = mutableListOf()
            var trophy: ItemStack? = null
            var type: AdvancementType = AdvancementType.TASK

            fun item(item: ItemStack) {
                itemRewards.add(item)
            }

            fun build() {
                // the message function
                consumer.accept(MCFunction(
                    Identifier.of(Main.MOD_ID, "msg/$tab/$name"),
                    messageGen(
                        tab,
                        "${Main.MOD_ID}.advancement.$tab.$name.title",
                        "${Main.MOD_ID}.advancement.$tab.$name.desc",
                        type
                    )
                ))

                // item reward function
                consumer.accept(MCFunction(
                    Identifier.of(Main.MOD_ID, "reward/$tab/$name"),
                    itemRewards.joinToString(separator = "\n") { giveGen(it) }
                ))

                // main reward function
                consumer.accept(MCFunction(
                    Identifier.of(Main.MOD_ID, "$tab/$name"),
                    mainRewardFunctionGen(Identifier.of(Main.MOD_ID, "$tab/$name"))
                ))

                // TODO: trophy
            }
        }
    }
}

fun Consumer<MCFunction>.builder(block: RewardsBuilder.() -> Unit) {
    return RewardsBuilder(this).block()
}

fun giveGen(item: ItemStack): String {
    return """
        give @s ${item.registryEntry.idAsString} ${item.count}
        tellraw @s {"color":"green","text":" +${item.count} ","extra":[{"translate":"${item.item.translationKey}"}]}
    """.trimIndent()
}

const val TAB_COLOR = "gray"

val bac_teams = listOf(
    "white",
    "yellow",
    "purple",
    "red",
    "aqua",
    "green",
    "blue",
    "dark_gray",
    "gray",
    "gold",
    "dark_purple",
    "dark_red",
    "dark_aqua",
    "dark_green",
    "dark_blue",
    "black"
)

fun messageGen(tab: String, title: String, description: String, type: AdvancementType): String {
    return """
        tellraw @a {"translate":"${type.message}","with":[{"selector":"@s"},{"color":"${type.titleColor}","text":"["},{"color":"${type.titleColor}","translate":"$title","hover_event":{"action":"show_text","value":{"color":"${type.titleColor}","translate":"$title","extra":[{"text":"\n"},{"color":"${type.descriptionColor}","translate":"$description"},{"text":"\n\n"},{"color":"$TAB_COLOR","italic":true,"translate":"%1${'$'}s tab","with":[{"translate":"$tab"}]}]}}},{"color":"${type.titleColor}","text":"]"}]}
    """.trimIndent()
}

fun mainRewardFunctionGen(advancementId: Identifier): String {
    val namespace: String = advancementId.namespace
    val path: String = advancementId.path

    val functionBody = StringBuilder()
    functionBody.append("""
        execute if score goal bac_settings matches 1 run function $namespace:msg/$path
        execute if score goal bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:msg/$path
        
        execute if score reward bac_settings matches 1 run function $namespace:reward/$path
        execute if score reward bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:reward/$path
        
        execute if score exp bac_settings matches 1 run function $namespace:exp/$path
        execute if score exp bac_settings matches -1 unless score $advancementId bac_obtained matches 1.. run function $namespace:exp/$path
    """.trimIndent())

    bac_teams.forEach { team ->
        functionBody.append("""
            execute if score goal bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:msg/$path
            execute if score reward bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:reward/$path
            execute if score exp bac_settings matches -2 if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function $namespace:exp/$path
        """.trimIndent())
    }

    functionBody.append("""
        function bacap_rewards:score_add
        execute unless score $advancementId bac_obtained matches 1.. run function bacap_rewards:first_score_add
        scoreboard players add $advancementId bac_obtained 1
        execute if score coop bac_settings matches 1 run advancement grant @a only $advancementId
    """.trimIndent())

    bac_teams.forEach { team ->
        functionBody.append("""
            execute if entity @s[team=bac_team_$team] unless score $advancementId bac_obtained_$team matches 1.. run function bacap_rewards:first_team_score_add
            execute if entity @s[team=bac_team_$team] run scoreboard players add $advancementId bac_obtained_$team 1
            execute if score coop bac_settings matches 2 if entity @s[team=bac_team_$team] run advancement grant @a[team=bac_team_$team] only $advancementId
        """.trimIndent())
    }

    functionBody.append("function #bacap_fanpacks:adventure/a_chiptune_relic")

    return functionBody.toString()
}
