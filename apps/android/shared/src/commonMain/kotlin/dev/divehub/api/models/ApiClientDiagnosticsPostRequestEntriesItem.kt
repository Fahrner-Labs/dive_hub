// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiClientDiagnosticsPostRequestEntriesItem(
    @SerialName("occurredAt") val occurredAt: String,
    @SerialName("level") val level: String,
    /** A stable client code; clients translate what they know */
    @SerialName("code") val code: String? = null,
    /** The build the entry was captured in; older clients omit it and the upload's client.version is used */
    @SerialName("clientVersion") val clientVersion: String? = null,
    @SerialName("message") val message: String,
    @SerialName("stack") val stack: String? = null,
    /** Client context; the server re-redacts keys such as token and strings such as e-mail addresses */
    @SerialName("context") val context: Map<String, String>? = null
)
