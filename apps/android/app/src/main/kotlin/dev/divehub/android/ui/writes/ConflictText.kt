package dev.divehub.android.ui.writes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.divehub.android.R
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

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
        field == "startsAt" || field == "utcOffsetSeconds" -> R.string.conflict_field_start
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
 * One side's value, said plainly: a string as itself, a number without JSON's quotes, an object as its readable
 * entries. A participant removal or an override reset has no JSON value of its own, so its [localAction] carries what
 * happened.
 */
fun conflictValueText(context: Context, element: JsonElement?, localAction: String? = null): String = when {
    localAction == "remove" -> context.getString(R.string.conflict_removed)
    localAction == "reset" -> context.getString(R.string.conflict_reset)
    element == null || element is JsonNull -> context.getString(R.string.conflict_not_set)
    element is JsonPrimitive -> element.content
    element is JsonArray -> element.joinToString(", ") { conflictValueText(context, it) }
    element is JsonObject -> element.entries.joinToString(", ") { (key, value) ->
        "$key: ${conflictValueText(context, value)}"
    }
    else -> element.toString()
}
