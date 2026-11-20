// What the synced store knows for the tools (FAH-274; ADR 0075): the User's own Diver, the gradient factor of their
// latest Recording, the planning SAC of their Dives and a Dive within the last 48 hours. The API reads the same values
// from its database; offline this is the only source, so an empty store is said as such instead of as an error.
package dev.divehub.shared.tools

import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.shared.planning.PlanningSacDive
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.SacCylinder
import dev.divehub.shared.planning.SacDive
import dev.divehub.shared.planning.planningSac
import dev.divehub.shared.planning.sacOfDive
import dev.divehub.shared.store.Cylinder
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

private const val SAC_LOOKBACK_DIVES = 100
private const val GF_LOOKBACK_RECORDINGS = 100
private const val RECENT_DIVE_HOURS = 48L
// The sync writes the API's JSON with nulls omitted (SyncMapping), so a null field is absent rather than `null`.
private val SUMMARY = Json { ignoreUnknownKeys = true; explicitNulls = false }

/**
 * The store's values for the tools, read in one transaction so a concurrent sync cannot mix two snapshots. The reads
 * are the server's: the latest 100 Dives for the SAC, the latest 100 Recordings for the GF, and the Diver's Dives for
 * the 48-hour warning; the three bulk queries are filtered here rather than one query per Dive. Every synced Diver is
 * named for the plan's picker, and any of them whose Dives are in the store brings their planning SAC.
 */
fun assembleToolSources(store: DiveStore, nowMs: Long): ToolSources = store.transaction {
    val allDivers = store.divers().sortedByDescending { it.isOwn }
    val dives = store.allDives().filter { it.deletedAt == null }
    val empty = dives.isEmpty()
    val syncedAt = store.syncState().lastFullSyncAt
    val divesByDiver = dives.groupBy { it.diverId }
    val diveIds = dives.mapTo(HashSet()) { it.id }
    val recordings = store.allRecordings()
        .filter { it.deletedAt == null && it.diveId in diveIds }
        .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
    val summaries = recordings.groupBy { it.diveId }.mapValues { (_, rows) -> rows.mapNotNull(::summaryOf) }
    val cylinders = store.allCylinders()
        .filter { it.diveId in diveIds }
        .groupBy { it.diveId }

    fun sacOfDiver(diverId: String): PlanningSacResult {
        val recent = divesByDiver[diverId].orEmpty()
            .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
            .take(SAC_LOOKBACK_DIVES)
        return planningSac(
            recent.map { PlanningSacDive(sacOf(it, summaries[it.id].orEmpty(), cylinders[it.id].orEmpty())) },
        )
    }

    fun recentEndOfDiver(diverId: String): String? = recentDiveEnd(
        divesByDiver[diverId].orEmpty().sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE },
        nowMs,
    )

    val divers = allDivers.map { diver ->
        ToolDiverSource(
            id = diver.id,
            name = diver.name,
            isOwn = diver.isOwn,
            planningSac = divesByDiver[diver.id]?.takeIf { it.isNotEmpty() }?.let { sacOfDiver(diver.id) },
            recentDiveEndedAt = recentEndOfDiver(diver.id),
        )
    }
    val own = allDivers.firstOrNull { it.isOwn }
    if (own == null) return@transaction ToolSources(storeEmpty = empty, syncedAt = syncedAt, divers = divers)

    val ownDives = divesByDiver[own.id].orEmpty()
        .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
    val ownIds = ownDives.mapTo(HashSet()) { it.id }
    val ownRecordings = recordings.filter { it.diveId in ownIds }
    ToolSources(
        ownDiverId = own.id,
        gfHigh = ownRecordings.take(GF_LOOKBACK_RECORDINGS).asSequence().mapNotNull { summaryOf(it)?.gfHigh }.firstOrNull(),
        planningSac = sacOfDiver(own.id),
        recentDiveEndedAt = recentEndOfDiver(own.id),
        storeEmpty = empty,
        syncedAt = syncedAt,
        divers = divers,
    )
}

/** The Dive's own SAC, else what its Cylinders say, else a tank pod's on its Recording (as `diveSacs`). */
private fun sacOf(
    dive: Dive,
    recordings: List<DiveViewRecordingsItemSummary>,
    cylinders: List<Cylinder>,
): Double? {
    dive.sacLitresPerMinute?.let { return it }
    val fromPod = recordings.firstOrNull { it.sacLpm != null }?.sacLpm
    val duration = dive.durationSeconds ?: return fromPod
    val computed = sacOfDive(
        SacDive(duration.toDouble(), dive.avgDepthM),
        cylinders.map { cylinder ->
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
        val end = start + (dive.durationSeconds ?: 0L) * 1000
        if (end <= nowMs && end >= from && end > latest) latest = end
    }
    return if (latest == Long.MIN_VALUE) null else Instant.fromEpochMilliseconds(latest).toString()
}

private fun epochMs(text: String): Long? = try {
    Instant.parse(text).toEpochMilliseconds()
} catch (_: Exception) {
    null
}
