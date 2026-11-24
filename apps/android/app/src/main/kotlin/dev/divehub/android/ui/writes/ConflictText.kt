package dev.divehub.android.ui.writes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.divehub.android.R
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.dive.DiveDetailText
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.util.Locale

/**
 * The field's name as the decision screen says it: the API's own field (`notes`, `set.durationSeconds`,
 * `participants.<diverId>`) mapped onto the logbook's words. An unknown field keeps its API name rather than
 * disappearing, so a new merge field is still showable before its translation lands.
 */
@Composable
fun conflictFieldLabel(field: String): String = conflictFieldLabel(LocalContext.current, field)

/** The same label where a composable call is not possible, such as building one line from several fields. */
fun conflictFieldLabel(context: Context, field: String): String {
    val bare = field.substringAfterLast('.')
    val key = when {
        field == "participants" || field.startsWith("participants.") -> R.string.conflict_field_participants
        field == "notes" -> R.string.conflict_field_notes
        field == "siteId" -> R.string.conflict_field_site
        bare == "startsAt" || bare == "utcOffsetSeconds" -> R.string.conflict_field_start
        field == "suit" -> R.string.conflict_field_suit
        field == "weightingFeedback" -> R.string.conflict_field_weighting
        field == "cylinders" -> R.string.conflict_field_cylinders
        field == "lead" -> R.string.conflict_field_lead
        bare == "durationSeconds" -> R.string.conflict_field_duration
        bare == "maxDepthM" -> R.string.conflict_field_max_depth
        bare == "avgDepthM" -> R.string.conflict_field_avg_depth
        bare == "waterTemperatureC" -> R.string.conflict_field_water_temperature
        else -> null
    }
    return if (key != null) context.getString(key) else field
}

/**
 * One side's value, said the way the logbook says that field (ADR 0014): a depth in the User's unit, a duration as
 * "45 min" / "1 h 5 min", a temperature with its degree, the start as the Dive page reads it, Weighting in words, a
 * tank as a Cylinder is named. A field this client has no words for keeps the API's own value, so a new merge field is
 * still decidable before its presentation lands. A participant removal or an override reset has no JSON value of its
 * own, so its [localAction] carries what happened.
 */
fun conflictValueText(
    context: Context,
    field: String,
    element: JsonElement?,
    locale: Locale,
    imperial: Boolean,
    localAction: String? = null,
): String {
    when {
        localAction == "remove" -> return context.getString(R.string.conflict_removed)
        localAction == "reset" -> return context.getString(R.string.conflict_reset)
        element == null || element is JsonNull -> return context.getString(R.string.conflict_not_set)
    }
    conflictFieldValue(context, field, element, locale, imperial)?.let { return it }
    return rawConflictValue(context, element)
}

/** The logbook's words for a field whose value this client can present; null when it cannot. */
private fun conflictFieldValue(
    context: Context,
    field: String,
    element: JsonElement,
    locale: Locale,
    imperial: Boolean,
): String? {
    val bare = field.substringAfterLast('.')
    val number = numberOrNull(element)
    return when {
        field.startsWith("participants.") -> stringOrNull(element)?.let { Formats.role(context, it) }
        bare == "durationSeconds" -> number?.let { Formats.duration(context, it) }
        bare == "maxDepthM" || bare == "avgDepthM" -> number?.let { Formats.depth(context, it, locale, imperial) }
        bare == "waterTemperatureC" -> number?.let { DiveDetailText.temperature(context, it, locale, imperial) }
        bare == "startsAt" -> conflictStartText(context, element, locale)
        bare == "suit" -> conflictSuitText(context, element, locale)
        bare == "weightingFeedback" -> conflictFeedbackText(context, element, locale, imperial)
        bare == "lead" -> conflictLeadText(context, element, locale, imperial)
        bare == "cylinders" -> conflictCylindersText(context, element, locale, imperial)
        else -> null
    }
}

/** The local wall-clock start as the Dive page reads it; a value that is not a time stays as it came. */
private fun conflictStartText(context: Context, element: JsonElement, locale: Locale): String? = when (element) {
    is JsonObject -> {
        val at = stringOrNull(element["at"]) ?: return null
        val offset = (element["utcOffsetSeconds"] as? JsonPrimitive)?.content?.toIntOrNull()
        DiveDetailText.start(context, at, offset, locale)
    }
    is JsonPrimitive -> element.takeIf { it.isString }?.content?.let { raw ->
        // A Provider keeps "YYYY-MM-DD HH:MM"; the Dive page's reader wants an instant, shown as it was logged.
        val iso = raw.replace(' ', 'T')
        val instant = when {
            iso.endsWith("Z") || iso.contains('+') -> iso
            iso.length == 16 -> "${iso}:00Z"
            else -> "${iso}Z"
        }
        DiveDetailText.start(context, instant, null, locale)
    }
    else -> null
}

private fun conflictSuitText(context: Context, element: JsonElement, locale: Locale): String? {
    val suit = element as? JsonObject ?: return null
    val type = stringOrNull(suit["type"]) ?: return null
    val thickness = numberOrNull(suit["thicknessMm"])
    val hood = (suit["hood"] as? JsonPrimitive)?.takeUnless { it.isString }?.content?.toBooleanStrictOrNull()
    return DiveDetailText.suit(context, locale, type, thickness, hood, stringOrNull(suit["undergarment"]))
}

private fun conflictFeedbackText(
    context: Context,
    element: JsonElement,
    locale: Locale,
    imperial: Boolean,
): String? {
    val feedback = element as? JsonObject ?: return null
    val felt = stringOrNull(feedback["felt"]) ?: return null
    val amount = numberOrNull(feedback["amountKg"])
    return when (felt) {
        "right" -> context.getString(R.string.dive_feedback_right)
        "too_heavy" -> amount?.let {
            context.getString(R.string.dive_feedback_too_heavy, DiveDetailText.mass(context, it, locale, imperial))
        } ?: context.getString(R.string.dive_feedback_too_heavy_unknown)
        "too_light" -> amount?.let {
            context.getString(R.string.dive_feedback_too_light, DiveDetailText.mass(context, it, locale, imperial))
        } ?: context.getString(R.string.dive_feedback_too_light_unknown)
        else -> felt
    }
}

private fun conflictLeadText(
    context: Context,
    element: JsonElement,
    locale: Locale,
    imperial: Boolean,
): String? {
    val entries = element as? JsonArray ?: return null
    if (entries.isEmpty()) return context.getString(R.string.conflict_not_set)
    return entries.joinToString("\n") { entry ->
        val lead = entry as? JsonObject ?: return@joinToString rawConflictValue(context, entry)
        val amount = numberOrNull(lead["amountKg"])
        val amountText = amount?.let { DiveDetailText.mass(context, it, locale, imperial) }
            ?: return@joinToString rawConflictValue(context, entry)
        stringOrNull(lead["placement"])?.let { "$amountText ${DiveDetailText.placement(context, it)}" } ?: amountText
    }
}

private fun conflictCylindersText(
    context: Context,
    element: JsonElement,
    locale: Locale,
    imperial: Boolean,
): String? {
    val cylinders = element as? JsonArray ?: return null
    if (cylinders.isEmpty()) return context.getString(R.string.conflict_not_set)
    val lines = cylinders.mapNotNull { conflictCylinderText(context, it, locale, imperial) }
    return lines.takeIf { it.isNotEmpty() }?.joinToString("; ")
}

private fun conflictCylinderText(
    context: Context,
    element: JsonElement,
    locale: Locale,
    imperial: Boolean,
): String? {
    val cylinder = element as? JsonObject ?: return null
    val parts = buildList {
        numberOrNull(cylinder["volumeL"])?.let { add(DiveDetailText.volume(context, it, locale)) }
        stringOrNull(cylinder["material"])?.let { add(DiveDetailText.material(context, it)) }
        numberOrNull(cylinder["workingPressureBar"])?.let {
            add(context.getString(R.string.dive_cylinder_working_pressure, Formats.pressure(context, it, locale, imperial)))
        }
        val gas = cylinder["gas"] as? JsonObject
        add(DiveDetailText.gas(context, numberOrNull(gas?.get("o2")), numberOrNull(gas?.get("he"))))
    }
    return parts.takeIf { it.isNotEmpty() }?.joinToString(" · ")
}

/** The API's own text for a value this client has no format for: a string, a number without JSON's quotes, entries. */
private fun rawConflictValue(context: Context, element: JsonElement): String = when (element) {
    is JsonPrimitive -> element.content
    is JsonArray -> element.joinToString(", ") { rawConflictValue(context, it) }
    is JsonObject -> element.entries.joinToString(", ") { (key, value) ->
        "$key: ${rawConflictValue(context, value)}"
    }
}

private fun numberOrNull(element: JsonElement?): Double? =
    (element as? JsonPrimitive)?.takeUnless { it.isString }?.content?.toDoubleOrNull()

private fun stringOrNull(element: JsonElement?): String? =
    (element as? JsonPrimitive)?.takeIf { it.isString }?.content
