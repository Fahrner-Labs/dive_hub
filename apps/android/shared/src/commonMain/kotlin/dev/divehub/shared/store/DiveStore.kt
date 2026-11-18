package dev.divehub.shared.store

import app.cash.sqldelight.db.SqlDriver
import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.store.db.Body_weight
import dev.divehub.shared.store.db.Cylinder as CylinderRow
import dev.divehub.shared.store.db.Dive as DiveRow
import dev.divehub.shared.store.db.DiveHubDatabase
import dev.divehub.shared.store.db.Dive_site
import dev.divehub.shared.store.db.Diver as DiverRow
import dev.divehub.shared.store.db.Recording as RecordingRow
import dev.divehub.shared.store.db.Recording_series
import dev.divehub.shared.store.db.Sync_checkpoint
import dev.divehub.shared.store.db.Sync_state

/**
 * The offline read store (ADR 0075): the single source for the UI's logbook reads.
 *
 * The facade wraps SQLDelight's generated queries in typed models and owns the transaction boundaries: a sync page or
 * a wipe is one transaction, so a crash never leaves half a page or half a wipe behind. It is synchronous by design;
 * the sync engine and the screens call it off the main thread.
 */
class DiveStore internal constructor(private val database: DiveHubDatabase) : UserScopedStore {
    private val queries = database.diveHubQueries

    fun dive(id: String): Dive? = queries.selectDiveById(id).executeAsOneOrNull()?.toModel()

    /** The logbook page, newest start first, without tombstones (the list's normal order). */
    fun dives(limit: Long = DEFAULT_PAGE_SIZE, offset: Long = 0): List<Dive> =
        queries.selectDivesPage(limit, offset).executeAsList().map(DiveRow::toModel)

    /** Every row, tombstones included: the sync's view, not the logbook's. */
    fun allDives(): List<Dive> = queries.selectAllDives().executeAsList().map(DiveRow::toModel)

    fun diveCount(): Long = queries.selectDiveCount().executeAsOne()

    fun activeDiveCount(): Long = queries.selectActiveDiveCount().executeAsOne()

    fun upsertDive(dive: Dive) {
        queries.upsertDive(
            id = dive.id,
            diverId = dive.diverId,
            version = dive.version,
            deletedAt = dive.deletedAt,
            startsAt = dive.startsAt,
            utcOffsetSeconds = dive.utcOffsetSeconds,
            utcOffsetSource = dive.utcOffsetSource,
            durationSeconds = dive.durationSeconds,
            maxDepthM = dive.maxDepthM,
            avgDepthM = dive.avgDepthM,
            siteId = dive.siteId,
            waterType = dive.waterType,
            fromProvider = dive.fromProvider,
            notes = dive.notes,
            suitJson = dive.suitJson,
            weightingFeedbackJson = dive.weightingFeedbackJson,
            leadJson = dive.leadJson,
            leadTotalKg = dive.leadTotalKg,
            sacLitresPerMinute = dive.sacLitresPerMinute,
            sacBarPerMinute = dive.sacBarPerMinute,
            sacMissing = dive.sacMissing,
            number = dive.number,
            findingsCount = dive.findingsCount,
            summaryJson = dive.summaryJson,
        )
    }

    fun recording(id: String): Recording? = queries.selectRecordingById(id).executeAsOneOrNull()?.toModel()

    fun recordings(diveId: String): List<Recording> =
        queries.selectRecordingsByDive(diveId).executeAsList().map(RecordingRow::toModel)

    fun allRecordings(): List<Recording> = queries.selectAllRecordings().executeAsList().map(RecordingRow::toModel)

    fun upsertRecording(recording: Recording) {
        queries.upsertRecording(
            id = recording.id,
            diveId = recording.diveId,
            isPrimary = recording.isPrimary.toLong(),
            deviceJson = recording.deviceJson,
            startsAt = recording.startsAt,
            durationSeconds = recording.durationSeconds,
            maxDepthM = recording.maxDepthM,
            parser = recording.parser,
            partOf = recording.partOf,
            summaryJson = recording.summaryJson,
            channelsJson = recording.channelsJson,
        )
    }

    fun series(recordingId: String): List<RecordingSeries> =
        queries.selectSeries(recordingId).executeAsList().map(Recording_series::toModel)

    fun series(recordingId: String, channel: String): RecordingSeries? =
        queries.selectSeriesFor(recordingId, channel).executeAsOneOrNull()?.toModel()

    fun allSeries(): List<RecordingSeries> = queries.selectAllSeries().executeAsList().map(Recording_series::toModel)

    fun upsertSeries(series: RecordingSeries) {
        queries.upsertSeries(
            recordingId = series.recordingId,
            channel = series.channel,
            offsetsMsJson = series.offsetsMsJson,
            valuesJson = series.valuesJson,
            fetchedAt = series.fetchedAt,
        )
    }

    fun cylinders(diveId: String): List<Cylinder> =
        queries.selectCylindersByDive(diveId).executeAsList().map(CylinderRow::toModel)

    fun allCylinders(): List<Cylinder> = queries.selectAllCylinders().executeAsList().map(CylinderRow::toModel)

    fun upsertCylinder(cylinder: Cylinder) {
        queries.upsertCylinder(
            diveId = cylinder.diveId,
            position = cylinder.position,
            volumeL = cylinder.volumeL,
            material = cylinder.material,
            o2 = cylinder.o2,
            he = cylinder.he,
            startPressureBar = cylinder.startPressureBar,
            endPressureBar = cylinder.endPressureBar,
            fromPod = cylinder.fromPod.toLong(),
            seriesJson = cylinder.seriesJson,
        )
    }

    fun diveSite(id: String): DiveSite? = queries.selectDiveSiteById(id).executeAsOneOrNull()?.toModel()

    fun diveSites(): List<DiveSite> = queries.selectAllDiveSites().executeAsList().map(Dive_site::toModel)

    fun upsertDiveSite(site: DiveSite) {
        queries.upsertDiveSite(
            id = site.id,
            version = site.version,
            name = site.name,
            waterType = site.waterType,
            positionJson = site.positionJson,
            countryCode = site.countryCode,
            mergedInto = site.mergedInto,
            payloadJson = site.payloadJson,
        )
    }

    fun diver(id: String): Diver? = queries.selectDiverById(id).executeAsOneOrNull()?.toModel()

    fun divers(): List<Diver> = queries.selectAllDivers().executeAsList().map(DiverRow::toModel)

    fun upsertDiver(diver: Diver) {
        queries.upsertDiver(
            id = diver.id,
            name = diver.name,
            isOwn = diver.isOwn.toLong(),
            external = diver.external.toLong(),
            apneaCounts = diver.apneaCounts.toLong(),
            divesBefore = diver.divesBefore,
            payloadJson = diver.payloadJson,
        )
    }

    fun bodyWeights(diverId: String): List<BodyWeight> =
        queries.selectBodyWeights(diverId).executeAsList().map(Body_weight::toModel)

    fun allBodyWeights(): List<BodyWeight> = queries.selectAllBodyWeights().executeAsList().map(Body_weight::toModel)

    fun upsertBodyWeight(weight: BodyWeight) {
        queries.upsertBodyWeight(weight.diverId, weight.date, weight.kg)
    }

    fun checkpoint(entity: String, phase: String): SyncCheckpoint? =
        queries.selectCheckpoint(entity, phase).executeAsOneOrNull()?.toModel()

    fun checkpoints(): List<SyncCheckpoint> =
        queries.selectAllCheckpoints().executeAsList().map(Sync_checkpoint::toModel)

    fun saveCheckpoint(checkpoint: SyncCheckpoint) {
        queries.upsertCheckpoint(
            entity = checkpoint.entity,
            phase = checkpoint.phase,
            cursor = checkpoint.cursor,
            pageOffset = checkpoint.pageOffset,
            done = checkpoint.done.toLong(),
            updatedAt = checkpoint.updatedAt,
        )
    }

    /** The singleton state, or the schema version with no sync recorded when no sync has written it yet. */
    fun syncState(): SyncState = queries.selectSyncState().executeAsOneOrNull()?.toModel()
        ?: SyncState(schemaVersion = schemaVersion)

    fun saveSyncState(state: SyncState) {
        queries.upsertSyncState(state.lastFullSyncAt, state.lastHintAt, state.schemaVersion)
    }

    /**
     * Forget everything cached for a User: sign-in as someone else and sign-out must leave nothing of the previous
     * person (client contract §1). The schema version stays; the next sync starts from scratch.
     */
    fun wipe() {
        database.transaction {
            queries.deleteAllDives()
            queries.deleteAllRecordings()
            queries.deleteAllSeries()
            queries.deleteAllCylinders()
            queries.deleteAllDiveSites()
            queries.deleteAllDivers()
            queries.deleteAllBodyWeights()
            queries.deleteAllCheckpoints()
            queries.deleteAllSyncState()
            queries.upsertSyncState(lastFullSyncAt = null, lastHintAt = null, schemaVersion = schemaVersion)
        }
    }

    override suspend fun forget() = wipe()

    companion object {
        /** The API's list page for the offline meta phase ([ADR 0017]); the store's default page mirrors it. */
        const val DEFAULT_PAGE_SIZE = 200L

        val schemaVersion: Long get() = DiveHubDatabase.Schema.version

        /** Open an existing store. Creating or migrating the schema is the driver owner's job, as in `app/`. */
        fun open(driver: SqlDriver): DiveStore = DiveStore(DiveHubDatabase(driver))

        fun create(driver: SqlDriver): DiveStore {
            DiveHubDatabase.Schema.create(driver)
            return open(driver)
        }

        fun migrate(driver: SqlDriver, oldVersion: Long, newVersion: Long = schemaVersion) {
            DiveHubDatabase.Schema.migrate(driver, oldVersion, newVersion)
        }
    }
}

private fun Long.toBoolean(): Boolean = this != 0L

private fun Boolean.toLong(): Long = if (this) 1L else 0L

private fun DiveRow.toModel() = Dive(
    id = id,
    diverId = diverId,
    version = version,
    deletedAt = deletedAt,
    startsAt = startsAt,
    utcOffsetSeconds = utcOffsetSeconds,
    utcOffsetSource = utcOffsetSource,
    durationSeconds = durationSeconds,
    maxDepthM = maxDepthM,
    avgDepthM = avgDepthM,
    siteId = siteId,
    waterType = waterType,
    fromProvider = fromProvider,
    notes = notes,
    suitJson = suitJson,
    weightingFeedbackJson = weightingFeedbackJson,
    leadJson = leadJson,
    leadTotalKg = leadTotalKg,
    sacLitresPerMinute = sacLitresPerMinute,
    sacBarPerMinute = sacBarPerMinute,
    sacMissing = sacMissing,
    number = number,
    findingsCount = findingsCount,
    summaryJson = summaryJson,
)

private fun RecordingRow.toModel() = Recording(
    id = id,
    diveId = diveId,
    isPrimary = isPrimary.toBoolean(),
    deviceJson = deviceJson,
    startsAt = startsAt,
    durationSeconds = durationSeconds,
    maxDepthM = maxDepthM,
    parser = parser,
    partOf = partOf,
    summaryJson = summaryJson,
    channelsJson = channelsJson,
)

private fun Recording_series.toModel() = RecordingSeries(
    recordingId = recordingId,
    channel = channel,
    offsetsMsJson = offsetsMsJson,
    valuesJson = valuesJson,
    fetchedAt = fetchedAt,
)

private fun CylinderRow.toModel() = Cylinder(
    diveId = diveId,
    position = position,
    volumeL = volumeL,
    material = material,
    o2 = o2,
    he = he,
    startPressureBar = startPressureBar,
    endPressureBar = endPressureBar,
    fromPod = fromPod.toBoolean(),
    seriesJson = seriesJson,
)

private fun Dive_site.toModel() = DiveSite(
    id = id,
    version = version,
    name = name,
    waterType = waterType,
    positionJson = positionJson,
    countryCode = countryCode,
    mergedInto = mergedInto,
    payloadJson = payloadJson,
)

private fun DiverRow.toModel() = Diver(
    id = id,
    name = name,
    isOwn = isOwn.toBoolean(),
    external = external_.toBoolean(),
    apneaCounts = apneaCounts.toBoolean(),
    divesBefore = divesBefore,
    payloadJson = payloadJson,
)

private fun Body_weight.toModel() = BodyWeight(diverId = diverId, date = date, kg = kg)

private fun Sync_checkpoint.toModel() = SyncCheckpoint(
    entity = entity,
    phase = phase,
    cursor = cursor,
    pageOffset = pageOffset,
    done = done.toBoolean(),
    updatedAt = updatedAt,
)

private fun Sync_state.toModel() = SyncState(
    lastFullSyncAt = lastFullSyncAt,
    lastHintAt = lastHintAt,
    schemaVersion = schemaVersion,
)
