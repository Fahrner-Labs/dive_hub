// What the synced store knows for the tools (FAH-274; ADR 0075): the User's own Diver, the gradient factor of their
// latest Recording, the planning SAC of their Dives and a Dive within the last 48 hours. The API reads the same values
// from its database; offline this is the only source, so an empty store is said as such instead of as an error.
package dev.divehub.shared.tools

import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.shared.planning.PlanningSacDive
import dev.divehub.shared.planning.SacCylinder
import dev.divehub.shared.planning.SacDive
import dev.divehub.shared.planning.planningSac
import dev.divehub.shared.planning.sacOfDive
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

private const val SAC_LOOKBACK_DIVES = 100
private const val RECENT_DIVE_HOURS = 48L
private val SUMMARY = Json { ignoreUnknownKeys = true }

/** The store's values for the tools, read once per screen change; pure enough to test against an in-memory store. */
fun assembleToolSources(store: DiveStore, nowMs: Long): ToolSources {
    val own = store.divers().firstOrNull { it.isOwn }
    val empty = store.activeDiveCount() == 0L
    if (own == null) return ToolSources(storeEmpty = empty)
    val dives = store.allDives()
        .filter { it.diverId == own.id && it.deletedAt == null }
        .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
    val summaries = dives.associate { dive ->
        dive.id to store.recordings(dive.id)
            .filter { it.deletedAt == null }
            .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
            .mapNotNull(::summaryOf)
    }
    return ToolSources(
        ownDiverId = own.id,
        gfHigh = summaries.values.asSequence().flatten().firstOrNull { it.gfHigh != null }?.gfHigh,
        planningSac = planningSac(dives.take(SAC_LOOKBACK_DIVES).map { PlanningSacDive(sacOf(store, it, summaries[it.id].orEmpty())) }),
        recentDiveEndedAt = recentDiveEnd(dives, nowMs),
        storeEmpty = empty,
    )
}

/** The Dive's own SAC, else what its Cylinders say, else a tank pod's on its Recording (as `diveSacs`). */
private fun sacOf(store: DiveStore, dive: Dive, recordings: List<DiveViewRecordingsItemSummary>): Double? {
    dive.sacLitresPerMinute?.let { return it }
    val fromPod = recordings.firstOrNull { it.sacLpm != null }?.sacLpm
    val duration = dive.durationSeconds ?: return fromPod
    val computed = sacOfDive(
        SacDive(duration.toDouble(), dive.avgDepthM),
        store.cylinders(dive.id).map { cylinder ->
            SacCylinder(
                volumeL = cylinder.volumeL,
                startPressureBar = cylinder.startPressureBar,
                endPressureBar = cylinder.endPressureBar,
                gas = if (cylinder.o2 == null || cylinder.he == null) null
                else dev.divehub.shared.planning.GasMix(cylinder.o2, cylinder.he),
            )
        },
    ).sac?.litresPerMinute
    return computed ?: fromPod
}

private fun summaryOf(recording: Recording): DiveViewRecordingsItemSummary? =
    try {
        SUMMARY.decodeFromString(DiveViewRecordingsItemSummary.serializer(), recording.summaryJson)
    } catch (_: Exception) {
        null
    }

/** The end of the Diver's latest Dive within the last 48 hours, if one is in the store. */
private fun recentDiveEnd(dives: List<Dive>, nowMs: Long): String? {
    val from = nowMs - RECENT_DIVE_HOURS * 3_600_000
    var latest = Long.MIN_VALUE
    for (dive in dives) {
        val start = epochMs(dive.startsAt) ?: continue
        val end = start + (dive.durationSeconds ?: 0.0).toLong() * 1000
        if (end <= nowMs && end >= from && end > latest) latest = end
    }
    return if (latest == Long.MIN_VALUE) null else Instant.fromEpochMilliseconds(latest).toString()
}

private fun epochMs(text: String): Long? = try {
    Instant.parse(text).toEpochMilliseconds()
} catch (_: Exception) {
    null
}
