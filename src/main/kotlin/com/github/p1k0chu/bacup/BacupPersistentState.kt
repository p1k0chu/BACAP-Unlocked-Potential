package com.github.p1k0chu.bacup

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Uuids
import net.minecraft.world.PersistentState
import net.minecraft.world.PersistentStateType
import net.minecraft.world.World
import java.util.*

class BacupPersistentState(
    players: Map<UUID, PlayerData>? = null
) : PersistentState() {
    // java code may give immutable map in constructor
    // even if the parameter type is MutableMap
    val players: MutableMap<UUID, PlayerData> = players?.toMutableMap() ?: mutableMapOf()

    companion object {
        val CODEC = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.unboundedMap(Uuids.CODEC, PlayerData.CODEC)
                    .fieldOf("players")
                    .forGetter(BacupPersistentState::players)
            ).apply(instance, ::BacupPersistentState)
        }

        @JvmStatic
        fun getType(): PersistentStateType<BacupPersistentState> {
            return PersistentStateType(
                Main.id("persistent_state"),
                { BacupPersistentState() },
                { CODEC },
                null
            )
        }

        @JvmStatic
        fun getState(server: MinecraftServer): BacupPersistentState {
            val manager = server.getWorld(World.OVERWORLD)!!.persistentStateManager
            return manager.getOrCreate(getType())
        }

        @JvmStatic
        fun getPlayerState(player: LivingEntity): PlayerData {
            val state = getState(player.server!!)

            state.markDirty()

            return state.players.getOrPut(player.uuid) { PlayerData() }
        }
    }
}

class PlayerData(
    petsTamed: Map<EntityType<*>, Int>? = null
) {
    val petsTamed: MutableMap<EntityType<*>, Int> = petsTamed?.toMutableMap() ?: mutableMapOf()

    companion object {
        val CODEC = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.unboundedMap(EntityType.CODEC, Codec.INT)
                    .fieldOf("petsTamed")
                    .forGetter(PlayerData::petsTamed)
            ).apply(instance, ::PlayerData)
        }
    }
}