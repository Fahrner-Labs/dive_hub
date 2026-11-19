// The personalized tools' answers (FAH-276; ADR 0031, ADR 0071), shaped exactly like the API's service shells: the
// lead estimate's range, basis, adjustments, missing, reserve, leftOut and weight check; the loadings' basis, the
// missing-vs-zero distinction and DAN's no-fly source. The inputs come from [assemblePersonalizedSources].
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
import dev.divehub.shared.planning.CNS_HALF_LIFE_MINUTES
import dev.divehub.shared.planning.EstimateAdjustment
import dev.divehub.shared.planning.EstimateBasis
import dev.divehub.shared.planning.EstimateInput
import dev.divehub.shared.planning.EstimateMissing
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.N2Loadings
import dev.divehub.shared.planning.PlannedConditions
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.cnsDecayed
import dev.divehub.shared.planning.estimateLead
import dev.divehub.shared.planning.isoUtc
import dev.divehub.shared.planning.noFlyAfter
import dev.divehub.shared.planning.otuLast24h
import dev.divehub.shared.planning.roundTo
import dev.divehub.shared.planning.tissuesAfterDives
import kotlin.time.Instant

/** The weight check the route always answers with (ADR 0031); the app translates the codes. */
val WEIGHT_CHECK_STEPS = listOf("eye_level_empty_bc", "neutral_at_5m_at_reserve")

/** DAN's guideline beside the numbers; information, not a countdown (ADR 0036). */
private val NO_FLY_SOURCE = ApiDiversIdLoadingsGet200ResponseNoFlySource(
    title = "DAN 2002: guidelines for flying after diving",
    url = "https://dan.org/health-medicine/health-resource/health-safety-guidelines/guidelines-for-flying-after-diving/",
)

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
    plan.suit.thicknessMm?.let { if (it < 0.0 || it > MAX_SUIT_THICKNESS_MM) throw ToolsError(ToolsError.INVALID_INPUT) }
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
const val MAX_SUIT_THICKNESS_MM = 15.0

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
