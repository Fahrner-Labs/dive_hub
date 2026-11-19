// The personalized offline tools (FAH-276; ADR 0031, ADR 0071): the lead estimate and the current loadings, computed
// from the synced store only — never from the API. The store read assembles the same plain data the server's service
// shells read from its database; the kernels do the maths and these functions shape the API's answer exactly, including
// the weight check, `leftOut`, the reserve, the no-fly source and the missing-vs-zero distinction.
package dev.divehub.shared.tools

import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200Response
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseBasis
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseLeftOut
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseMissingItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseRange
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseReserve
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseWeightCheck
import dev.divehub.api.models.ApiDiversIdLoadingsGet200Response
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseBasis
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseCns
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseN2
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseNoFly
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseNoFlySource
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseOtu
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewSuit
import dev.divehub.api.models.DiveViewWeightingFeedback
import dev.divehub.shared.planning.CNS_HALF_LIFE_MINUTES
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.EstimateBasis
import dev.divehub.shared.planning.EstimateDive
import dev.divehub.shared.planning.EstimateDiveCylinder
import dev.divehub.shared.planning.EstimateAdjustment
import dev.divehub.shared.planning.EstimateInput
import dev.divehub.shared.planning.EstimateMissing
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.LoadingDive
import dev.divehub.shared.planning.N2Loadings
import dev.divehub.shared.planning.N2_REPLAY_WINDOW_HOURS
import dev.divehub.shared.planning.PlannedConditions
import dev.divehub.shared.planning.SeriesDive
import dev.divehub.shared.planning.SeriesDiveInput
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.Suit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import dev.divehub.shared.planning.TissuePoint
import dev.divehub.shared.planning.WeightingFeedback
import dev.divehub.shared.planning.WeightingFelt
import dev.divehub.shared.planning.cnsDecayed
import dev.divehub.shared.planning.enteredDecoFromNdl
import dev.divehub.shared.planning.estimateLead
import dev.divehub.shared.planning.isoUtc
import dev.divehub.shared.planning.noFlyAfter
import dev.divehub.shared.planning.otuLast24h
import dev.divehub.shared.planning.roundTo
import dev.divehub.shared.planning.seriesDiveOf
import dev.divehub.shared.planning.seriesRulesApply
import dev.divehub.shared.planning.tissuesAfterDives
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlin.time.Instant

private const val HOUR_MS: Long = 3_600_000
private const val RECENT_RECORDINGS = 100
private val SUMMARY = Json { ignoreUnknownKeys = true }
private val JSON = Json { ignoreUnknownKeys = true }

/** The weight check the route always answers with (ADR 0031); the app translates the codes. */
val WEIGHT_CHECK_STEPS = listOf("eye_level_empty_bc", "neutral_at_5m_at_reserve")

/** DAN's guideline beside the numbers; information, not a countdown (ADR 0036). */
private val NO_FLY_SOURCE = ApiDiversIdLoadingsGet200ResponseNoFlySource(
    title = "DAN 2002: guidelines for flying after diving",
    url = "https://dan.org/health-medicine/health-resource/health-safety-guidelines/guidelines-for-flying-after-diving/",
)

/** A Dive with the link data the answer names beside each dive the estimate rests on. */
data class LeadDiveSource(
    val id: String,
    val startsAt: String,
    val number: Long?,
    val dive: EstimateDive,
)

/** The latest Recording's CNS, decayed from the end of its Dive (ADR 0071). */
data class CnsSource(val cnsEnd: Double, val endMs: Long)

/** What the loadings need: the replay's dives, the series rules' dives, the GF high and the CNS. */
data class LoadingsSources(
    val replayDives: List<LoadingDive>,
    val seriesDives: List<SeriesDive>,
    val latestDiveId: String?,
    val gfHigh: Double,
    val cns: CnsSource?,
)

/**
 * The synced store's values for the personalized tools. Everything is plain data so the service functions can be
 * exercised without a database; [assemblePersonalizedSources] is the only reader.
 */
data class PersonalizedSources(
    val ownDiverId: String? = null,
    /** Every non-deleted Dive of the Diver, the lead estimate's input. */
    val leadDives: List<LeadDiveSource> = emptyList(),
    /** The Diver's latest body weight in kg; null when none is set. */
    val bodyWeightKg: Double? = null,
    /** Null without an own Diver. */
    val loadings: LoadingsSources? = null,
    val syncedAt: String? = null,
    /** Whether every synced Recording's listed channels are in the store (the profiles phase, FAH-273). */
    val profilesComplete: Boolean = true,
    /** No Dive has synced yet: an empty state, not an error. */
    val storeEmpty: Boolean = true,
)

/**
 * Reads the Diver's own values in one transaction, so a concurrent sync cannot mix two snapshots. Every read is the
 * server's: the site's water type (never the computer's), the latest body weight, the dives that ended in the window,
 * the primary Recording's depth samples and NDL series, and the summaries for GF high, CNS and OTU.
 */
fun assemblePersonalizedSources(store: DiveStore, nowMs: Long): PersonalizedSources = store.transaction {
    val divers = store.divers()
    val own = divers.firstOrNull { it.isOwn }
    val dives = store.allDives().filter { it.deletedAt == null }
    val syncedAt = store.syncState().lastFullSyncAt
    val profilesComplete = profilesComplete(store, dives)
    if (own == null) {
        return@transaction PersonalizedSources(
            syncedAt = syncedAt,
            profilesComplete = profilesComplete,
            storeEmpty = dives.isEmpty(),
        )
    }

    val ownDives = dives.filter { it.diverId == own.id }
    val diveIds = ownDives.mapTo(HashSet()) { it.id }
    val sites = store.allDiveSites().associateBy { it.id }
    val recordings = store.allRecordings()
        .filter { it.deletedAt == null && it.diveId in diveIds }
        .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
    val recordingsByDive = recordings.groupBy { it.diveId }
    val seriesByRecording = store.allSeries().groupBy { it.recordingId }
    val cylindersByDive = store.allCylinders().filter { it.diveId in diveIds }.groupBy { it.diveId }

    fun selectedRecording(diveId: String): Recording? {
        val list = recordingsByDive[diveId].orEmpty()
        return list.firstOrNull { it.isPrimary } ?: list.firstOrNull()
    }

    fun summaryOf(recording: Recording?): DiveViewRecordingsItemSummary? = recording?.let { found ->
        try {
            SUMMARY.decodeFromString(DiveViewRecordingsItemSummary.serializer(), found.summaryJson)
        } catch (_: Exception) {
            null
        }
    }

    val leadDives = ownDives.mapNotNull { dive ->
        val date = epochMs(dive.startsAt)?.let { Instant.fromEpochMilliseconds(it) } ?: return@mapNotNull null
        val siteWater = dive.siteId?.let { sites[it]?.waterType }
        val cylinders = cylindersByDive[dive.id].orEmpty().mapNotNull { cylinder ->
            val volume = cylinder.volumeL ?: return@mapNotNull null
            EstimateDiveCylinder(
                volumeL = volume,
                material = materialOf(cylinder.material),
                endPressureBar = cylinder.endPressureBar,
            )
        }
        LeadDiveSource(
            id = dive.id,
            startsAt = dive.startsAt,
            number = dive.number,
            dive = EstimateDive(
                id = dive.id,
                date = date,
                leadKg = dive.leadTotalKg,
                feedback = feedbackOf(dive.weightingFeedbackJson),
                suit = suitOf(dive.suitJson),
                water = waterOf(siteWater),
                cylinders = cylinders,
            ),
        )
    }

    val bodyWeightKg = store.bodyWeights(own.id).maxByOrNull { it.date }?.kg

    val ended = ownDives.mapNotNull { dive ->
        val start = epochMs(dive.startsAt) ?: return@mapNotNull null
        dive to (start + (dive.durationSeconds ?: 0L) * 1000)
    }.filter { (_, end) -> end <= nowMs }
    val latest = ended.maxByOrNull { (_, end) -> end }?.first
    val window = ended.filter { (_, end) -> nowMs - end <= N2_REPLAY_WINDOW_HOURS * HOUR_MS }
    val replayDives = window
        .sortedBy { (dive, _) -> epochMs(dive.startsAt) ?: Long.MIN_VALUE }
        .mapNotNull { (dive, end) ->
            val recording = selectedRecording(dive.id)
            val summary = summaryOf(recording)
            val start = epochMs(dive.startsAt) ?: return@mapNotNull null
            val depth = seriesByRecording[recording?.id].orEmpty().firstOrNull { it.channel == "depth" }
            LoadingDive(
                startMs = start,
                endMs = end,
                samples = depthPoints(depth),
                gas = gasOf(cylindersByDive[dive.id].orEmpty().firstOrNull { it.position == 0L }),
                otuStart = summary?.otuStart,
                otuEnd = summary?.otuEnd,
            )
        }.toMutableList()
    if (latest != null && window.none { (dive, _) -> dive.id == latest.id }) {
        val start = epochMs(latest.startsAt)
        if (start != null) {
            replayDives.add(
                LoadingDive(startMs = start, endMs = start + (latest.durationSeconds ?: 0L) * 1000, samples = emptyList()),
            )
        }
    }

    val seriesDives = ownDives.mapNotNull { dive ->
        val recording = selectedRecording(dive.id)
        val summary = summaryOf(recording)
        if (!seriesRulesApply(summary?.diveMode)) return@mapNotNull null
        val start = epochMs(dive.startsAt) ?: return@mapNotNull null
        val ndl = seriesByRecording[recording?.id].orEmpty().firstOrNull { it.channel == "ndl" }
        seriesDiveOf(
            SeriesDiveInput(
                id = dive.id,
                startMs = start,
                utcOffsetSeconds = dive.utcOffsetSeconds?.toInt(),
                durationSeconds = (dive.durationSeconds ?: 0L).toDouble(),
                maxDepthM = dive.maxDepthM,
            ),
            enteredDeco = enteredDecoFromNdl(seriesValues(ndl)),
        )
    }

    val gfHigh = recordings.take(RECENT_RECORDINGS).asSequence()
        .mapNotNull { summaryOf(it)?.gfHigh }
        .firstOrNull() ?: DEFAULT_GF_HIGH
    val cnsRecording = recordings.take(RECENT_RECORDINGS).firstOrNull { summaryOf(it)?.cnsEnd != null }
    val cnsSummary = summaryOf(cnsRecording)
    val cnsDive = cnsRecording?.let { found -> ownDives.firstOrNull { it.id == found.diveId } }
    val cnsEnd = cnsSummary?.cnsEnd
    val cns = if (cnsEnd != null && cnsDive != null) {
        val start = epochMs(cnsDive.startsAt)
        if (start != null) CnsSource(cnsEnd, start + (cnsDive.durationSeconds ?: 0L) * 1000) else null
    } else {
        null
    }

    PersonalizedSources(
        ownDiverId = own.id,
        leadDives = leadDives,
        bodyWeightKg = bodyWeightKg,
        loadings = LoadingsSources(
            replayDives = replayDives,
            seriesDives = seriesDives,
            latestDiveId = latest?.id,
            gfHigh = gfHigh,
            cns = cns,
        ),
        syncedAt = syncedAt,
        profilesComplete = profilesComplete,
        storeEmpty = dives.isEmpty(),
    )
}

/**
 * The lead estimate, shaped exactly like `GET /api/divers/{id}/lead-estimate`: the kernel's answer plus the weight
 * check and the Dives left out for a site without a water type. A thickness on a suit without one or an undergarment
 * under anything but a drysuit is refused as `suit_invalid`, as the route does.
 */
fun leadEstimate(
    plan: PlannedConditions,
    unit: LeadUnit,
    sources: PersonalizedSources,
    today: Instant,
): ApiDiversIdLeadEstimateGet200Response {
    if (plan.cylinders.isEmpty() || plan.cylinders.size > MAX_PLAN_CYLINDERS ||
        plan.cylinders.any { it.volumeL <= 0.0 || it.volumeL > MAX_CYLINDER_LITRES }
    ) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    if ((plan.suit.thicknessMm != null && plan.suit.type != SuitType.wetsuit && plan.suit.type != SuitType.semidry) ||
        (plan.suit.undergarment != null && plan.suit.type != SuitType.drysuit)
    ) {
        throw ToolsError(ToolsError.SUIT_INVALID)
    }
    val estimate = estimateLead(
        EstimateInput(
            dives = sources.leadDives.map { it.dive },
            bodyWeightKg = sources.bodyWeightKg,
            plan = plan,
            unit = unit,
            today = today,
        ),
    )
    val refs = sources.leadDives.associateBy { it.id }
    val basis = estimate.basis?.let { found ->
        when (found) {
            is EstimateBasis.Dives -> ApiDiversIdLeadEstimateGet200ResponseBasis(
                kind = "dives",
                dives = found.dives.map { use ->
                    val ref = refs.getValue(use.id)
                    ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem(
                        diveId = use.id,
                        startsAt = ref.startsAt,
                        number = ref.number?.toInt(),
                        carried = use.carried,
                        needed = use.needed,
                        weight = use.weight,
                    )
                },
                personal = found.personal,
            )
            is EstimateBasis.RuleOfThumb -> ApiDiversIdLeadEstimateGet200ResponseBasis(
                kind = "rule_of_thumb",
                rule = found.rule,
                personalOffset = found.personalOffset,
                offsetFromSuits = found.offsetFromSuits,
            )
        }
    }
    return ApiDiversIdLeadEstimateGet200Response(
        unit = if (unit == LeadUnit.lb) "lb" else "kg",
        total = estimate.total,
        range = estimate.range?.let { ApiDiversIdLeadEstimateGet200ResponseRange(it.low, it.high) },
        confidence = estimate.confidence.name,
        basis = basis,
        adjustments = estimate.adjustments.map { adjustment ->
            when (adjustment) {
                is EstimateAdjustment.Cylinder ->
                    ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(kind = "cylinder", amount = adjustment.amount)
                is EstimateAdjustment.Water ->
                    ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(
                        kind = "water",
                        amount = adjustment.amount,
                        water = adjustment.water.name,
                    )
                is EstimateAdjustment.PersonalOffset ->
                    ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(kind = "personal_offset", amount = adjustment.amount)
            }
        },
        missing = estimate.missing.map { missing -> missingOf(missing) },
        reserve = ApiDiversIdLeadEstimateGet200ResponseReserve(estimate.reserve.bar, estimate.reserve.fromDives),
        leftOut = ApiDiversIdLeadEstimateGet200ResponseLeftOut(
            noWaterType = sources.leadDives.count { it.dive.leadKg != null && it.dive.suit != null && it.dive.water == null },
        ),
        weightCheck = ApiDiversIdLeadEstimateGet200ResponseWeightCheck(
            steps = WEIGHT_CHECK_STEPS,
            reserveBar = estimate.reserve.bar,
        ),
    )
}

/**
 * The current loadings, shaped exactly like `GET /api/divers/{id}/loadings`: N₂ only when the replay could estimate it,
 * CNS from the latest Recording (a zero is a real zero), OTU over the last 24 h, and DAN's no-fly time as information.
 */
fun currentLoadings(sources: PersonalizedSources, nowMs: Long): ApiDiversIdLoadingsGet200Response? {
    val loadings = sources.loadings ?: return null
    val n2 = tissuesAfterDives(loadings.replayDives, nowMs, loadings.gfHigh)
    val estimate = n2 as? N2Loadings.Estimate
    val cns = loadings.cns?.let { found ->
        ApiDiversIdLoadingsGet200ResponseCns(
            percentNow = roundTo(cnsDecayed(found.cnsEnd, (nowMs - found.endMs) / 60_000.0), 1),
            percentAtLastDiveEnd = roundTo(found.cnsEnd, 1),
            halfLifeMinutes = CNS_HALF_LIFE_MINUTES,
        )
    }
    val otu = otuLast24h(loadings.replayDives, nowMs)
    val noFly = loadings.latestDiveId?.let { latest -> noFlyAfter(loadings.seriesDives, latest) }
    return ApiDiversIdLoadingsGet200Response(
        computedAt = isoUtc(nowMs),
        basis = ApiDiversIdLoadingsGet200ResponseBasis(
            windowHours = n2.basis.windowHours,
            divesUsed = n2.basis.divesUsed,
            divesSkippedNoProfile = n2.basis.divesSkippedNoProfile,
            lastDiveEndedAt = n2.basis.lastDiveEndedAt,
        ),
        n2 = estimate?.let {
            ApiDiversIdLoadingsGet200ResponseN2(
                worstCompartmentPercent = roundTo(it.worstCompartmentPercent, 1),
                gfHigh = it.gfHigh,
            )
        },
        cns = cns,
        otu = otu?.let { ApiDiversIdLoadingsGet200ResponseOtu(roundTo(it, 1)) },
        noFly = noFly?.let {
            ApiDiversIdLoadingsGet200ResponseNoFly(
                hours = it.hours,
                reason = it.reason.name,
                until = it.until,
                source = NO_FLY_SOURCE,
            )
        },
    )
}

/** What the route's query accepts for the planned Cylinders (ADR 0031). */
const val MAX_PLAN_CYLINDERS = 4
const val MAX_CYLINDER_LITRES = 40.0

private fun missingOf(missing: EstimateMissing): ApiDiversIdLeadEstimateGet200ResponseMissingItem = when (missing) {
    EstimateMissing.BodyWeight -> ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "body_weight")
    is EstimateMissing.CylinderMaterial -> ApiDiversIdLeadEstimateGet200ResponseMissingItem(
        kind = "cylinder_material",
        diveIds = missing.diveIds,
        planned = missing.planned,
    )
    is EstimateMissing.Cylinder -> ApiDiversIdLeadEstimateGet200ResponseMissingItem(
        kind = "cylinder",
        diveIds = missing.diveIds,
    )
    EstimateMissing.SuitThickness -> ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "suit_thickness")
    EstimateMissing.SuitUndergarment -> ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "suit_undergarment")
}

private fun profilesComplete(store: DiveStore, dives: List<Dive>): Boolean {
    val diveIds = dives.mapTo(HashSet()) { it.id }
    val recordings = store.allRecordings().filter { it.deletedAt == null && it.diveId in diveIds }
    if (recordings.isEmpty()) return true
    val seriesByRecording = store.allSeries().groupBy { it.recordingId }
    return recordings.all { recording ->
        val channels = channelsOf(recording.channelsJson)
        channels.isEmpty() || channels.all { channel ->
            seriesByRecording[recording.id].orEmpty().any { it.channel == channel }
        }
    }
}

private fun channelsOf(json: String): List<String> = try {
    JSON.decodeFromString(json)
} catch (_: Exception) {
    emptyList()
}

private fun summaryOf(json: String): DiveViewRecordingsItemSummary? = try {
    SUMMARY.decodeFromString(DiveViewRecordingsItemSummary.serializer(), json)
} catch (_: Exception) {
    null
}

private fun summaryOf(recording: Recording?): DiveViewRecordingsItemSummary? =
    recording?.let { summaryOf(it.summaryJson) }

private fun depthPoints(series: RecordingSeries?): List<TissuePoint> {
    if (series == null) return emptyList()
    val offsets: List<Int> = try {
        JSON.decodeFromString(series.offsetsMsJson)
    } catch (_: Exception) {
        return emptyList()
    }
    val values: List<Double> = try {
        JSON.decodeFromString(series.valuesJson)
    } catch (_: Exception) {
        return emptyList()
    }
    return offsets.mapIndexedNotNull { index, ms -> values.getOrNull(index)?.let { TissuePoint(ms / 1000.0, it) } }
}

private fun seriesValues(series: RecordingSeries?): List<Double> {
    if (series == null) return emptyList()
    return try {
        JSON.decodeFromString(series.valuesJson)
    } catch (_: Exception) {
        emptyList()
    }
}

private fun gasOf(cylinder: dev.divehub.shared.store.Cylinder?): GasMix? {
    if (cylinder == null) return null
    val o2 = cylinder.o2 ?: return null
    val he = cylinder.he ?: return null
    return GasMix(o2, he)
}

private fun materialOf(name: String?): CylinderMaterial? = when (name) {
    "aluminium" -> CylinderMaterial.aluminium
    "steel" -> CylinderMaterial.steel
    "carbon" -> CylinderMaterial.carbon
    else -> null
}

private fun waterOf(name: String?): SiteWaterType? = when (name) {
    "fresh" -> SiteWaterType.fresh
    "salt" -> SiteWaterType.salt
    "brackish" -> SiteWaterType.brackish
    else -> null
}

private fun suitOf(json: String?): Suit? {
    if (json == null) return null
    val found = try {
        SUMMARY.decodeFromString(DiveViewSuit.serializer(), json)
    } catch (_: Exception) {
        return null
    }
    val type = when (found.type) {
        "none" -> SuitType.none
        "skin" -> SuitType.skin
        "wetsuit" -> SuitType.wetsuit
        "semidry" -> SuitType.semidry
        "drysuit" -> SuitType.drysuit
        else -> return null
    }
    return Suit(
        type = type,
        thicknessMm = found.thicknessMm,
        hood = hoodOf(found.hood),
        undergarment = when (found.undergarment) {
            "light" -> SuitUndergarment.light
            "medium" -> SuitUndergarment.medium
            "heavy" -> SuitUndergarment.heavy
            else -> null
        },
    )
}

private fun feedbackOf(json: String?): WeightingFeedback? {
    if (json == null) return null
    val found = try {
        SUMMARY.decodeFromString(DiveViewWeightingFeedback.serializer(), json)
    } catch (_: Exception) {
        return null
    }
    val felt = when (found.felt) {
        "right" -> WeightingFelt.right
        "too_heavy" -> WeightingFelt.too_heavy
        "too_light" -> WeightingFelt.too_light
        else -> return null
    }
    return WeightingFeedback(felt, found.amountKg)
}

private fun hoodOf(element: JsonElement?): Boolean? = when (element) {
    null, JsonNull -> null
    is JsonPrimitive -> element.booleanOrNull
    else -> null
}

private fun epochMs(text: String): Long? = try {
    Instant.parse(text).toEpochMilliseconds()
} catch (_: Exception) {
    null
}
