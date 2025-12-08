package com.github.p1k0chu.bacup

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.server.MinecraftServer
import net.minecraft.core.UUIDUtil
import net.minecraft.world.level.saveddata.SavedData
import net.minecraft.world.level.saveddata.SavedDataType
import net.minecraft.world.level.Level
import java.util.*

class BacupPersistentState(
    players: Map<UUID, PlayerData>? = null
) : SavedData() {
    // java code may give immutable map in constructor
    // even if the parameter type is MutableMap
    val players: MutableMap<UUID, PlayerData> = players?.toMutableMap() ?: mutableMapOf()

    companion object {
        val CODEC: Codec<BacupPersistentState> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.unboundedMap(UUIDUtil.AUTHLIB_CODEC, PlayerData.CODEC)
                    .fieldOf("players")
                    .forGetter(BacupPersistentState::players)
            ).apply(instance, ::BacupPersistentState)
        }

        @JvmStatic
        fun getType(): SavedDataType<BacupPersistentState> {
            return SavedDataType(
                Main.id("persistent_state"),
                { BacupPersistentState() },
                { CODEC },
                null
            )
        }

        @JvmStatic
        fun getState(server: MinecraftServer): BacupPersistentState {
            val manager = server.getLevel(Level.OVERWORLD)!!.dataStorage
            return manager.computeIfAbsent(getType())
        }

        @JvmStatic
        fun getPlayerState(player: LivingEntity): PlayerData {
            val state = getState(player.level().server!!)

            state.setDirty()

            return state.players.getOrPut(player.uuid) { PlayerData() }
        }
    }
}

/**
 * @param emeraldsObtained emeralds obtained through trade with merchants
 */
class PlayerData(
    petsTamed: Map<EntityType<*>, Int>? = null,
    var emeraldsObtained: Int = 0,
    var glgltuCounter: Int = 0,
) {
    val petsTamed: MutableMap<EntityType<*>, Int> = petsTamed?.toMutableMap() ?: mutableMapOf()

    companion object {
        val CODEC: Codec<PlayerData> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.unboundedMap(EntityType.CODEC, Codec.INT)
                    .fieldOf("petsTamed")
                    .forGetter(PlayerData::petsTamed),
                Codec.INT.optionalFieldOf("emeralds_obtained", 0)
                    .forGetter(PlayerData::emeraldsObtained),
                Codec.INT.optionalFieldOf("glgltu_counter", 0)
                    .forGetter(PlayerData::glgltuCounter)
            ).apply(instance, ::PlayerData)
        }
    }
}
