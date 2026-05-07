package io.github.p1k0chu.bacapup.advancement

import net.minecraft.advancements.AdvancementHolder
import net.minecraft.core.HolderLookup

interface AdvancementSubProvider {
    fun generate(provider: HolderLookup.Provider, consumer: AdvancementConsumer)
}

interface AdvancementConsumer {
    fun advancement(advancementHolder: AdvancementHolder)
    fun function(function: MCFunction)
}
