// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsBottomTimeGet200ResponseLimits(
    /** Minutes; null when the depth does not limit */
    @SerialName("ndl") val ndl: Double?,
    @SerialName("oxygen") val oxygen: Double?,
    /** Null without a Cylinder */
    @SerialName("gas") val gas: Double?
)
