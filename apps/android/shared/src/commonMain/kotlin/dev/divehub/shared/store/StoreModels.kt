package dev.divehub.shared.store

/**
 * One row of the offline store's `dive` table: the columns the logbook list and the detail screen read, plus the
 * server's identity/version/tombstone fields the later sync and change feed need (ADR 0075).
 *
 * Timestamps are the API's ISO-8601 strings; JSON columns are the API's JSON payloads unchanged, so a client parses
 * them with the generated API models instead of a second hand-written mapping.
 */
data class Dive(
    val id: String,
    val diverId: String,
    val version: Long? = null,
    val deletedAt: String? = null,
    val startsAt: String,
    val utcOffsetSeconds: Long? = null,
    val utcOffsetSource: String? = null,
    val durationSeconds: Long? = null,
    val maxDepthM: Double? = null,
    val avgDepthM: Double? = null,
    val siteId: String? = null,
    val waterType: String? = null,
    val fromProvider: String? = null,
    val notes: String? = null,
    val suitJson: String? = null,
    val weightingFeedbackJson: String? = null,
    val leadJson: String? = null,
    val leadTotalKg: Double? = null,
    val sacLitresPerMinute: Double? = null,
    val sacBarPerMinute: Double? = null,
    val sacMissing: String? = null,
    val number: Long? = null,
    val findingsCount: Long? = null,
    val summaryJson: String? = null,
    /** The API's own list row (`GET /api/dives`), so the logbook screen draws exactly what the server sent. */
    val listJson: String? = null,
    /** The API's own Dive view (`GET /api/dives/{id}`), so the dive page draws exactly what the server sent. */
    val detailJson: String? = null,
)

data class Recording(
    val id: String,
    val diveId: String,
    val isPrimary: Boolean,
    val deviceJson: String? = null,
    val startsAt: String,
    val durationSeconds: Long,
    val maxDepthM: Double? = null,
    val parser: String,
    val partOf: String? = null,
    val deletedAt: String? = null,
    val summaryJson: String = "{}",
    val channelsJson: String = "[]",
)

/** One channel's samples, as the API sent them: offsets in milliseconds and integer values, both JSON arrays. */
data class RecordingSeries(
    val recordingId: String,
    val channel: String,
    val offsetsMsJson: String,
    val valuesJson: String,
    val fetchedAt: String,
)

data class Cylinder(
    val diveId: String,
    val position: Long,
    val volumeL: Double? = null,
    val material: String? = null,
    val o2: Double? = null,
    val he: Double? = null,
    val startPressureBar: Double? = null,
    val endPressureBar: Double? = null,
    val fromPod: Boolean = false,
    val seriesJson: String? = null,
)

data class DiveSite(
    val id: String,
    val version: Long? = null,
    val name: String,
    val waterType: String? = null,
    val positionJson: String? = null,
    val countryCode: String? = null,
    val mergedInto: String? = null,
    val deletedAt: String? = null,
    val payloadJson: String = "{}",
)

data class Diver(
    val id: String,
    val name: String,
    val isOwn: Boolean,
    val external: Boolean,
    val apneaCounts: Boolean,
    val divesBefore: Long,
    val mergedInto: String? = null,
    val deletedAt: String? = null,
    val payloadJson: String = "{}",
)

data class BodyWeight(
    val diverId: String,
    val date: String,
    val kg: Double,
)

/** Resume state of one sync phase (entity + phase), advanced inside the phase's write transaction. */
data class SyncCheckpoint(
    val entity: String,
    val phase: String,
    val cursor: String? = null,
    val pageOffset: Long? = null,
    val done: Boolean = false,
    val updatedAt: String,
)

/** The singleton sync state the staleness label reads. */
data class SyncState(
    val lastFullSyncAt: String? = null,
    val lastHintAt: String? = null,
    val schemaVersion: Long,
    /** The last list page's per-filter counts, JSON as `DiveListViewCounts`; null before the first list sync. */
    val countsJson: String? = null,
)
