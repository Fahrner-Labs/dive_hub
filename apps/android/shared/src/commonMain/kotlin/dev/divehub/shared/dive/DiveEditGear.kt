package dev.divehub.shared.dive

import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewLeadItem
import dev.divehub.api.models.DiveViewRecordingsItem
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** A pressure series of one of the Dive's Recordings that measured a Cylinder (ADR 0045). */
data class DiveEditSeries(val recordingId: String, val channel: String)

/**
 * One Cylinder in the Dive's edit form (ADR 0045). The values are the API's (litres and bar), and the screen converts
 * the pressures to the User's unit for display only. `fromPod` and `existing` are display and form bookkeeping: they
 * never go back to the server.
 */
data class DiveEditCylinder(
    val volumeL: Double? = null,
    val workingPressureBar: Double? = null,
    val material: String? = null,
    val o2: Double? = null,
    val he: Double? = null,
    val startPressureBar: Double? = null,
    val endPressureBar: Double? = null,
    val equipmentItemId: String? = null,
    val series: DiveEditSeries? = null,
    /** An import made it from a tank pod's data and it still has the pod's series: the screen says so. */
    val fromPod: Boolean = false,
    /** Whether the Dive already had it: a blank row the User added is not saved. */
    val existing: Boolean = false,
    /** Stable while the form is open, so a text field keeps its place when another Cylinder is removed. */
    val key: String = nextGearKey("cylinder"),
) {
    /** A row nobody typed anything into: the form doesn't save it. */
    val blank: Boolean
        get() = volumeL == null && workingPressureBar == null && material == null && o2 == null && he == null &&
            startPressureBar == null && endPressureBar == null && equipmentItemId == null && series == null

    /** The values the API takes; the form's own fields are left out. */
    fun wire(): DiveEditCylinder = copy(fromPod = false, existing = false, key = "")

    /** Whether the server would refuse it as typed (docs/spec/clients.md, `cylinder_invalid`). */
    fun problem(): CylinderProblem? {
        if (startPressureBar != null && endPressureBar != null && endPressureBar > startPressureBar) {
            return CylinderProblem.END_ABOVE_START
        }
        if (o2 != null && o2 + (he ?: 0.0) > 100.0) return CylinderProblem.GAS_OVER_100
        return null
    }

    fun toJson(): JsonObject = buildJsonObject {
        put("volumeL", volumeL?.let(::JsonPrimitive) ?: JsonNull)
        put("workingPressureBar", workingPressureBar?.let(::JsonPrimitive) ?: JsonNull)
        put("material", material?.let(::JsonPrimitive) ?: JsonNull)
        put(
            "gas",
            o2?.let { oxygen ->
                buildJsonObject {
                    put("o2", oxygen)
                    put("he", he ?: 0.0)
                }
            } ?: JsonNull,
        )
        put("startPressureBar", startPressureBar?.let(::JsonPrimitive) ?: JsonNull)
        put("endPressureBar", endPressureBar?.let(::JsonPrimitive) ?: JsonNull)
        put("equipmentItemId", equipmentItemId?.let(::JsonPrimitive) ?: JsonNull)
        put(
            "series",
            series?.let {
                buildJsonObject {
                    put("recordingId", it.recordingId)
                    put("channel", it.channel)
                }
            } ?: JsonNull,
        )
    }
}

enum class CylinderProblem { END_ABOVE_START, GAS_OVER_100 }

/** One Lead entry in the edit form (ADR 0031): the amount in kilograms and where it was carried. */
data class DiveEditLead(
    val amountKg: Double? = null,
    val placement: String? = null,
    /** Stable while the form is open, so a text field keeps its place when another entry is removed. */
    val key: String = nextGearKey("lead"),
) {
    fun wire(): DiveEditLead = copy(key = "")

    fun toJson(): JsonObject = buildJsonObject {
        put("amountKg", amountKg!!)
        put("placement", placement?.let(::JsonPrimitive) ?: JsonNull)
    }
}

/**
 * A tank pod's pressure series the User can tie a Cylinder to: the Recording it belongs to (named by its Device, else
 * by its number) and the pod's number within that Recording.
 */
data class DiveEditSeriesOption(
    val series: DiveEditSeries,
    val recordingName: String?,
    val recordingIndex: Int,
    val podIndex: Int,
    val podCount: Int,
)

private const val PRESSURE_CHANNEL = "tankPressure"

/** Every tank-pressure channel of the Dive's Recordings, in their order (ADR 0045). */
fun pressureSeriesOptions(recordings: List<DiveViewRecordingsItem>): List<DiveEditSeriesOption> =
    recordings.flatMapIndexed { recordingIndex, recording ->
        val channels = recording.channels.filter { it == PRESSURE_CHANNEL || it.startsWith("$PRESSURE_CHANNEL:") }
        channels.mapIndexed { podIndex, channel ->
            DiveEditSeriesOption(
                series = DiveEditSeries(recording.id, channel),
                recordingName = recording.device?.let { deviceName(it.manufacturer, it.product) },
                recordingIndex = recordingIndex,
                podIndex = podIndex,
                podCount = channels.size,
            )
        }
    }

private var gearKeys = 0L

private fun nextGearKey(prefix: String): String = "$prefix-${++gearKeys}"

internal fun DiveViewCylindersItem.toEditDraft(): DiveEditCylinder = DiveEditCylinder(
    volumeL = volumeL,
    workingPressureBar = workingPressureBar,
    material = material,
    o2 = gas?.o2,
    he = gas?.he,
    startPressureBar = startPressureBar,
    endPressureBar = endPressureBar,
    equipmentItemId = equipmentItemId,
    series = series?.let { DiveEditSeries(it.recordingId, it.channel) },
    fromPod = fromPod,
    existing = true,
)

internal fun DiveViewLeadItem.toEditDraft(): DiveEditLead = DiveEditLead(amountKg = amountKg, placement = placement)
