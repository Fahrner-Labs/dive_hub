package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.math.roundToInt

/** A start time being edited: the wall clock at the dive site and the UTC offset it is read in. */
data class DiveEditStart(val local: LocalDateTime, val utcOffsetSeconds: Int)

/** The exposure suit as the edit form holds it; the server refuses parts the type does not have. */
data class DiveEditSuit(
    val type: String,
    val thicknessMm: Double? = null,
    val hood: Boolean? = null,
    val undergarment: String? = null,
) {
    val worn: Boolean get() = type != "none"
    val hasThickness: Boolean get() = type == "wetsuit" || type == "semidry"
}

/** How the weighting felt; `amountKg` is only meaningful for `too_heavy` and `too_light`. */
data class DiveEditWeighting(val felt: String, val amountKg: Double? = null)

/**
 * The Dive's scalar edit form ("write as if online", ADR 0084): an immutable draft that knows the value it was opened
 * with, which fields the User set by hand ([touched]), which were given back to the Recording ([reset]), and the
 * PATCH body those changes add up to.
 *
 * `number` is not here on purpose: the server computes it and refuses it on an edit (ADR 0048). Cylinders and Lead are
 * whole-list fields here (T8b); Participants are another editor (T8c).
 */
data class DiveEditDraft(
    val diveId: String,
    val version: Int,
    /** The server-computed number (ADR 0048): shown in the editor, never part of an edit. */
    val number: Int?,
    val notes: String,
    val siteId: String?,
    val start: DiveEditStart,
    /** Where the start's offset came from (ADR 0030); the editor says so, and editing the start silences it. */
    val utcOffsetSource: String,
    val durationMinutes: Int,
    val maxDepthM: Double?,
    val avgDepthM: Double?,
    val waterTemperatureC: Double?,
    val suit: DiveEditSuit?,
    val weighting: DiveEditWeighting?,
    /** The Dive's Cylinders in their order; the edit sends the whole list when it changes (T8b). */
    val cylinders: List<DiveEditCylinder>,
    /** The Lead entries in their order; the edit sends the whole list when it changes (T8b). */
    val lead: List<DiveEditLead>,
    private val originalNotes: String?,
    private val originalSiteId: String?,
    private val originalStart: DiveEditStart,
    private val originalDurationMinutes: Int,
    private val originalMaxDepthM: Double?,
    private val originalAvgDepthM: Double?,
    private val originalWaterTemperatureC: Double?,
    private val originalSuit: DiveEditSuit?,
    private val originalWeighting: DiveEditWeighting?,
    private val originalCylinders: List<DiveEditCylinder>,
    private val originalLead: List<DiveEditLead>,
    private val recorded: Map<String, RecordedValue>,
    private val overrides: Set<String>,
    val touched: Set<String> = emptySet(),
    val reset: Set<String> = emptySet(),
    val suitTouched: Boolean = false,
    val weightingTouched: Boolean = false,
) {
    /** Whether a value shows the "edited" mark: set before, or this session, and not given back to the Recording. */
    fun isEdited(field: DiveValue): Boolean =
        (field.wire in overrides || field.wire in touched) && field.wire !in reset

    /** Whether [field] has a Recording value to go back to; a hand-typed Dive has none. */
    fun hasRecording(field: DiveValue): Boolean = field.wire in recorded

    /** What the Primary Recording says for [field], for the "use the recording's value" hint. */
    fun recordedValue(field: DiveValue): RecordedValue? = recorded[field.wire]

    /** Whether this session set [field] by hand; the UI marks it while typing, before saving. */
    fun wasSetHere(field: DiveValue): Boolean = field.wire in touched && field.wire !in reset

    val dirty: Boolean
        get() = touched.isNotEmpty() ||
            reset.any { it in overrides } ||
            notes != (originalNotes ?: "") ||
            siteId != originalSiteId ||
            suitTouched ||
            weightingTouched ||
            cylindersChanged ||
            leadChanged

    /** Whether the Cylinder list would save differently; a blank row the User added does not count. */
    val cylindersChanged: Boolean
        get() = cylinderValues() != originalCylinders

    /** Whether the Lead list would save differently; an entry without an amount is not saved. */
    val leadChanged: Boolean
        get() = leadValues() != originalLead

    /** Whether every Cylinder the server would receive is one it accepts (docs/spec/clients.md, `cylinder_invalid`). */
    val cylindersFit: Boolean
        get() = cylinderValues().all { it.problem() == null }

    fun setNotes(value: String): DiveEditDraft = copy(notes = value)

    fun setSite(id: String?): DiveEditDraft = copy(siteId = id)

    fun setStart(value: DiveEditStart): DiveEditDraft =
        copy(start = value).mark(DiveValue.STARTS_AT)

    fun setDurationMinutes(value: Int): DiveEditDraft =
        copy(durationMinutes = value.coerceAtLeast(0)).mark(DiveValue.DURATION_SECONDS)

    fun setMaxDepthM(value: Double?): DiveEditDraft = copy(maxDepthM = value).mark(DiveValue.MAX_DEPTH_M)

    fun setAvgDepthM(value: Double?): DiveEditDraft = copy(avgDepthM = value).mark(DiveValue.AVG_DEPTH_M)

    fun setWaterTemperatureC(value: Double?): DiveEditDraft =
        copy(waterTemperatureC = value).mark(DiveValue.WATER_TEMPERATURE_C)

    fun setSuit(value: DiveEditSuit?): DiveEditDraft = copy(suit = value, suitTouched = true)

    fun setWeighting(value: DiveEditWeighting?): DiveEditDraft =
        copy(weighting = value, weightingTouched = true)

    fun setCylinders(value: List<DiveEditCylinder>): DiveEditDraft = copy(cylinders = value)

    fun setLead(value: List<DiveEditLead>): DiveEditDraft = copy(lead = value)

    /**
     * Give [field] back to the Recording (ADR 0015): the draft takes the recorded value again so the page shows what
     * will be saved, and the patch asks the server to drop the Override.
     */
    fun resetToRecording(field: DiveValue): DiveEditDraft {
        val value = recorded[field.wire] ?: return this
        val cleared = copy(
            touched = touched - field.wire,
            reset = reset + field.wire,
        )
        return when (field) {
            DiveValue.STARTS_AT -> (value as? RecordedValue.Time)?.let { time ->
                val offset = time.utcOffsetSeconds ?: 0
                cleared.copy(start = DiveEditStart(diveLocalTime(time.at, offset) ?: cleared.start.local, offset))
            } ?: cleared
            DiveValue.DURATION_SECONDS -> cleared.copy(
                durationMinutes = ((value as? RecordedValue.Number)?.value ?: 0.0).div(60).roundToInt(),
            )
            DiveValue.MAX_DEPTH_M -> cleared.copy(
                maxDepthM = (value as? RecordedValue.Number)?.value,
            )
            DiveValue.AVG_DEPTH_M -> cleared.copy(
                avgDepthM = (value as? RecordedValue.Number)?.value,
            )
            DiveValue.WATER_TEMPERATURE_C -> cleared.copy(
                waterTemperatureC = (value as? RecordedValue.Number)?.value,
            )
        }
    }

    /**
     * The PATCH body these changes add up to (`set`, `reset`, notes, site, suit, weighting), or null when nothing
     * would change. The queue never sends `version`: it carries the base version and sends it as `If-Match`.
     */
    fun patch(): JsonObject? {
        val set = linkedMapOf<String, JsonElement>()
        for (wire in touched) {
            if (wire in reset) continue
            when (wire) {
                DiveValue.STARTS_AT.wire -> set[wire] = buildJsonObject {
                    put("at", diveStartAt(start.local, start.utcOffsetSeconds))
                    put("utcOffsetSeconds", start.utcOffsetSeconds)
                }
                DiveValue.DURATION_SECONDS.wire -> set[wire] = JsonPrimitive(durationMinutes * 60)
                DiveValue.MAX_DEPTH_M.wire -> set[wire] = maxDepthM.numberOrNull()
                DiveValue.AVG_DEPTH_M.wire -> set[wire] = avgDepthM.numberOrNull()
                DiveValue.WATER_TEMPERATURE_C.wire -> set[wire] = waterTemperatureC.numberOrNull()
            }
        }
        // Only a field the Dive already overrode can be given back: resetting one it never overrode is a no-op.
        val resets = reset.filter { it in overrides }

        val body = buildJsonObject {
            if (set.isNotEmpty()) put("set", JsonObject(set))
            if (resets.isNotEmpty()) put("reset", JsonArray(resets.map(::JsonPrimitive)))
            if (notes != (originalNotes ?: "")) {
                put("notes", notes.takeIf { it.isNotBlank() }?.let(::JsonPrimitive) ?: JsonNull)
            }
            if (siteId != originalSiteId) {
                put("siteId", siteId?.let(::JsonPrimitive) ?: JsonNull)
            }
            if (suitTouched) {
                put("suit", suit?.toJson() ?: JsonNull)
            }
            if (weightingTouched) {
                put("weightingFeedback", weighting?.toJson() ?: JsonNull)
            }
            if (cylindersChanged) {
                put("cylinders", JsonArray(cylinderValues().map { it.toJson() }))
            }
            if (leadChanged) {
                put("lead", JsonArray(leadValues().map { it.toJson() }))
            }
        }
        return body.takeIf { it.isNotEmpty() }
    }

    /** The Cylinders the edit takes: the ones the Dive had, plus a new one the User filled in. */
    private fun cylinderValues(): List<DiveEditCylinder> =
        cylinders.filter { it.existing || !it.blank }.map { it.wire() }

    /** The Lead the edit takes: an entry without an amount is unknown and left out. */
    private fun leadValues(): List<DiveEditLead> =
        lead.mapNotNull { entry -> entry.amountKg?.let { entry.wire() } }

    private fun mark(field: DiveValue): DiveEditDraft = copy(
        touched = touched + field.wire,
        reset = reset - field.wire,
    )

    companion object {
        fun of(dive: DiveView): DiveEditDraft {
            val values = dive.values
            val offset = values.startsAt.utcOffsetSeconds ?: 0
            val local = diveLocalTime(values.startsAt.at, offset)
                ?: LocalDateTime(1970, 1, 1, 0, 0)
            val recorded = DiveValue.entries.mapNotNull { field ->
                dive.recordedValueOf(field)?.let { field.wire to it }
            }.toMap()
            val cylinders = dive.cylinders.map { it.toEditDraft() }
            val lead = dive.lead.map { it.toEditDraft() }
            return DiveEditDraft(
                diveId = dive.id,
                version = dive.version,
                number = values.number,
                notes = dive.notes.orEmpty(),
                siteId = dive.site?.id,
                start = DiveEditStart(local, offset),
                utcOffsetSource = dive.utcOffsetSource,
                durationMinutes = (values.durationSeconds / 60).roundToInt(),
                maxDepthM = values.maxDepthM,
                avgDepthM = values.avgDepthM,
                waterTemperatureC = values.waterTemperatureC,
                suit = dive.suit?.toDraft(),
                weighting = dive.weightingFeedback?.toDraft(),
                cylinders = cylinders,
                // One entry's fields are always there; left empty it is not saved.
                lead = lead.ifEmpty { listOf(DiveEditLead()) },
                originalNotes = dive.notes,
                originalSiteId = dive.site?.id,
                originalStart = DiveEditStart(local, offset),
                originalDurationMinutes = (values.durationSeconds / 60).roundToInt(),
                originalMaxDepthM = values.maxDepthM,
                originalAvgDepthM = values.avgDepthM,
                originalWaterTemperatureC = values.waterTemperatureC,
                originalSuit = dive.suit?.toDraft(),
                originalWeighting = dive.weightingFeedback?.toDraft(),
                originalCylinders = cylinders.map { it.wire() },
                originalLead = lead.map { it.wire() },
                recorded = recorded,
                overrides = dive.overrides.toSet(),
            )
        }
    }
}

private fun Double?.numberOrNull(): JsonElement = this?.let(::JsonPrimitive) ?: JsonNull

private fun DiveViewSuitDraftSource.toDraft(): DiveEditSuit = DiveEditSuit(
    type = type,
    thicknessMm = thicknessMm,
    hood = (hood as? JsonPrimitive)?.booleanOrNull,
    undergarment = undergarment,
)

/** The generated model's shape, kept out of the signature so the draft reads the same to callers. */
private typealias DiveViewSuitDraftSource = dev.divehub.api.models.DiveViewSuit

private fun dev.divehub.api.models.DiveViewWeightingFeedback.toDraft(): DiveEditWeighting =
    DiveEditWeighting(felt = felt, amountKg = amountKg)

private fun DiveEditSuit.toJson(): JsonObject = buildJsonObject {
    put("type", type)
    if (hasThickness) put("thicknessMm", thicknessMm?.let(::JsonPrimitive) ?: JsonNull)
    if (worn) put("hood", hood?.let(::JsonPrimitive) ?: JsonNull)
    if (type == "drysuit") put("undergarment", undergarment?.let(::JsonPrimitive) ?: JsonNull)
}

private fun DiveEditWeighting.toJson(): JsonObject = buildJsonObject {
    put("felt", felt)
    when (felt) {
        "right" -> Unit
        else -> put("amountKg", amountKg?.let(::JsonPrimitive) ?: JsonNull)
    }
}
