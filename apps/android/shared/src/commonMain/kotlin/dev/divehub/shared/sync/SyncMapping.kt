package dev.divehub.shared.sync

import dev.divehub.api.models.ApiDiverBodyWeightsGet200ResponseItem
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemGasesItem
import dev.divehub.api.models.DiveListViewDivesItemParticipantsItem
import dev.divehub.api.models.DiveListViewDivesItemSite
import dev.divehub.api.models.DiverView
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.SiteView
import dev.divehub.shared.store.BodyWeight
import dev.divehub.shared.store.Cylinder
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.Diver
import dev.divehub.shared.store.Recording
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

/**
 * The API's values into the store's rows (ADR 0075). The generated models are the contract: complex values keep
 * their JSON unchanged, so a later read decodes them with the same models instead of a second hand-written shape.
 */
internal fun DiverView.toStore(): Diver = Diver(
    id = id,
    name = name,
    isOwn = isOwn,
    external = false,
    apneaCounts = apneaCounts,
    divesBefore = divesBefore.toLong(),
    mergedInto = null,
    deletedAt = null,
    payloadJson = JSON.encodeToString(this),
)

internal fun SiteView.toStore(): DiveSite = DiveSite(
    id = id,
    version = version.toLong(),
    name = name,
    waterType = waterType,
    positionJson = position?.let { JSON.encodeToString(it) },
    countryCode = country,
    mergedInto = mergedInto,
    deletedAt = null,
    payloadJson = JSON.encodeToString(this),
)

internal fun ApiDiverBodyWeightsGet200ResponseItem.toStore(diverId: String): BodyWeight =
    BodyWeight(diverId = diverId, date = date, kg = kg)

/**
 * The list pass: what `GET /api/dives` says, before the detail pass fills the rest.
 *
 * A refresh re-reads the list after the detail pass has run. When the row is already detailed, its detail-only fields
 * must survive: `version` is null in a list row, so a wholesale list write would erase the row's detail. The list's
 * current summary fields (number, times, findings, site) still win, because that is what the logbook shows.
 */
internal fun DiveListViewDivesItem.toStore(existing: Dive?): Dive {
    if (existing?.version != null) {
        return existing.copy(
            // A live list row means the Dive is back (a restore): its tombstone must not outlive the server state.
            deletedAt = null,
            diverId = diverId,
            startsAt = startsAt,
            utcOffsetSeconds = utcOffsetSeconds?.toLong() ?: existing.utcOffsetSeconds,
            utcOffsetSource = utcOffsetSource,
            durationSeconds = durationSeconds.toLong(),
            maxDepthM = maxDepthM,
            avgDepthM = avgDepthM,
            siteId = site?.id,
            fromProvider = fromProvider,
            number = number?.toLong(),
            findingsCount = findings.toLong(),
            listJson = JSON.encodeToString(this),
        )
    }
    return Dive(
        id = id,
        diverId = diverId,
        version = null,
        deletedAt = null,
        startsAt = startsAt,
        utcOffsetSeconds = utcOffsetSeconds?.toLong(),
        utcOffsetSource = utcOffsetSource,
        durationSeconds = durationSeconds.toLong(),
        maxDepthM = maxDepthM,
        avgDepthM = avgDepthM,
        siteId = site?.id,
        waterType = null,
        fromProvider = fromProvider,
        notes = null,
        suitJson = null,
        weightingFeedbackJson = null,
        leadJson = null,
        leadTotalKg = null,
        sacLitresPerMinute = null,
        sacBarPerMinute = null,
        sacMissing = null,
        number = number?.toLong(),
        findingsCount = findings.toLong(),
        summaryJson = null,
        listJson = JSON.encodeToString(this),
    )
}

/** The detail pass: a wholesale replacement, keeping what only the list pass carried (the findings count). */
internal fun DiveView.toStore(existing: Dive?): Dive = Dive(
    id = id,
    diverId = diverId,
    version = version.toLong(),
    // A detail is only readable while the Dive is live: a successful fetch clears any tombstone.
    deletedAt = null,
    startsAt = values.startsAt.at,
    utcOffsetSeconds = values.startsAt.utcOffsetSeconds?.toLong() ?: existing?.utcOffsetSeconds,
    utcOffsetSource = utcOffsetSource,
    durationSeconds = values.durationSeconds.toLong(),
    maxDepthM = values.maxDepthM,
    avgDepthM = values.avgDepthM,
    siteId = site?.id,
    waterType = waterType,
    fromProvider = fromProvider,
    notes = notes,
    suitJson = suit?.let { JSON.encodeToString(it) },
    weightingFeedbackJson = weightingFeedback?.let { JSON.encodeToString(it) },
    leadJson = JSON.encodeToString(lead),
    leadTotalKg = leadTotalKg,
    sacLitresPerMinute = sac?.litresPerMinute,
    sacBarPerMinute = sac?.barPerMinute,
    sacMissing = sacMissing,
    number = values.number?.toLong(),
    findingsCount = existing?.findingsCount,
    summaryJson = existing?.summaryJson,
    listJson = existing?.listJson,
    detailJson = JSON.encodeToString(this),
)

/**
 * A Dive hint's detail into the row the logbook list shows, keeping the list-only values the detail does not carry
 * (the findings mark, the profile sketch and Waiting pushes). The next list refresh replaces it anyway.
 */
internal fun DiveView.toListRow(existing: String?): String {
    val old = existing?.let {
        try {
            JSON.decodeFromString(DiveListViewDivesItem.serializer(), it)
        } catch (_: Exception) {
            null
        }
    }
    val primary = recordings.firstOrNull { it.isPrimary } ?: recordings.firstOrNull()
    val row = DiveListViewDivesItem(
        id = id,
        diverId = diverId,
        number = values.number,
        startsAt = values.startsAt.at,
        utcOffsetSeconds = values.startsAt.utcOffsetSeconds,
        utcOffsetSource = utcOffsetSource,
        durationSeconds = values.durationSeconds,
        maxDepthM = values.maxDepthM,
        avgDepthM = values.avgDepthM,
        site = site?.let { DiveListViewDivesItemSite(id = it.id, name = it.name) },
        findings = old?.findings ?: 0,
        recordings = recordings.size,
        fromProvider = fromProvider,
        gases = primary?.summary?.gases.orEmpty().map {
            DiveListViewDivesItemGasesItem(o2 = it.o2, he = it.he)
        },
        surfaceIntervalSeconds = primary?.summary?.surfaceIntervalSeconds,
        participants = participants.map {
            DiveListViewDivesItemParticipantsItem(diverId = it.diverId, name = it.name, role = it.role)
        },
        waiting = old?.waiting.orEmpty(),
        profile = old?.profile,
    )
    return JSON.encodeToString(DiveListViewDivesItem.serializer(), row)
}

internal fun DiveViewRecordingsItem.toStore(diveId: String): Recording = Recording(
    id = id,
    diveId = diveId,
    isPrimary = isPrimary,
    deviceJson = device?.let { JSON.encodeToString(it) },
    startsAt = startsAt,
    durationSeconds = durationSeconds.toLong(),
    maxDepthM = maxDepthM,
    parser = parser,
    partOf = partOf,
    deletedAt = null,
    summaryJson = JSON.encodeToString(summary),
    channelsJson = JSON.encodeToString(channels),
)

internal fun DiveViewCylindersItem.toStore(diveId: String, position: Long): Cylinder = Cylinder(
    diveId = diveId,
    position = position,
    volumeL = volumeL,
    material = material,
    o2 = gas?.o2,
    he = gas?.he,
    startPressureBar = startPressureBar,
    endPressureBar = endPressureBar,
    fromPod = fromPod,
    seriesJson = series?.let { JSON.encodeToString(it) },
)
