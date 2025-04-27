package com.github.p1k0chu.bacup.data.function

import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.util.function.Consumer

fun interface FunctionGenerator {
    fun accept(wrapperLookup: WrapperLookup, consumer: Consumer<MCFunction>)
}