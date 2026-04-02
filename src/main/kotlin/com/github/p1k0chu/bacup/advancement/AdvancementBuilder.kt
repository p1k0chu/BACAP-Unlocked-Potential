package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.Main
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.DisplayInfo
import net.minecraft.core.ClientAsset
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.item.ItemStackTemplate
import java.util.*
import java.util.function.Consumer

class AdvancementBuilder(val tab: String, val name: String) : Advancement.Builder() {
    private val itemRewards: MutableList<ItemStackTemplate> = mutableListOf()
    var trophy: ItemStackTemplate? = null
    var title: String? = null
    var description: String? = null
    var type: AdvancementType = AdvancementType.TASK
    var exp: Int? = null

    fun itemReward(itemStack: ItemStackTemplate) {
        itemRewards.add(itemStack)
    }

    inline fun Advancement.Builder.display(block: AdvancementDisplayBuilder.() -> Unit) {
        this.display(AdvancementDisplayBuilder().apply(block).build())
    }

    inner class AdvancementDisplayBuilder {
        lateinit var icon: ItemStackTemplate
        var background: ClientAsset? = null
        var showToast: Boolean = true
        var announceToChat: Boolean = true
        var hidden: Boolean = false

        fun build(): DisplayInfo {
            return DisplayInfo(
                icon,
                Component.translatable(title!!),
                Component.translatable(description!!).withColor(type.descriptionColor.value),
                Optional.ofNullable(background?.id()?.let(ClientAsset::ResourceTexture)),
                AdvancementType.getFrame(type),
                showToast,
                announceToChat,
                hidden
            )
        }
    }

    fun buildFunctions(consumer: Consumer<MCFunction>) {
        // the message function
        consumer.accept(
            MCFunction(
                Identifier.fromNamespaceAndPath(Main.MOD_ID, "msg/$tab/$name"), messageGen(
                    tab,
                    title!!,
                    description!!,
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
                Identifier.fromNamespaceAndPath(Main.MOD_ID, "trophy/$tab/$name"), trophyGen(title, trophy)
            )
        )
    }
}

inline fun advancement(
    consumer: AdvancementConsumer,
    tab: String,
    name: String,
    block: AdvancementBuilder.() -> Unit
): AdvancementHolder {
    val builder = AdvancementBuilder(tab, name)
    builder.apply(block)
    builder.rewards(AdvancementRewards.Builder.function(Identifier.parse(id(tab, name))))
    val adv = builder.build(Identifier.parse(id(tab, name)))
    consumer.advancement(adv)
    builder.buildFunctions(consumer::function)
    return adv
}

