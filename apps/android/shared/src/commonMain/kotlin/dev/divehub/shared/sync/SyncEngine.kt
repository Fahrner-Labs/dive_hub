package dev.divehub.shared.sync

import dev.divehub.api.models.ApiDiveSitesGet200Response
import dev.divehub.api.models.ApiDiverBodyWeightsGet200ResponseItem
import dev.divehub.api.models.DeletedDivesPage
import dev.divehub.api.models.DiveDetailsListView
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiverView
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.SiteView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import dev.divehub.shared.store.SyncCheckpoint
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.encodeURLParameter
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.Clock

/** The sync phases in their strict order (FAH-265 plan §3); profiles is the background, cancellable one. */
enum class SyncPhase { Meta, Details, Profiles }

/** What one [SyncEngine] call did. */
sealed interface SyncResult {
    /** The work asked for is complete and the store is the logbook. */
    data object Complete : SyncResult

    /** A 401 ended the session: the engine stops and cached data stays readable (client contract §2). */
    data object SessionEnded : SyncResult

    /** A retryable failure outlasted the retry: the failed phase keeps its checkpoint for the next trigger. */
    data class Stopped(val phase: SyncPhase, val error: Throwable) : SyncResult
}

/**
 * The per-phase state the UI reads for its sync line and staleness label: how much of each phase is in the store.
 *
 * `profilesDone` and `divesAwaitingProfiles` are derived from the per-Dive profile checkpoints, so a Dive whose detail
 * changed is counted pending again until phase 3 has refetched its samples.
 */
data class SyncProgress(
    val lastFullSyncAt: String?,
    val lastHintAt: String?,
    val metaDone: Boolean,
    val detailsDone: Boolean,
    val profilesDone: Boolean,
    val storedDivers: Long,
    val storedBodyWeights: Long,
    val storedDiveSites: Long,
    val storedDives: Long,
    val detailedDives: Long,
    val divesAwaitingProfiles: Long,
) {
    companion object {
        /** Nothing is in the store yet; the flow's value before the first phase writes anything. */
        val EMPTY = SyncProgress(
            lastFullSyncAt = null,
            lastHintAt = null,
            metaDone = false,
            detailsDone = false,
            profilesDone = false,
            storedDivers = 0,
            storedBodyWeights = 0,
            storedDiveSites = 0,
            storedDives = 0,
            detailedDives = 0,
            divesAwaitingProfiles = 0,
        )
    }
}

/**
 * The checkpointed sync loop (FAH-265 plan §3–§4): the initial phases 1–2, the background profiles phase, and the
 * incremental refresh (ADR 0066). Every page is fetched, written and checkpointed in one store transaction, so an
 * interruption resumes the exact phase and offset and a crash never double-writes a page.
 *
 * Phase 1 (meta) makes the logbook list render offline; phase 2 (details) makes the dive page render offline; phase 3
 * fetches each Recording's sample series at the explicit 20,000-point cap the server allows (verified in FAH-269).
 * A refresh re-runs phase 1, fetches details for new Dives and writes deletion tombstones; hints narrow that to one Dive
 * or site. The engine has no Android imports: the app's foreground poll and WorkManager trigger arrive behind plain
 * `suspend` calls and [PeriodicSyncScheduler].
 */
class SyncEngine(
    private val api: ApiClient,
    private val store: DiveStore,
    private val clock: () -> String = { Clock.System.now().toString() },
    private val backoff: suspend (attempt: Int) -> Unit = { delay(RETRY_BACKOFF_MS * it) },
) {
    private val lock = Mutex()
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    /**
     * Called with the store's new counts after a phase advances, so the app can show determinate progress. The
     * callback runs on the sync coroutine, after the write's transaction, and must not suspend.
     */
    var onProgress: ((SyncProgress) -> Unit)? = null

    /**
     * Run or resume the initial phases 1–2. A completed sync is a no-op except for backfilling a v1 store's missing API
     * payloads; [refresh] owns the later triggers.
     */
    suspend fun sync(): SyncResult = lock.withLock {
        emitProgress()
        if (store.syncState().lastFullSyncAt != null) return@withLock backfillPayloadsIfMissing()
        var phase = SyncPhase.Meta
        return@withLock try {
            syncMeta(now = clock())
            emitProgress()
            phase = SyncPhase.Details
            syncDetails(now = clock())
            emitProgress()
            markFullSync(now = clock())
            emitProgress()
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(phase, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(phase, error)
        }
    }

    /**
     * A store migrated from schema v1 has no list or detail payload for its rows. Fetch them once through the normal
     * refresh, so the D4 screens read exactly what the API sends; a normal store returns at once.
     */
    private suspend fun backfillPayloadsIfMissing(): SyncResult {
        val missing = store.allDives().any {
            it.deletedAt == null && (it.listJson == null || it.detailJson == null)
        }
        if (!missing) return SyncResult.Complete
        return try {
            refreshInternal(now = clock())
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(SyncPhase.Meta, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(SyncPhase.Meta, error)
        }
    }

    /**
     * Phase 3: each active Dive's Recordings, lowest priority and cancellable. A Dive is done only after all its
     * recordings are stored, so the UI can mark profiles "not yet synced" per Dive and the next run resumes there.
     */
    suspend fun syncProfiles(): SyncResult = lock.withLock {
        emitProgress()
        try {
            syncProfilesInternal(now = clock())
            emitProgress()
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(SyncPhase.Profiles, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(SyncPhase.Profiles, error)
        }
    }

    /**
     * The reconnect and polling path: phase 1 again, details for Dives that only have a list row, and the deleted-Dives
     * cursor. It marks a full sync when it ends, because the store is then current as of now.
     */
    suspend fun refresh(): SyncResult = lock.withLock {
        emitProgress()
        try {
            refreshInternal(now = clock())
            emitProgress()
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(SyncPhase.Meta, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(SyncPhase.Meta, error)
        }
    }

    /**
     * A `dive` hint: list page 1 keeps the row's current summary, the detail replaces the cached values wholesale, and
     * the Dive's profile checkpoint goes back to pending so the background phase refetches its samples. A 404 means the
     * Dive was deleted meanwhile; it becomes a tombstone, never a local hard delete.
     */
    suspend fun applyDiveHint(id: String): SyncResult = lock.withLock {
        val now = clock()
        emitProgress()
        try {
            refreshDiveListPageOne(now)
            fetchAndStoreDetail(id, now)
            markHint(now)
            emitProgress()
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(SyncPhase.Details, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(SyncPhase.Details, error)
        }
    }

    /** A `site` hint: refetch that one Dive site through the normal API. A site gone meanwhile leaves the cache as is. */
    suspend fun applySiteHint(id: String): SyncResult = lock.withLock {
        val now = clock()
        emitProgress()
        try {
            try {
                val site = fetchJson<SiteView>("/api/dive-sites/${id.encodeURLParameter()}")
                store.transaction { store.upsertDiveSite(site.toStore()) }
            } catch (error: DiveHubException) {
                if (error.status != 404) throw error
            }
            markHint(now)
            emitProgress()
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(SyncPhase.Meta, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(SyncPhase.Meta, error)
        }
    }

    /** Whether this Dive's profiles (all its Recordings' series) are in the store; the UI's per-Dive marker reads it. */
    fun profileSynced(diveId: String): Boolean =
        store.checkpoint(profileEntity(diveId), PHASE_PROFILES)?.done == true

    /** Whether any live Dive with a stored detail still needs its profiles, so a refresh can hand phase 3 some work. */
    fun hasPendingProfiles(): Boolean = store.allDives().any {
        it.deletedAt == null && it.version != null && !profileSynced(it.id)
    }

    /** The store's per-phase counts, read in one transaction so a concurrent write cannot mix two snapshots. */
    fun progress(): SyncProgress = store.transaction {
        val active = store.allDives().filter { it.deletedAt == null }
        val awaiting = active.count { !profileSynced(it.id) }
        SyncProgress(
            lastFullSyncAt = store.syncState().lastFullSyncAt,
            lastHintAt = store.syncState().lastHintAt,
            metaDone = metaDone(),
            detailsDone = active.all { it.version != null },
            profilesDone = awaiting == 0,
            storedDivers = store.divers().size.toLong(),
            storedBodyWeights = store.allBodyWeights().size.toLong(),
            storedDiveSites = store.diveSites().size.toLong(),
            storedDives = store.activeDiveCount(),
            detailedDives = active.count { it.version != null }.toLong(),
            divesAwaitingProfiles = awaiting.toLong(),
        )
    }

    /** Hands the current counts to [onProgress], always outside a store transaction. */
    private fun emitProgress() {
        onProgress?.invoke(progress())
    }

    private suspend fun syncMeta(now: String) {
        syncDivers(now)
        syncBodyWeights(now)
        syncDiveSites(now)
        syncDives(now)
    }

    private suspend fun syncDivers(now: String) {
        if (store.checkpoint(ENTITY_DIVERS, PHASE_META)?.done == true) return
        val divers = fetchJson<List<DiverView>>("/api/divers")
        store.transaction {
            divers.forEach { store.upsertDiver(it.toStore()) }
            store.saveCheckpoint(checkpoint(ENTITY_DIVERS, PHASE_META, cursor = null, pageOffset = null, done = true, now = now))
        }
    }

    private suspend fun syncBodyWeights(now: String) {
        val existing = store.checkpoint(ENTITY_BODY_WEIGHTS, PHASE_META)
        if (existing?.done == true) return
        val divers = store.divers()
        val start = existing?.pageOffset?.toInt()?.coerceAtLeast(0) ?: 0
        if (start >= divers.size) {
            store.transaction {
                store.saveCheckpoint(
                    checkpoint(ENTITY_BODY_WEIGHTS, PHASE_META, divers.lastOrNull()?.id, divers.size.toLong(), true, now),
                )
            }
            return
        }
        for (index in start until divers.size) {
            val diver = divers[index]
            val weights = fetchJson<List<ApiDiverBodyWeightsGet200ResponseItem>>("/api/divers/${diver.id}/body-weights")
            val next = index + 1
            store.transaction {
                weights.forEach { store.upsertBodyWeight(it.toStore(diver.id)) }
                store.saveCheckpoint(
                    checkpoint(ENTITY_BODY_WEIGHTS, PHASE_META, diver.id, next.toLong(), next >= divers.size, now),
                )
            }
        }
    }

    private suspend fun syncDiveSites(now: String) {
        val existing = store.checkpoint(ENTITY_DIVE_SITES, PHASE_META)
        if (existing?.done == true) return
        var offset = existing?.pageOffset ?: 0
        while (true) {
            val page = fetchJson<ApiDiveSitesGet200Response>("/api/dive-sites?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=$offset")
            val next = offset + page.sites.size
            val done = page.sites.isEmpty() || next >= page.total
            store.transaction {
                page.sites.forEach { store.upsertDiveSite(it.toStore()) }
                store.saveCheckpoint(checkpoint(ENTITY_DIVE_SITES, PHASE_META, cursor = null, pageOffset = next, done = done, now = now))
            }
            if (done) return
            offset = next
        }
    }

    private suspend fun syncDives(now: String) {
        val existing = store.checkpoint(ENTITY_DIVES, PHASE_META)
        if (existing?.done == true) return
        var offset = existing?.pageOffset ?: 0
        while (true) {
            val page = fetchJson<DiveListView>("/api/dives?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=$offset")
            val next = offset + page.dives.size
            val done = page.dives.isEmpty() || next >= page.total
            store.transaction {
                page.dives.forEach { store.upsertDive(it.toStore(store.diveIncludingDeleted(it.id))) }
                store.saveLogbookCounts(json.encodeToString(page.counts))
                store.saveCheckpoint(checkpoint(ENTITY_DIVES, PHASE_META, cursor = null, pageOffset = next, done = done, now = now))
            }
            emitProgress()
            if (done) return
            offset = next
        }
    }

    private suspend fun syncDetails(now: String) {
        val existing = store.checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS)
        // A store migrated from v1 has detailed rows without their API payload: fetch every detail once so the
        // screens read exactly what the API sent. A row still waiting for its first detail is not stale.
        val stalePayloads = store.allDives().any {
            it.deletedAt == null && it.version != null && it.detailJson == null
        }
        if (existing?.done == true && !stalePayloads) return
        var offset = if (stalePayloads) 0L else existing?.pageOffset?.coerceAtLeast(0) ?: 0L
        var total = 0L
        while (true) {
            // A details page is a whole Dive per row, so the server caps it lower than the summary list (FAH-297).
            // A pre-FAH-297 server accepts the unknown query parameter but answers the summary list; that is an old
            // server, not a failed sync, so fall through to the per-Dive repair path below.
            val page = fetchDetailsPage(
                "/api/dives?include=details&limit=$DETAILS_PAGE_SIZE&offset=$offset",
            ) ?: break
            total = page.total.toLong()
            val next = offset + page.dives.size
            val done = page.dives.isEmpty() || next >= total
            store.transaction {
                page.dives.forEach { writeDetail(it) }
                store.saveCheckpoint(
                    checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, cursor = null, pageOffset = next, done = done, now = now),
                )
            }
            emitProgress()
            if (done) break
            offset = next
        }
        // The per-Dive fetch stays the repair path (ADR 0066): a Dive phase 1 stored but the details pages did not
        // return (deleted meanwhile, or a page boundary moved) is fetched on its own, and a 404 leaves a tombstone.
        // The same goes for a v1 row whose version is known but whose API payload is still missing. Their progress is
        // not a page, so the phase stays open until each miss is written or tombstoned.
        val missing = store.allDives().filter { it.deletedAt == null && (it.version == null || it.detailJson == null) }
        if (missing.isEmpty()) return
        store.transaction {
            store.saveCheckpoint(checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, cursor = null, pageOffset = total, done = false, now = now))
        }
        for (dive in missing) {
            fetchAndStoreDetail(dive.id, now)
            emitProgress()
        }
        store.transaction {
            val repaired = store.allDives().none { it.deletedAt == null && (it.version == null || it.detailJson == null) }
            store.saveCheckpoint(checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, cursor = null, pageOffset = total, done = repaired, now = now))
        }
        emitProgress()
    }

    private suspend fun syncProfilesInternal(now: String) {
        // A snapshot: a Dive that arrives during the phase is picked up by the next run. A Dive whose detail has
        // not synced has no Recordings yet; marking it done here would hide its profiles forever.
        for (dive in store.allDives()) {
            if (dive.deletedAt != null || dive.version == null) continue
            if (profileSynced(dive.id)) continue
            syncDiveProfiles(dive.id, now)
            emitProgress()
        }
    }

    private suspend fun syncDiveProfiles(diveId: String, now: String) {
        val recordings = store.recordings(diveId)
        val existing = store.checkpoint(profileEntity(diveId), PHASE_PROFILES)
        val start = existing?.pageOffset?.toInt()?.coerceAtLeast(0) ?: 0
        if (start >= recordings.size) {
            store.transaction {
                store.saveCheckpoint(SyncCheckpoint(profileEntity(diveId), PHASE_PROFILES, cursor = null, pageOffset = recordings.size.toLong(), done = true, updatedAt = now))
            }
            return
        }
        for (index in start until recordings.size) {
            syncRecordingProfiles(recordings[index], now)
            val next = index + 1
            store.transaction {
                store.saveCheckpoint(
                    SyncCheckpoint(
                        entity = profileEntity(diveId),
                        phase = PHASE_PROFILES,
                        cursor = null,
                        pageOffset = next.toLong(),
                        done = next >= recordings.size,
                        updatedAt = now,
                    ),
                )
            }
        }
    }

    private suspend fun syncRecordingProfiles(recording: Recording, now: String) {
        val channels = channelsOf(recording)
        if (channels.isEmpty()) return
        val wanted = channels.joinToString(",") { it.encodeURLParameter() }
        val samples = try {
            fetchJson<RecordingSamplesView>(
                "/api/recordings/${recording.id.encodeURLParameter()}/samples?channels=$wanted&maxPoints=$MAX_PROFILE_POINTS",
            )
        } catch (error: DiveHubException) {
            if (error.status != 404) throw error
            // The Recording is gone: drop it and its series rather than failing this phase forever on a 404.
            store.transaction {
                store.deleteSeries(recording.id)
                store.deleteRecording(recording.id)
            }
            return
        }
        store.transaction {
            // A refetch replaces the Recording's channels: one the server no longer reports must not linger.
            store.deleteSeries(recording.id)
            samples.series.forEach { series ->
                store.upsertSeries(
                    RecordingSeries(
                        recordingId = recording.id,
                        channel = series.channel,
                        offsetsMsJson = json.encodeToString(series.offsetsMs),
                        valuesJson = json.encodeToString(series.values),
                        fetchedAt = now,
                    ),
                )
            }
        }
    }

    private suspend fun refreshInternal(now: String) {
        refreshDivers(now)
        refreshBodyWeights(now)
        refreshDiveSites(now)
        refreshDives(now)
        // Tombstones before the new details: a Dive deleted meanwhile must not be fetched as if it were new.
        try {
            syncTombstones(now)
        } catch (error: DiveHubException) {
            if (error.status != 404) throw error
            // A server that predates the route cannot report deletions; the rest of the refresh still stands.
        }
        refreshNewDetails(now)
        markFullSync(now)
    }

    private suspend fun refreshDivers(now: String) {
        val divers = fetchJson<List<DiverView>>("/api/divers")
        store.transaction {
            divers.forEach { store.upsertDiver(it.toStore()) }
            store.saveCheckpoint(checkpoint(ENTITY_DIVERS, PHASE_META, cursor = null, pageOffset = null, done = true, now = now))
        }
    }

    private suspend fun refreshBodyWeights(now: String) {
        val divers = store.divers()
        for ((index, diver) in divers.withIndex()) {
            val weights = fetchJson<List<ApiDiverBodyWeightsGet200ResponseItem>>("/api/divers/${diver.id}/body-weights")
            val next = index + 1
            store.transaction {
                weights.forEach { store.upsertBodyWeight(it.toStore(diver.id)) }
                store.saveCheckpoint(
                    checkpoint(ENTITY_BODY_WEIGHTS, PHASE_META, diver.id, next.toLong(), next >= divers.size, now),
                )
            }
        }
    }

    private suspend fun refreshDiveSites(now: String) {
        var offset = 0L
        while (true) {
            val page = fetchJson<ApiDiveSitesGet200Response>("/api/dive-sites?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=$offset")
            val next = offset + page.sites.size
            val done = page.sites.isEmpty() || next >= page.total
            store.transaction {
                page.sites.forEach { store.upsertDiveSite(it.toStore()) }
                store.saveCheckpoint(checkpoint(ENTITY_DIVE_SITES, PHASE_META, cursor = null, pageOffset = next, done = done, now = now))
            }
            if (done) return
            offset = next
        }
    }

    private suspend fun refreshDives(now: String) {
        var offset = 0L
        while (true) {
            val page = fetchJson<DiveListView>("/api/dives?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=$offset")
            val next = offset + page.dives.size
            val done = page.dives.isEmpty() || next >= page.total
            store.transaction {
                page.dives.forEach { store.upsertDive(it.toStore(store.diveIncludingDeleted(it.id))) }
                store.saveLogbookCounts(json.encodeToString(page.counts))
                store.saveCheckpoint(checkpoint(ENTITY_DIVES, PHASE_META, cursor = null, pageOffset = next, done = done, now = now))
            }
            if (done) return
            offset = next
        }
    }

    /** `GET /api/dives/deleted`, paged by its opaque cursor; each row becomes a local tombstone, nothing is deleted. */
    private suspend fun syncTombstones(now: String) {
        val existing = store.checkpoint(ENTITY_DELETED_DIVES, PHASE_TOMBSTONES)
        var cursor = existing?.takeUnless { it.done }?.cursor
        val resuming = cursor != null
        while (true) {
            val query = cursor?.let { "&cursor=${it.encodeURLParameter()}" }.orEmpty()
            val page = fetchJson<DeletedDivesPage>("/api/dives/deleted?limit=${DiveStore.DEFAULT_PAGE_SIZE}$query")
            val next = page.nextCursor
            // Newest first: on a fresh walk an unchanged page means every older page is unchanged too, so the
            // common "no new deletions" refresh costs one request instead of the whole history. A resumed walk
            // must not stop early: deletions added at the top since the interrupted run were not seen.
            val unchanged = page.dives.all { it.deletedAt == store.diveIncludingDeleted(it.id)?.deletedAt }
            val done = next == null || (unchanged && !resuming)
            store.transaction {
                page.dives.forEach { store.markDiveDeleted(it.id, it.deletedAt, it.version.toLong()) }
                store.saveCheckpoint(
                    SyncCheckpoint(
                        entity = ENTITY_DELETED_DIVES,
                        phase = PHASE_TOMBSTONES,
                        cursor = if (done) null else next,
                        pageOffset = null,
                        done = done,
                        updatedAt = now,
                    ),
                )
            }
            if (done) return
            cursor = next
        }
    }

    private suspend fun refreshNewDetails(now: String) {
        val pending = store.allDives().filter { it.deletedAt == null && (it.version == null || it.detailJson == null) }
        for (dive in pending) fetchAndStoreDetail(dive.id, now)
        store.transaction {
            store.saveCheckpoint(
                checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, cursor = null, pageOffset = store.activeDiveCount(), done = true, now = now),
            )
        }
    }

    private suspend fun refreshDiveListPageOne(now: String) {
        val page = fetchJson<DiveListView>("/api/dives?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=0")
        store.transaction {
            page.dives.forEach { store.upsertDive(it.toStore(store.diveIncludingDeleted(it.id))) }
            store.saveLogbookCounts(json.encodeToString(page.counts))
            store.saveCheckpoint(checkpoint(ENTITY_DIVES, PHASE_META, cursor = null, pageOffset = page.dives.size.toLong(), done = true, now = now))
        }
    }

    /**
     * One Dive's detail into the store, with its profile checkpoint back to pending. A 404 writes a tombstone: a hint
     * arrived for a Dive deleted elsewhere, and the cached row is kept rather than removed.
     */
    private suspend fun fetchAndStoreDetail(diveId: String, now: String): Boolean = try {
        val detail = fetchJson<DiveView>("/api/dives/${diveId.encodeURLParameter()}")
        store.transaction {
            writeDetail(detail)
            store.saveCheckpoint(pendingProfileCheckpoint(diveId, now))
        }
        true
    } catch (error: DiveHubException) {
        if (error.status != 404) throw error
        store.transaction {
            store.markDiveDeleted(diveId, now)
            store.saveCheckpoint(pendingProfileCheckpoint(diveId, now))
        }
        false
    }

    /**
     * One Dive's detail into the store, inside the caller's transaction. The detail is authoritative: Recordings and
     * Cylinders it no longer lists are removed with their series, so a stale Recording cannot make the profiles phase
     * fail on a 404 forever.
     */
    private fun writeDetail(detail: DiveView) {
        val diveId = detail.id
        val existing = store.diveIncludingDeleted(diveId)
        store.upsertDive(detail.toStore(existing).copy(listJson = detail.toListRow(existing?.listJson)))
        val kept = detail.recordings.mapTo(mutableSetOf()) { it.id }
        store.allRecordingsForDive(diveId).filter { it.id !in kept }.forEach { recording ->
            store.deleteSeries(recording.id)
            store.deleteRecording(recording.id)
        }
        detail.recordings.forEach { store.upsertRecording(it.toStore(diveId)) }
        store.deleteCylinders(diveId)
        detail.cylinders.forEachIndexed { position, cylinder ->
            store.upsertCylinder(cylinder.toStore(diveId, position.toLong()))
        }
    }

    private fun pendingProfileCheckpoint(diveId: String, now: String): SyncCheckpoint = SyncCheckpoint(
        entity = profileEntity(diveId),
        phase = PHASE_PROFILES,
        cursor = null,
        pageOffset = 0,
        done = false,
        updatedAt = now,
    )

    /** The channel names the detail pass listed; a malformed local value means no profiles rather than a failed phase. */
    private fun channelsOf(recording: Recording): List<String> = try {
        json.decodeFromString<List<String>>(recording.channelsJson)
    } catch (_: Exception) {
        emptyList()
    }

    /**
     * One details page. A pre-FAH-297 server accepts `include=details` but answers the summary list, which does not
     * decode as [DiveDetailsListView]; that body is not a failed sync, so the caller falls back to one request per
     * Dive. Any other decode failure is real and rethrown.
     */
    private suspend fun fetchDetailsPage(path: String): DiveDetailsListView? {
        val body = fetch(path).bodyAsText()
        return try {
            api.decode(body, DiveDetailsListView.serializer())
        } catch (error: SerializationException) {
            if (decodesAsSummaryList(body)) null else throw error
        }
    }

    private fun decodesAsSummaryList(body: String): Boolean = try {
        api.decode(body, DiveListView.serializer())
        true
    } catch (_: SerializationException) {
        false
    }

    private fun profileEntity(diveId: String): String = "$ENTITY_PROFILE_PREFIX$diveId"

    private fun markFullSync(now: String) {
        store.transaction {
            val state = store.syncState()
            store.saveSyncState(state.copy(lastFullSyncAt = now))
        }
    }

    private fun markHint(now: String) {
        store.transaction {
            val state = store.syncState()
            store.saveSyncState(state.copy(lastHintAt = now))
        }
    }

    private fun metaDone(): Boolean = listOf(ENTITY_DIVERS, ENTITY_BODY_WEIGHTS, ENTITY_DIVE_SITES, ENTITY_DIVES)
        .all { store.checkpoint(it, PHASE_META)?.done == true }

    private fun checkpoint(
        entity: String,
        phase: String,
        cursor: String?,
        pageOffset: Long?,
        done: Boolean,
        now: String,
    ): SyncCheckpoint = SyncCheckpoint(
        entity = entity,
        phase = phase,
        cursor = cursor,
        pageOffset = pageOffset,
        done = done,
        updatedAt = now,
    )

    /**
     * One page with the engine's own policy: retry only a network failure or a 5xx, once, with [backoff] between the
     * attempts. The HTTP layer's retry is off here so the policy is not applied twice. A 401 has already ended the
     * session in the HTTP layer and is never retried.
     */
    private suspend fun fetch(path: String): HttpResponse {
        var attempt = 0
        while (true) {
            try {
                val response = api.get(path, retry = false)
                if (response.status.isSuccess()) return response
                val failure = api.failure(response)
                if (!retryable(failure) || attempt >= MAX_RETRIES) throw failure
            } catch (error: NoAnswerException) {
                if (attempt >= MAX_RETRIES) throw error
            }
            attempt += 1
            backoff(attempt)
        }
    }

    private suspend inline fun <reified T> fetchJson(path: String): T = api.decode(fetch(path))

    private fun retryable(error: DiveHubException): Boolean = error.status in 500..599

    private companion object {
        const val PHASE_META = "meta"
        const val PHASE_DETAILS = "details"
        const val PHASE_PROFILES = "profiles"
        const val ENTITY_DIVERS = "divers"
        const val ENTITY_BODY_WEIGHTS = "body_weights"
        const val ENTITY_DIVE_SITES = "dive_sites"
        const val ENTITY_DIVES = "dives"
        const val ENTITY_DIVE_DETAILS = "dive_details"
        const val ENTITY_DELETED_DIVES = "deleted_dives"
        const val ENTITY_PROFILE_PREFIX = "profiles:"
        const val PHASE_TOMBSTONES = "tombstones"
        const val MAX_RETRIES = 1
        const val RETRY_BACKOFF_MS = 1_000L

        /** The details mode's server cap (FAH-297): a page carries a whole Dive per row. */
        const val DETAILS_PAGE_SIZE = 50

        /** The samples route's verified maximum (FAH-269): an explicit high cap keeps the peaks for offline replay. */
        const val MAX_PROFILE_POINTS = 20_000
    }
}
