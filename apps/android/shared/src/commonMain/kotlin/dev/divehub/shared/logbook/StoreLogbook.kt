package dev.divehub.shared.logbook

import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewCounts
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemGasesItem
import dev.divehub.api.models.DiveListViewDivesItemSite
import dev.divehub.api.models.DiveListViewMonthsItem
import dev.divehub.api.models.DiveListViewTotals
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewCylindersItemGas
import dev.divehub.api.models.DiveViewLeadItem
import dev.divehub.api.models.DiveViewParticipantsItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewSac
import dev.divehub.api.models.DiveViewSite
import dev.divehub.api.models.DiveViewSuit
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.api.models.DiveViewWeightingFeedback
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.NeighboursViewNext
import dev.divehub.api.models.NeighboursViewPrevious
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import kotlinx.serialization.json.Json

private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }
private const val PROFILE_PHASE = "profiles"
private const val PROFILE_ENTITY_PREFIX = "profiles:"

/** The store's staleness and per-Dive profile state, with no page attached: the dive page's sync line reads it too. */
data class StoreLogbookContext(
    val syncedAt: String?,
    /** Dives whose Recordings are in the store but whose sample series have not finished syncing. */
    val profilePending: Set<String>,
) {
    val hasSync: Boolean get() = syncedAt != null
}

/** One logbook read: the API's shape, plus what the sync line and the per-Dive marker need. */
data class LogbookRead(
    val data: DiveListView,
    val syncedAt: String?,
    val profilePending: Set<String>,
) {
    val hasSync: Boolean get() = syncedAt != null
}

/** One Dive read from the store: the API's shape, plus the sync line and this Dive's profile marker. */
data class StoredDive(
    val dive: DiveView,
    val syncedAt: String?,
    val profilePending: Boolean,
) {
    val hasSync: Boolean get() = syncedAt != null
}

/**
 * The logbook reads the store holds (FAH-265 plan D4; client contract, Offline reads): the list, one Dive, its
 * neighbours and its profile series, without a request. The stored list rows and Dive views are the API's own JSON,
 * so the screens draw what the server sent; totals and months are computed here from the stored Dives because the
 * server computes them from the same rows. The per-filter chip counts come from the last list sync, so a chip keeps
 * its number offline.
 *
 * `changed-since-sent` and `not-at-provider` depend on server-side Push state the store does not hold; a caller asks
 * [canAnswer] first and fetches those online (client contract, Offline reads).
 */
class StoreLogbook(private val store: DiveStore) {
    fun context(): StoreLogbookContext = store.transaction {
        StoreLogbookContext(
            syncedAt = store.syncState().lastFullSyncAt,
            profilePending = pendingProfiles(),
        )
    }

    /** Whether every filter can be answered from the store; false means the caller needs the API. */
    fun canAnswer(filters: Set<LogbookFilter>): Boolean = filters.none(::needsApi)

    /** The filters the store can still apply when a server-only one cannot be fetched offline. */
    fun storedFilters(filters: Set<LogbookFilter>): Set<LogbookFilter> = filters.filterNot(::needsApi).toSet()

    private fun needsApi(filter: LogbookFilter): Boolean =
        filter == LogbookFilter.CHANGED_SINCE_SENT || filter == LogbookFilter.NOT_AT_PROVIDER

    fun list(filters: Set<LogbookFilter>): LogbookRead = store.transaction {
        val rows = store.allDives().filter { it.deletedAt == null }
        val items = rows.map { it.toListItem() }
        val matching = items.filter { matches(it, filters) }
        val counts = storedCounts() ?: countsOf(items)
        LogbookRead(
            data = DiveListView(
                dives = matching,
                total = matching.size,
                counts = counts,
                totals = totalsOf(rows),
                months = monthsOf(matching),
            ),
            syncedAt = store.syncState().lastFullSyncAt,
            profilePending = pendingProfiles(),
        )
    }

    fun dive(id: String): StoredDive? = store.transaction {
        val row = store.dive(id) ?: return@transaction null
        val dive = row.detail() ?: row.toDetail()
        val recordings = store.recordings(row.id)
        StoredDive(
            dive = dive,
            syncedAt = store.syncState().lastFullSyncAt,
            profilePending = recordings.isNotEmpty() && !profileSynced(row.id),
        )
    }

    /** The Dive's neighbours in the list those filters show, as `GET /api/dives/{id}/neighbours` answers them. */
    fun neighbours(id: String, filters: Set<LogbookFilter>): NeighboursView = store.transaction {
        val items = store.allDives()
            .filter { it.deletedAt == null }
            .map { it.toListItem() }
            .filter { matches(it, filters) }
        val index = items.indexOfFirst { it.id == id }
        if (index < 0) {
            NeighboursView(previous = null, next = null, position = null, total = items.size)
        } else {
            NeighboursView(
                previous = items.getOrNull(index - 1)?.let { NeighboursViewPrevious(it.id) },
                next = items.getOrNull(index + 1)?.let { NeighboursViewNext(it.id) },
                position = index + 1,
                total = items.size,
            )
        }
    }

    /** Whether the store holds any series row for this Recording, even one that no longer decodes. */
    fun hasSeries(recordingId: String): Boolean = store.series(recordingId).isNotEmpty()

    /**
     * The live Dive sites the store holds, by name: the same server-existing sites a picker may choose from, already
     * cached by the full sync, so a site can be chosen with no connection (ADR 0075, client contract "Offline writes").
     */
    fun sites(): List<DiveSite> = store.diveSites()

    /** The stored depth and temperature series, or null when the profiles phase has not written them yet. */
    fun samples(recordingId: String): RecordingSamplesView? = store.transaction {
        val series = store.series(recordingId).mapNotNull { row ->
            val offsets = decode<List<Int>>(row.offsetsMsJson) ?: return@mapNotNull null
            val values = decode<List<Double>>(row.valuesJson) ?: return@mapNotNull null
            RecordingSamplesViewSeriesItem(channel = row.channel, offsetsMs = offsets, values = values)
        }
        if (series.isEmpty()) null else RecordingSamplesView(recordingId = recordingId, series = series)
    }

    private fun pendingProfiles(): Set<String> {
        val recordingsByDive = store.allRecordings()
            .filter { it.deletedAt == null }
            .groupBy { it.diveId }
        return store.allDives()
            .filter { it.deletedAt == null }
            .filter { row -> recordingsByDive[row.id].orEmpty().isNotEmpty() && !profileSynced(row.id) }
            .mapTo(LinkedHashSet()) { it.id }
    }

    private fun profileSynced(diveId: String): Boolean =
        store.checkpoint(PROFILE_ENTITY_PREFIX + diveId, PROFILE_PHASE)?.done == true

    private fun storedCounts(): DiveListViewCounts? =
        store.syncState().countsJson?.let { decode(it) }

    private fun countsOf(items: List<DiveListViewDivesItem>): DiveListViewCounts =
        DiveListViewCounts(
            noRecording = items.count { it.recordings == 0 },
            noSite = items.count { it.site == null },
            withFindings = items.count { it.findings > 0 },
            // The server works these out from Push state the store does not hold: 0 hides the chip until a sync.
            notAtProvider = 0,
            changedSinceSent = 0,
        )

    private fun totalsOf(rows: List<Dive>): DiveListViewTotals =
        DiveListViewTotals(
            dives = rows.size,
            durationSeconds = rows.sumOf { it.durationSeconds ?: 0L }.toDouble(),
            deepestM = rows.mapNotNull { it.maxDepthM }.maxOrNull(),
            lastDiveAt = rows.maxByOrNull { it.startsAt }?.startsAt,
        )

    private fun monthsOf(dives: List<DiveListViewDivesItem>): List<DiveListViewMonthsItem> {
        val byMonth = dives.groupBy { localMonthOf(it.startsAt, it.utcOffsetSeconds) }
        return dives.mapNotNull { localMonthOf(it.startsAt, it.utcOffsetSeconds) }
            .distinct()
            .mapNotNull { month ->
                byMonth[month]?.let { matching ->
                    DiveListViewMonthsItem(
                        month = month,
                        dives = matching.size,
                        durationSeconds = matching.sumOf { it.durationSeconds },
                    )
                }
            }
    }

    private fun matches(item: DiveListViewDivesItem, filters: Set<LogbookFilter>): Boolean =
        filters.all { filter ->
            when (filter) {
                LogbookFilter.NO_RECORDING -> item.recordings == 0
                LogbookFilter.NO_SITE -> item.site == null
                LogbookFilter.WITH_FINDINGS -> item.findings > 0
                LogbookFilter.NOT_AT_PROVIDER, LogbookFilter.CHANGED_SINCE_SENT -> true
            }
        }

    private fun Dive.toListItem(): DiveListViewDivesItem {
        listJson?.let { decode<DiveListViewDivesItem>(it) }?.let { return it }
        val recordings = store.recordings(id)
        val primary = recordings.firstOrNull { it.isPrimary } ?: recordings.firstOrNull()
        val summary = primary?.summary()
        val site = siteId?.let { store.diveSite(it) }
        return DiveListViewDivesItem(
            id = id,
            diverId = diverId,
            number = number?.toInt(),
            startsAt = startsAt,
            utcOffsetSeconds = utcOffsetSeconds?.toInt(),
            utcOffsetSource = utcOffsetSource ?: "unknown",
            durationSeconds = (durationSeconds ?: 0L).toDouble(),
            maxDepthM = maxDepthM,
            avgDepthM = avgDepthM,
            site = site?.let { DiveListViewDivesItemSite(id = it.id, name = it.name) },
            findings = (findingsCount ?: 0L).toInt(),
            recordings = recordings.size,
            fromProvider = fromProvider,
            gases = summary?.gases.orEmpty().map {
                DiveListViewDivesItemGasesItem(o2 = it.o2, he = it.he)
            },
            surfaceIntervalSeconds = summary?.surfaceIntervalSeconds,
            participants = emptyList(),
            waiting = emptyList(),
            profile = null,
        )
    }

    private fun Dive.detail(): DiveView? = detailJson?.let { decode<DiveView>(it) }

    /** A v1 store row without its detail payload: enough for the page to stand, refilled by the next sync. */
    private fun Dive.toDetail(): DiveView {
        val recordings = store.recordings(id).map { it.toView() }
        val site = siteId?.let { store.diveSite(it) }
        return DiveView(
            id = id,
            diverId = diverId,
            version = (version ?: 0L).toInt(),
            values = DiveViewValues(
                number = number?.toInt(),
                startsAt = DiveViewValuesStartsAt(startsAt, utcOffsetSeconds?.toInt()),
                durationSeconds = (durationSeconds ?: 0L).toDouble(),
                maxDepthM = maxDepthM,
                avgDepthM = avgDepthM,
                waterTemperatureC = null,
            ),
            overrides = emptyList(),
            utcOffsetSource = utcOffsetSource ?: "unknown",
            fromProvider = fromProvider,
            fromRecording = null,
            notes = notes,
            site = site?.let { DiveViewSite(id = it.id, name = it.name) },
            waterType = waterType,
            waterMismatch = null,
            position = null,
            recordings = recordings,
            cylinders = store.cylinders(id).map { row ->
                DiveViewCylindersItem(
                    volumeL = row.volumeL,
                    workingPressureBar = null,
                    material = row.material,
                    gas = if (row.o2 == null || row.he == null) null else DiveViewCylindersItemGas(row.o2, row.he),
                    startPressureBar = row.startPressureBar,
                    endPressureBar = row.endPressureBar,
                    fromPod = row.fromPod,
                    series = null,
                )
            },
            lead = decode<List<DiveViewLeadItem>>(leadJson).orEmpty(),
            leadTotalKg = leadTotalKg,
            weightingFeedback = decode<DiveViewWeightingFeedback>(weightingFeedbackJson),
            suit = decode<DiveViewSuit>(suitJson),
            sac = sacLitresPerMinute?.let { DiveViewSac(it, sacBarPerMinute) },
            sacMissing = sacMissing,
            participants = emptyList(),
            verificationCodes = emptyList(),
        )
    }

    private fun Recording.toView(): DiveViewRecordingsItem = DiveViewRecordingsItem(
        id = id,
        isPrimary = isPrimary,
        device = decode(deviceJson),
        startsAt = startsAt,
        durationSeconds = durationSeconds.toDouble(),
        maxDepthM = maxDepthM,
        parser = parser,
        partOf = partOf,
        summary = summary() ?: DiveViewRecordingsItemSummary(),
        channels = decode<List<String>>(channelsJson).orEmpty(),
    )

    private fun Recording.summary(): DiveViewRecordingsItemSummary? =
        decode<DiveViewRecordingsItemSummary>(summaryJson)

    private inline fun <reified T> decode(text: String?): T? {
        if (text == null) return null
        return try {
            JSON.decodeFromString(text)
        } catch (_: Exception) {
            null
        }
    }
}
