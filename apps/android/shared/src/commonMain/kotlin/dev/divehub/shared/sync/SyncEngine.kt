package dev.divehub.shared.sync

import dev.divehub.api.models.ApiDiveSitesGet200Response
import dev.divehub.api.models.ApiDiverBodyWeightsGet200ResponseItem
import dev.divehub.api.models.ApiDivesGet200Response
import dev.divehub.api.models.DiverView
import dev.divehub.api.models.DiveView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncCheckpoint
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlin.time.Clock

/** The two phases this slice syncs, in their strict order (FAH-265 plan §3). */
enum class SyncPhase { Meta, Details }

/** What one [SyncEngine.sync] call did. */
sealed interface SyncResult {
    /** Both phases are complete and the store is the logbook. */
    data object Complete : SyncResult

    /** A 401 ended the session: the engine stops and cached data stays readable (client contract §2). */
    data object SessionEnded : SyncResult

    /** A retryable failure outlasted the retry: the failed phase keeps its checkpoint for the next trigger. */
    data class Stopped(val phase: SyncPhase, val error: Throwable) : SyncResult
}

/**
 * The per-phase state the UI reads for its sync line and staleness label: how much of each phase is in the store.
 */
data class SyncProgress(
    val lastFullSyncAt: String?,
    val metaDone: Boolean,
    val detailsDone: Boolean,
    val storedDivers: Long,
    val storedBodyWeights: Long,
    val storedDiveSites: Long,
    val storedDives: Long,
    val detailedDives: Long,
)

/**
 * The checkpointed sync loop (FAH-265 plan §3–§4). Every page is fetched, written and checkpointed in one store
 * transaction, so an interruption resumes the exact phase and offset and a crash never double-writes a page. The
 * cursor is cursor-shaped even while V1 pulls full pages, so the later change feed can replace the page source.
 *
 * Phase 1 (meta) makes the logbook list render offline; phase 2 (details) makes the dive page render offline. The
 * engine has no Android imports: the app's WorkManager trigger arrives later, behind this plain `suspend` call.
 */
class SyncEngine(
    private val api: ApiClient,
    private val store: DiveStore,
    private val clock: () -> String = { Clock.System.now().toString() },
    private val backoff: suspend (attempt: Int) -> Unit = { delay(RETRY_BACKOFF_MS * it) },
) {
    /** Run or resume both phases. A completed sync is a no-op until the refresh slice (D3) adds its triggers. */
    suspend fun sync(): SyncResult {
        if (store.syncState().lastFullSyncAt != null) return SyncResult.Complete
        var phase = SyncPhase.Meta
        return try {
            syncMeta(now = clock())
            phase = SyncPhase.Details
            syncDetails(now = clock())
            markFullSync(now = clock())
            SyncResult.Complete
        } catch (error: CancellationException) {
            throw error
        } catch (error: DiveHubException) {
            if (error.status == 401) SyncResult.SessionEnded else SyncResult.Stopped(phase, error)
        } catch (error: NoAnswerException) {
            SyncResult.Stopped(phase, error)
        }
    }

    /** The store's per-phase counts, read in one transaction so a concurrent write cannot mix two snapshots. */
    fun progress(): SyncProgress = store.transaction {
        SyncProgress(
            lastFullSyncAt = store.syncState().lastFullSyncAt,
            metaDone = metaDone(),
            detailsDone = store.checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS)?.done == true,
            storedDivers = store.divers().size.toLong(),
            storedBodyWeights = store.allBodyWeights().size.toLong(),
            storedDiveSites = store.diveSites().size.toLong(),
            storedDives = store.activeDiveCount(),
            detailedDives = store.checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS)?.pageOffset ?: 0,
        )
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
            val page = fetchJson<ApiDivesGet200Response>("/api/dives?limit=${DiveStore.DEFAULT_PAGE_SIZE}&offset=$offset")
            val next = offset + page.dives.size
            val done = page.dives.isEmpty() || next >= page.total
            store.transaction {
                page.dives.forEach { store.upsertDive(it.toStore()) }
                store.saveCheckpoint(checkpoint(ENTITY_DIVES, PHASE_META, cursor = null, pageOffset = next, done = done, now = now))
            }
            if (done) return
            offset = next
        }
    }

    private suspend fun syncDetails(now: String) {
        val existing = store.checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS)
        if (existing?.done == true) return
        val dives = store.allDives()
        val start = existing?.pageOffset?.toInt()?.coerceAtLeast(0) ?: 0
        if (start >= dives.size) {
            store.transaction {
                store.saveCheckpoint(
                    checkpoint(
                        ENTITY_DIVE_DETAILS,
                        PHASE_DETAILS,
                        dives.lastOrNull()?.id,
                        dives.size.toLong(),
                        done = true,
                        now = now,
                    ),
                )
            }
            return
        }
        for (index in start until dives.size) {
            val dive = dives[index]
            val next = index + 1
            val done = next >= dives.size
            try {
                val detail = fetchJson<DiveView>("/api/dives/${dive.id}")
                store.transaction {
                    store.upsertDive(detail.toStore(store.diveIncludingDeleted(dive.id)))
                    detail.recordings.forEach { store.upsertRecording(it.toStore(dive.id)) }
                    detail.cylinders.forEachIndexed { position, cylinder ->
                        store.upsertCylinder(cylinder.toStore(dive.id, position.toLong()))
                    }
                    store.saveCheckpoint(
                        checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, dive.id, next.toLong(), done, now),
                    )
                }
            } catch (error: DiveHubException) {
                // Deleted between the list and this fetch: the tombstone is a later slice; skip it for now.
                if (error.status != 404) throw error
                store.transaction {
                    store.saveCheckpoint(
                        checkpoint(ENTITY_DIVE_DETAILS, PHASE_DETAILS, dive.id, next.toLong(), done, now),
                    )
                }
            }
        }
    }

    private fun markFullSync(now: String) {
        store.transaction {
            val state = store.syncState()
            store.saveSyncState(state.copy(lastFullSyncAt = now))
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
        const val ENTITY_DIVERS = "divers"
        const val ENTITY_BODY_WEIGHTS = "body_weights"
        const val ENTITY_DIVE_SITES = "dive_sites"
        const val ENTITY_DIVES = "dives"
        const val ENTITY_DIVE_DETAILS = "dive_details"
        const val MAX_RETRIES = 1
        const val RETRY_BACKOFF_MS = 1_000L
    }
}
