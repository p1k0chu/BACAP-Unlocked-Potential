package com.github.p1k0chu.bacup.advancement

import com.github.p1k0chu.bacup.Main
import com.github.p1k0chu.bacup.constants.BacConstants
import net.minecraft.resources.Identifier
import java.util.function.Consumer

fun makeCoopSyncFunction(ids: Iterable<Identifier>): MCFunction {
    val s = StringBuilder()
    ids.forEach {
        s.append("execute if score $it bac_obtained matches 1.. run advancement grant @a only $it\n")
    }

    return MCFunction(Identifier.fromNamespaceAndPath(Main.MOD_ID, "config/coop_update"), s.toString())
}

fun makeCoopSyncForTeamFunction(team: String, ids: Iterable<Identifier>): MCFunction {
    val s = StringBuilder()
    ids.forEach {
        s.append("execute if score $it bac_obtained_$team matches 1.. run advancement grant @a[team=bac_team_$team] only $it\n")
    }

    return MCFunction(Identifier.fromNamespaceAndPath(Main.MOD_ID, "config/coop_update_team_$team"), s.toString())
}

fun makeAllCoopSyncFunctions(
    functionConsumer: Consumer<MCFunction>,
    advIds: Iterable<Identifier>
) {
    functionConsumer.accept(makeCoopSyncFunction(advIds))
    BacConstants.bac_teams.forEach { team ->
        functionConsumer.accept(makeCoopSyncForTeamFunction(team, advIds))
    }
}
