package com.github.p1k0chu.bacup.advancement

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.core.HolderLookup
import net.minecraft.resources.Identifier

interface AdvancementGenerator {
    fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer)
}

interface AdvancementConsumer {
    fun advancement(advancementHolder: AdvancementHolder)
    fun function(function: MCFunction)
}
