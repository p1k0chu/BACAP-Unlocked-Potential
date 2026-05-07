package io.github.p1k0chu.bacup;

import org.junit.jupiter.api.Test;
import org.spongepowered.asm.mixin.MixinEnvironment;

public class AllMixinsApplyTest {
    @Test
    void testAllMixinsApply() {
        MixinEnvironment.getCurrentEnvironment().audit();
    }
}
