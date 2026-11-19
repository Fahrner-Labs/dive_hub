// The lead estimate, ported from apps/server/src/lead/estimate.ts (FAH-272; ADR 0031, slice 20a).
// One estimate from three kinds of evidence, as plain data in and out: physics (the cylinder at reserve and the
// water's density), the Diver's own Dives with the same suit set-up, and a rule of thumb for a first guess. Never
// certain: the answer always carries a range, its basis, each adjustment and what was missing.
// Design: docs/research/2026-10-06-weight-calculator.md ("Proposed estimator"). No Android imports.
package dev.divehub.shared.planning

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.time.Instant

// ---- Constants. Every number that is a judgement or unverified is named here, with its source, so a correction is one edit.

/** Reserve pressure when the Diver's dives log no end pressure (DAN: weigh for 5 m with 35-50 bar left). */
private const val DEFAULT_RESERVE_BAR = 50.0
/** Mass of air per litre at 1 bar, kg (1.225 g/L; Wikipedia, "Diving cylinder"). Nitrox and trimix differ by under 10 %. */
private const val GAS_KG_PER_LITRE_BAR = 0.001225
/** Water density in kg/m3: fresh and salt as in the vocabulary; brackish halfway (ADR 0031, "Still to check"). */
private val WATER_DENSITY_KG_M3 = mapOf(
    SiteWaterType.fresh to 1000.0,
    SiteWaterType.brackish to 1012.0,
    SiteWaterType.salt to 1025.0,
)
/**
 * Mass in air of everything a diver wears and carries but the lead and the body: cylinder, BC, regulator, suit. A
 * judgement (a steel 12 L alone is about 15 kg); it only scales the 2.5 % water term, so ±10 kg moves it by 0.25 kg.
 */
private const val GEAR_MASS_KG = 25.0
/** Used for the water term only when no body weight is set; the missing weight is reported. */
private const val FALLBACK_BODY_WEIGHT_KG = 75.0

/** A 1 kg correction when feedback says "too heavy" or "too light" without an amount (ADR 0031). */
private const val FEEDBACK_DEFAULT_KG = 1.0
/** Dive weights: felt right counts twice, and a dive loses half its weight every two years (ADR 0031). */
private const val WEIGHT_FELT_RIGHT = 2.0
private const val WEIGHT_OTHER = 1.0
private const val HALF_LIFE_YEARS = 2.0
private const val DAYS_PER_YEAR = 365.25

/**
 * Empty (no gas) buoyancy in kg of one cylinder by water volume, in seawater, positive floats. Points are the published
 * values of the research note's table (Wikipedia, "Diving cylinder", Luxfer and Faber datasheets) between zero and the
 * largest listed size; between points linear, beyond the last constant. Origin anchors keep small cylinders sensible.
 * A manifold's own mass is not counted.
 */
private val EMPTY_BUOYANCY_POINTS = mapOf(
    CylinderMaterial.aluminium to listOf(0.0 to 0.0, 9.0 to 1.8, 11.1 to 1.7, 13.0 to 1.4),
    CylinderMaterial.steel to listOf(0.0 to 0.0, 12.0 to -1.2, 15.0 to -1.4),
)
/** A single cylinder above this is a twin set (the catalogue gives twins as one volume): two cylinders of half. */
private const val TWIN_ABOVE_L = 20.0

private const val ROUND_KG = 0.5
private const val KG_PER_LB = 0.45359237

// Range and confidence: judgements, not statistics; the dives are few.
/** Half-width of the range, kg, floors by basis: the spread of the dives can widen it, never narrow it below this. */
private const val HALF_WIDTH_MANY_DIVES_KG = 1.0
private const val HALF_WIDTH_FEW_DIVES_KG = 1.5
private const val HALF_WIDTH_RULE_WITH_OFFSET_KG = 2.0
/** The rule of thumb is good to ±2-3 kg (research note), the upper end for a Diver with no history. */
private const val HALF_WIDTH_RULE_ALONE_KG = 3.0
/** "Few" is up to this many dives; "many" (high confidence) from this many. */
private const val FEW_DIVES = 2
private const val MANY_DIVES = 5
/** Converts a median absolute deviation to a standard deviation for normally spread values. */
private const val MAD_TO_SIGMA = 1.4826

// Rule of thumb (strategy 1). UNVERIFIED against PADI's printed table: the numbers are as seen through the open-source
// Lead app's code (quoting PADI's basic guideline), PADI's blog (2024) and DAN (2014), see the research note's table. A
// correction is an edit of this block. They assume salt water and an AL80 (below); no study validates any of them.
private const val RULE_PERCENT_THIN = 0.05 // 3 mm one-piece and thinner
private const val RULE_PERCENT_THICK = 0.1 // 5 mm and thicker, drysuits
private const val RULE_THIN_UP_TO_MM = 3.0
/** Extra kg for 7 mm and thicker with a hood (PADI: "7 mm + hood and boots: 10 % + 1.5-3 kg"; the low end, as the note's scenario). */
private const val RULE_THICK_FROM_MM = 7.0
private const val RULE_THICK_EXTRA_KG = 2.0
/** Assumed where a wetsuit's thickness was not logged: the middle band (reported as missing). */
private const val RULE_ASSUMED_THICKNESS_MM = 5.0
/** Swimsuit or skin: 0.5-2 kg (PADI), the middle. */
private const val RULE_SKIN_KG = 1.25
/** Drysuit, 10 % plus: light undergarment 1.5-3 kg, heavy 3-7 kg (PADI); the low end of light, the middle of heavy. */
private val RULE_DRYSUIT_EXTRA_KG = mapOf(
    SuitUndergarment.light to 2.0,
    SuitUndergarment.medium to 4.0,
    SuitUndergarment.heavy to 5.0,
)
/** The conditions the rule is quoted for. */
private val RULE_WATER = SiteWaterType.salt
private val RULE_CYLINDER = EstimateCylinder(11.1, CylinderMaterial.aluminium)

/** The unit the answer is rounded and given in: 0.5 kg, or whole pounds. */
enum class LeadUnit { kg, lb }

/** Where a Dive was dived; the names match the server's `SiteWaterType`. */
enum class SiteWaterType { fresh, salt, brackish }

/** The cylinder's material; carbon is not tabulated and is treated as unknown (as the server does). */
enum class CylinderMaterial { aluminium, steel, carbon }

enum class SuitType { none, skin, wetsuit, semidry, drysuit }

enum class SuitUndergarment { light, medium, heavy }

enum class WeightingFelt { right, too_heavy, too_light }

enum class Confidence { low, medium, high }

/** The exposure suit of a Dive; the type [SuitType.none] is a dive without one. */
data class Suit(
    val type: SuitType,
    val thicknessMm: Double?,
    val hood: Boolean?,
    val undergarment: SuitUndergarment?,
)

data class WeightingFeedback(val felt: WeightingFelt, val amountKg: Double?)

/** A cylinder as the estimate reads it; a null material is taken halfway between aluminium and steel. */
data class EstimateCylinder(val volumeL: Double, val material: CylinderMaterial?)

/** A Dive's cylinder with its logged end pressure (null when none was logged). */
data class EstimateDiveCylinder(val volumeL: Double, val material: CylinderMaterial?, val endPressureBar: Double?)

/** One of the Diver's Dives (not deleted: the caller leaves those out), as far as the estimate looks at it. */
data class EstimateDive(
    val id: String,
    val date: Instant,
    /** The total of its Lead entries in kg; null when none were logged (unknown). 0 is no lead and counts. */
    val leadKg: Double?,
    val feedback: WeightingFeedback?,
    val suit: Suit?,
    /** The water of its site (ADR 0025); null when the Dive has no site or the site no water type. */
    val water: SiteWaterType?,
    val cylinders: List<EstimateDiveCylinder>,
)

data class PlannedConditions(
    val water: SiteWaterType,
    val suit: Suit,
    val cylinders: List<EstimateCylinder>,
)

data class EstimateInput(
    val dives: List<EstimateDive>,
    /** The Diver's latest body weight in kg; null when none is set. */
    val bodyWeightKg: Double?,
    val plan: PlannedConditions,
    val unit: LeadUnit,
    /** The day the plan is made on, for the age of the dives. */
    val today: Instant,
)

data class LeadRange(val low: Double, val high: Double)

/** A Dive the estimate rests on, with what it needed as carried and corrected by its feedback. */
data class EstimateDiveUse(val id: String, val carried: Double, val needed: Double, val weight: Double)

/** What the estimate rests on. */
sealed interface EstimateBasis {
    data class Dives(
        val dives: List<EstimateDiveUse>,
        /** The Diver's own term in the answer's unit: needed at fresh water with a cylinder that is neutral at reserve. */
        val personal: Double,
    ) : EstimateBasis

    data class RuleOfThumb(
        /** The rule for the planned suit, salt water and an AL80, in the answer's unit. */
        val rule: Double,
        /** How far the Diver's own Dives differ from the rule for the suits they logged; null without any. */
        val personalOffset: Double?,
        /** How many suit set-ups the offset comes from. */
        val offsetFromSuits: Int,
    ) : EstimateBasis
}

sealed interface EstimateAdjustment {
    data class Cylinder(val amount: Double) : EstimateAdjustment

    data class Water(val amount: Double, val water: SiteWaterType) : EstimateAdjustment

    data class PersonalOffset(val amount: Double) : EstimateAdjustment
}

sealed interface EstimateMissing {
    data object BodyWeight : EstimateMissing

    data class CylinderMaterial(val diveIds: List<String>, val planned: Boolean) : EstimateMissing

    data class Cylinder(val diveIds: List<String>) : EstimateMissing

    data object SuitThickness : EstimateMissing

    data object SuitUndergarment : EstimateMissing
}

/** The cylinder's reserve in bar used, and whether it is the Diver's own median. */
data class LeadReserve(val bar: Double, val fromDives: Boolean)

data class LeadEstimate(
    val unit: LeadUnit,
    /** Null when it can't be told: the rule of thumb needs body weight and there are no dives with the suit. */
    val total: Double?,
    val range: LeadRange?,
    val confidence: Confidence,
    /** Null together with [total]. */
    val basis: EstimateBasis?,
    val adjustments: List<EstimateAdjustment>,
    val missing: List<EstimateMissing>,
    val reserve: LeadReserve,
)

/** The rule's lead in kg for the suit and body weight, in salt water with an AL80, and what it had to assume. */
private data class RuleKg(val kg: Double, val missing: List<EstimateMissing>)

private fun ruleOfThumbKg(suit: Suit, bodyWeightKg: Double): RuleKg {
    val missing = mutableListOf<EstimateMissing>()
    if (suit.type == SuitType.none || suit.type == SuitType.skin) return RuleKg(RULE_SKIN_KG, missing)
    if (suit.type == SuitType.drysuit) {
        if (suit.undergarment == null) missing.add(EstimateMissing.SuitUndergarment)
        return RuleKg(RULE_PERCENT_THICK * bodyWeightKg + RULE_DRYSUIT_EXTRA_KG.getValue(suit.undergarment ?: SuitUndergarment.medium), missing)
    }
    if (suit.thicknessMm == null) missing.add(EstimateMissing.SuitThickness)
    val mm = suit.thicknessMm ?: RULE_ASSUMED_THICKNESS_MM
    if (mm <= RULE_THIN_UP_TO_MM) return RuleKg(RULE_PERCENT_THIN * bodyWeightKg, missing)
    // A hood is part of the table's 7 mm row; only a logged "no hood" leaves the extra out.
    val extra = if (mm >= RULE_THICK_FROM_MM && suit.hood != false) RULE_THICK_EXTRA_KG else 0.0
    return RuleKg(RULE_PERCENT_THICK * bodyWeightKg + extra, missing)
}

// ---- Physics.

private fun emptyBuoyancyKg(volumeL: Double, material: CylinderMaterial): Double {
    val points = EMPTY_BUOYANCY_POINTS.getValue(material)
    val last = points.last()
    if (volumeL >= last.first) return last.second
    for (i in 1 until points.size) {
        val (v1, b1) = points[i]
        if (volumeL <= v1) {
            val (v0, b0) = points[i - 1]
            return b0 + (b1 - b0) * (volumeL - v0) / (v1 - v0)
        }
    }
    return last.second
}

private data class CylinderTerm(
    val kg: Double,
    /** Half the difference between aluminium and steel where the material is not known, else 0. */
    val uncertaintyKg: Double,
    val materialKnown: Boolean,
)

/** One cylinder's buoyancy at reserve in kg (positive floats, so needs more lead). */
private fun oneCylinderAtReserve(c: EstimateCylinder, reserveBar: Double): CylinderTerm {
    val twin = c.volumeL > TWIN_ABOVE_L
    val each = if (twin) c.volumeL / 2 else c.volumeL
    val count = if (twin) 2.0 else 1.0
    val gas = each * reserveBar * GAS_KG_PER_LITRE_BAR
    // Carbon is not tabulated: it is treated like an unknown material rather than guessed as aluminium or steel.
    val material = c.material?.takeIf { it == CylinderMaterial.aluminium || it == CylinderMaterial.steel }
    val alu = emptyBuoyancyKg(each, CylinderMaterial.aluminium)
    val steel = emptyBuoyancyKg(each, CylinderMaterial.steel)
    val empty = when (material) {
        CylinderMaterial.aluminium -> alu
        CylinderMaterial.steel -> steel
        else -> (alu + steel) / 2
    }
    return CylinderTerm(
        kg = count * (empty - gas),
        uncertaintyKg = if (material != null) 0.0 else count * abs(alu - steel) / 2,
        materialKnown = material != null,
    )
}

private fun cylindersAtReserve(cylinders: List<EstimateCylinder>, reserveBar: Double): CylinderTerm {
    val terms = cylinders.map { oneCylinderAtReserve(it, reserveBar) }
    return CylinderTerm(
        kg = terms.sumOf { it.kg },
        uncertaintyKg = terms.sumOf { it.uncertaintyKg },
        materialKnown = terms.all { it.materialKnown },
    )
}

private fun densityRatio(water: SiteWaterType): Double =
    WATER_DENSITY_KG_M3.getValue(water) / WATER_DENSITY_KG_M3.getValue(SiteWaterType.fresh)

/**
 * Lead needed in [water] for a base (personal + cylinder terms, as at fresh water). The denser water needs its ratio
 * more displacement for everything carried, body and gear and lead: needed = base x ratio + (body + gear) x (ratio - 1).
 * Fresh to salt is then 2.5 % of the total mass.
 */
private fun neededIn(water: SiteWaterType, base: Double, bodyAndGearKg: Double): Double =
    base * densityRatio(water) + bodyAndGearKg * (densityRatio(water) - 1)

/** The inverse: the base (as at fresh water) from what was needed in [water]. */
private fun baseFrom(water: SiteWaterType, needed: Double, bodyAndGearKg: Double): Double =
    (needed - bodyAndGearKg * (densityRatio(water) - 1)) / densityRatio(water)

// ---- Statistics.

private data class Weighted(val value: Double, val weight: Double)

/** The weighted median; at exactly half the weight, the mean of the two values around it. */
private fun weightedMedian(items: List<Weighted>): Double {
    val sorted = items.sortedBy { it.value }
    val half = sorted.sumOf { it.weight } / 2
    var cumulative = 0.0
    for (i in sorted.indices) {
        cumulative += sorted[i].weight
        if (abs(cumulative - half) < 1e-9 && i + 1 < sorted.size) return (sorted[i].value + sorted[i + 1].value) / 2
        if (cumulative > half) return sorted[i].value
    }
    return sorted.last().value
}

private fun median(values: List<Double>): Double = weightedMedian(values.map { Weighted(it, 1.0) })

// ---- Dives.

/** What a dive needed: carried, corrected by its feedback. Too heavy by x: carried - x; too light by x: carried + x. */
private fun neededKg(carried: Double, feedback: WeightingFeedback?): Double {
    if (feedback == null || feedback.felt == WeightingFelt.right) return carried
    val amount = feedback.amountKg ?: FEEDBACK_DEFAULT_KG
    return if (feedback.felt == WeightingFelt.too_heavy) carried - amount else carried + amount
}

private fun diveWeight(d: EstimateDive, today: Instant): Double {
    val years = max(0.0, (today - d.date).inWholeMilliseconds.toDouble() / 86_400_000.0 / DAYS_PER_YEAR)
    return (if (d.feedback?.felt == WeightingFelt.right) WEIGHT_FELT_RIGHT else WEIGHT_OTHER) * 0.5.pow(years / HALF_LIFE_YEARS)
}

/** The Diver's reserve: the median of every logged end pressure, else the default. */
private fun reserveOf(dives: List<EstimateDive>): LeadReserve {
    val pressures = dives.flatMap { d -> d.cylinders.mapNotNull { it.endPressureBar } }.filter { it > 0 }
    return if (pressures.isNotEmpty()) LeadReserve(median(pressures), true) else LeadReserve(DEFAULT_RESERVE_BAR, false)
}

private fun suitKey(s: Suit): String = if (s.type == SuitType.none || s.type == SuitType.skin) {
    s.type.name
} else {
    "${s.type}|${s.thicknessMm}|${s.hood}|${s.undergarment?.name}"
}

// ---- Output units.

/** A total in the answer's unit, rounded to 0.5 kg or 1 lb; [jsRound] ties as the server's Math.round does. */
private fun roundedTotal(kg: Double, unit: LeadUnit): Double =
    if (unit == LeadUnit.lb) jsRound(kg / KG_PER_LB) else jsRound(kg / ROUND_KG) * ROUND_KG

/** Kilograms in the answer's unit, unrounded. */
private fun inUnit(kg: Double, unit: LeadUnit): Double = if (unit == LeadUnit.lb) kg / KG_PER_LB else kg

/** For explanations: one decimal. */
private fun detail(kg: Double, unit: LeadUnit): Double = jsRound(inUnit(kg, unit) * 10) / 10

private data class Answer(val total: Double, val range: LeadRange)

private fun answer(totalKg: Double, halfWidthKg: Double, unit: LeadUnit): Answer = Answer(
    total = roundedTotal(totalKg, unit),
    range = LeadRange(
        low = roundedTotal(max(0.0, totalKg - halfWidthKg), unit),
        high = roundedTotal(totalKg + halfWidthKg, unit),
    ),
)

// ---- The estimate.

private data class UsedDive(val id: String, val carried: Double, val needed: Double, val weight: Double)

private class Terms {
    val weighted = mutableListOf<Weighted>()
    val used = mutableListOf<UsedDive>()
    val unknownMaterial = mutableListOf<String>()
    val noCylinder = mutableListOf<String>()

    /** The largest uncertainty from a cylinder of unknown material among the dives. */
    var uncertaintyKg = 0.0
}

/**
 * Each dive's personal term: what it needed, with its cylinder at reserve and its water removed (as at fresh water, a
 * cylinder that is neutral at reserve). A dive without a cylinder is taken to have had the planned one.
 */
private fun personalTerms(
    dives: List<EstimateDive>,
    reserveBar: Double,
    plannedCylinderKg: Double,
    bodyAndGearKg: Double,
    today: Instant,
): Terms {
    val terms = Terms()
    for (d in dives) {
        val leadKg = d.leadKg ?: error("personalTerms needs dives with lead; the caller filters them")
        val needed = neededKg(leadKg, d.feedback)
        var cylinderKg = plannedCylinderKg
        if (d.cylinders.isEmpty()) {
            terms.noCylinder.add(d.id)
        } else {
            val term = cylindersAtReserve(d.cylinders.map { EstimateCylinder(it.volumeL, it.material) }, reserveBar)
            cylinderKg = term.kg
            terms.uncertaintyKg = max(terms.uncertaintyKg, term.uncertaintyKg)
            if (!term.materialKnown) terms.unknownMaterial.add(d.id)
        }
        val weight = diveWeight(d, today)
        terms.weighted.add(Weighted(baseFrom(d.water!!, needed, bodyAndGearKg) - cylinderKg, weight))
        terms.used.add(UsedDive(d.id, leadKg, needed, weight))
    }
    return terms
}

fun estimateLead(input: EstimateInput): LeadEstimate {
    val plan = input.plan
    val unit = input.unit
    val reserve = reserveOf(input.dives)
    val missing = mutableListOf<EstimateMissing>()
    if (input.bodyWeightKg == null) missing.add(EstimateMissing.BodyWeight)
    val bodyAndGearKg = (input.bodyWeightKg ?: FALLBACK_BODY_WEIGHT_KG) + GEAR_MASS_KG

    val planCylinders = cylindersAtReserve(plan.cylinders, reserve.bar)
    val planMaterialUnknown = !planCylinders.materialKnown

    // Candidates: lead logged (0 counts), a suit set-up, the water known from the site.
    val eligible = input.dives.filter { it.leadKg != null && it.suit != null && it.water != null }
    val planSuit = suitKey(plan.suit)
    val candidates = eligible.filter { suitKey(it.suit!!) == planSuit }
    val terms = personalTerms(candidates, reserve.bar, planCylinders.kg, bodyAndGearKg, input.today)

    if (terms.weighted.isNotEmpty()) {
        if (terms.unknownMaterial.isNotEmpty() || planMaterialUnknown) {
            missing.add(EstimateMissing.CylinderMaterial(terms.unknownMaterial.toList(), planMaterialUnknown))
        }
        if (terms.noCylinder.isNotEmpty()) missing.add(EstimateMissing.Cylinder(terms.noCylinder.toList()))

        val personal = weightedMedian(terms.weighted)
        val spread = MAD_TO_SIGMA * weightedMedian(terms.weighted.map { Weighted(abs(it.value - personal), it.weight) })
        val floor = if (terms.used.size <= FEW_DIVES) HALF_WIDTH_FEW_DIVES_KG else HALF_WIDTH_MANY_DIVES_KG
        val halfWidth = max(floor, spread) + max(terms.uncertaintyKg, planCylinders.uncertaintyKg)

        val total = neededIn(plan.water, personal + planCylinders.kg, bodyAndGearKg)
        val result = answer(total, halfWidth, unit)
        return LeadEstimate(
            unit = unit,
            total = result.total,
            range = result.range,
            confidence = if (terms.used.size >= MANY_DIVES) Confidence.high else Confidence.medium,
            basis = EstimateBasis.Dives(
                dives = terms.used.map { EstimateDiveUse(it.id, detail(it.carried, unit), detail(it.needed, unit), it.weight) },
                personal = detail(personal, unit),
            ),
            adjustments = listOf(
                EstimateAdjustment.Cylinder(detail(planCylinders.kg, unit)),
                EstimateAdjustment.Water(detail(total - (personal + planCylinders.kg), unit), plan.water),
            ),
            missing = missing,
            reserve = reserve,
        )
    }

    // No dive with this suit set-up: the rule of thumb, shifted by the Diver's own offset from the suits they did log.
    val bodyWeightKg = input.bodyWeightKg ?: return LeadEstimate(
        unit = unit,
        total = null,
        range = null,
        confidence = Confidence.low,
        basis = null,
        adjustments = emptyList(),
        missing = missing,
        reserve = reserve,
    )
    val ruleCylinderKg = cylindersAtReserve(listOf(RULE_CYLINDER), reserve.bar).kg
    /** The rule's personal term: its lead at the rule's conditions, taken back to fresh water and a neutral cylinder. */
    fun ruleTerm(suit: Suit): Double =
        baseFrom(RULE_WATER, ruleOfThumbKg(suit, bodyWeightKg).kg, bodyAndGearKg) - ruleCylinderKg

    val bySuit = LinkedHashMap<String, MutableList<EstimateDive>>()
    for (d in eligible) bySuit.getOrPut(suitKey(d.suit!!)) { mutableListOf() }.add(d)
    val offsets = mutableListOf<Double>()
    var offsetUncertaintyKg = 0.0
    for (group in bySuit.values) {
        val g = personalTerms(group, reserve.bar, planCylinders.kg, bodyAndGearKg, input.today)
        offsets.add(weightedMedian(g.weighted) - ruleTerm(group.first().suit!!))
        offsetUncertaintyKg = max(offsetUncertaintyKg, g.uncertaintyKg)
        if (g.unknownMaterial.isNotEmpty()) missing.add(EstimateMissing.CylinderMaterial(g.unknownMaterial.toList(), false))
        if (g.noCylinder.isNotEmpty()) missing.add(EstimateMissing.Cylinder(g.noCylinder.toList()))
    }
    val offset = if (offsets.isNotEmpty()) median(offsets) else null

    val rule = ruleOfThumbKg(plan.suit, bodyWeightKg)
    missing.addAll(rule.missing)
    if (planMaterialUnknown) missing.add(EstimateMissing.CylinderMaterial(emptyList(), true))
    val personal = ruleTerm(plan.suit) + (offset ?: 0.0)
    val total = neededIn(plan.water, personal + planCylinders.kg, bodyAndGearKg)
    val halfWidth = (if (offset == null) HALF_WIDTH_RULE_ALONE_KG else HALF_WIDTH_RULE_WITH_OFFSET_KG) +
        max(offsetUncertaintyKg, planCylinders.uncertaintyKg)
    val adjustments = buildList {
        if (offset != null) add(EstimateAdjustment.PersonalOffset(detail(offset, unit)))
        add(EstimateAdjustment.Cylinder(detail(planCylinders.kg, unit)))
        add(EstimateAdjustment.Water(detail(total - (personal + planCylinders.kg), unit), plan.water))
    }
    val result = answer(total, halfWidth, unit)
    return LeadEstimate(
        unit = unit,
        total = result.total,
        range = result.range,
        confidence = Confidence.low,
        missing = missing,
        reserve = reserve,
        adjustments = adjustments,
        basis = EstimateBasis.RuleOfThumb(
            rule = detail(rule.kg, unit),
            personalOffset = offset?.let { detail(it, unit) },
            offsetFromSuits = offsets.size,
        ),
    )
}
