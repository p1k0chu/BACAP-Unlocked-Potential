package com.github.p1k0chu.bacup;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public final class BacapupDataAttachments {
    private BacapupDataAttachments() {
    }

    public static final AttachmentType<Integer> EMERALDS_OBTAINED = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(Main.MOD_ID, "emeralds_obtained"),
            builder -> builder
                    .initializer(() -> 0)
                    .persistent(Codec.INT)
                    .copyOnDeath()
    );
    public static final AttachmentType<Integer> GLGLTU_COUNTER = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(Main.MOD_ID, "glgltu_counter"),
            builder -> builder
                    .initializer(() -> 0)
                    .persistent(Codec.INT)
                    .copyOnDeath()
    );
    public static final AttachmentType<BacapupPetsTamed> PETS_TAMED = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(Main.MOD_ID, "pets_tamed"),
            builder -> builder
                    .initializer(BacapupPetsTamed::new)
                    .persistent(BacapupPetsTamed.CODEC)
                    .copyOnDeath()
    );

    public static void doNothing() {
    }
}
