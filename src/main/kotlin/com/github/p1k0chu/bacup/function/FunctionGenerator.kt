package com.github.p1k0chu.bacup.function

import net.minecraft.core.HolderLookup.Provider
import java.util.function.Consumer

fun interface FunctionGenerator {
    fun accept(wrapperLookup: Provider, consumer: Consumer<MCFunction>)
}