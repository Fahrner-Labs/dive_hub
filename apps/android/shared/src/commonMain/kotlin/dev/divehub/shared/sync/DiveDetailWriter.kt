package dev.divehub.shared.sync

import dev.divehub.api.models.DiveView
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncCheckpoint

/**
 * One Dive's detail into the store, inside the caller's transaction. The detail is authoritative: Recordings and
 * Cylinders it no longer lists are removed with their series, so a stale Recording cannot make the profiles phase
 * fail on a 404 forever.
 *
 * The sync's detail pass and the write queue's replay share this: a write's 200 answer is stored exactly as a read's
 * would be (ADR 0084 D3 step 2).
 */
internal fun DiveStore.writeDiveDetail(detail: DiveView) {
    val diveId = detail.id
    val existing = diveIncludingDeleted(diveId)
    upsertDive(detail.toStore(existing).copy(listJson = detail.toListRow(existing?.listJson)))
    val kept = detail.recordings.mapTo(mutableSetOf()) { it.id }
    allRecordingsForDive(diveId).filter { it.id !in kept }.forEach { recording ->
        deleteSeries(recording.id)
        deleteRecording(recording.id)
    }
    detail.recordings.forEach { upsertRecording(it.toStore(diveId)) }
    deleteCylinders(diveId)
    detail.cylinders.forEachIndexed { position, cylinder ->
        upsertCylinder(cylinder.toStore(diveId, position.toLong()))
    }
}

/** A Dive's profile checkpoint back to pending; the next profiles pass refetches a replay's changed Recordings. */
internal fun queuedProfileCheckpoint(diveId: String, now: String): SyncCheckpoint = SyncCheckpoint(
    entity = "profiles:$diveId",
    phase = "profiles",
    cursor = null,
    pageOffset = 0,
    done = false,
    updatedAt = now,
)
