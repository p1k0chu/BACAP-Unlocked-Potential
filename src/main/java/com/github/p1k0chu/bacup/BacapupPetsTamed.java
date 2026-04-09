package com.github.p1k0chu.bacup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;

import java.util.Map;
import java.util.function.*;

public record BacapupPetsTamed(int cat, int wolf) {
    public static final Codec<BacapupPetsTamed> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    Codec.INT.optionalFieldOf("cat", 0)
                            .forGetter(BacapupPetsTamed::cat),
                    Codec.INT.optionalFieldOf("wolf", 0)
                            .forGetter(BacapupPetsTamed::wolf)
            ).apply(builder, BacapupPetsTamed::new)
    );

    public BacapupPetsTamed() {
        this(0, 0);
    }

    public BacapupPetsTamed withCat(int cat) {
        return new BacapupPetsTamed(cat, this.wolf);
    }

    public BacapupPetsTamed withWolf(int wolf) {
        return new BacapupPetsTamed(this.cat, wolf);
    }

    public void forEach(BiConsumer<EntityType<?>, Integer> consumer) {
        if (cat > 0) {
            consumer.accept(EntityType.CAT, cat);
        }
        if (wolf > 0) {
            consumer.accept(EntityType.WOLF, wolf);
        }
    }

    public enum TrackedEntityType {
        CAT(BacapupPetsTamed::cat, BacapupPetsTamed::withCat),
        WOLF(BacapupPetsTamed::wolf, BacapupPetsTamed::withWolf);

        public static final Map<EntityType<?>, TrackedEntityType> BY_TYPE_MAP = Map.of(
                EntityType.CAT, CAT,
                EntityType.WOLF, WOLF
        );

        public final Function<BacapupPetsTamed, Integer> getter;
        public final BiFunction<BacapupPetsTamed, Integer, BacapupPetsTamed> setter;

        TrackedEntityType(Function<BacapupPetsTamed, Integer> getter, BiFunction<BacapupPetsTamed, Integer, BacapupPetsTamed> setter) {
            this.getter = getter;
            this.setter = setter;
        }

        public BacapupPetsTamed apply(BacapupPetsTamed petsTamed, UnaryOperator<Integer> operator) {
            return setter.apply(petsTamed, operator.apply(getter.apply(petsTamed)));
        }
    }
}
