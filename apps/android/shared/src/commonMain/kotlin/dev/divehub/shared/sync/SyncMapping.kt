package dev.divehub.shared.sync

import dev.divehub.api.models.ApiDiverBodyWeightsGet200ResponseItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItem
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
internal fun ApiDivesGet200ResponseDivesItem.toStore(existing: Dive?): Dive {
    if (existing?.version != null) {
        return existing.copy(
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
        )
    }
    return Dive(
        id = id,
        diverId = diverId,
        version = null,
        deletedAt = existing?.deletedAt,
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
    )
}

/** The detail pass: a wholesale replacement, keeping what only the list pass carried (the findings count). */
internal fun DiveView.toStore(existing: Dive?): Dive = Dive(
    id = id,
    diverId = diverId,
    version = version.toLong(),
    deletedAt = existing?.deletedAt,
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
)

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
