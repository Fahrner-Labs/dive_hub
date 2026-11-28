package dev.divehub.shared.push

import dev.divehub.api.models.ApiDevicesPost200Response
import dev.divehub.api.models.ApiDevicesPostRequest
import dev.divehub.api.models.ApiPushConfigGet200Response
import dev.divehub.api.models.ApiPushConfigGet200ResponseAndroid
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DecodeException
import io.ktor.http.encodeURLParameter
import io.ktor.http.isSuccess
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * The registration half of push (ADR 0085): the public Firebase client config an instance serves and the
 * session-scoped device row a native client keeps while it is signed in. Hints only; nothing here carries Dive
 * content, and the server's send credential never reaches this module.
 */
class PushRegistration(
    private val api: ApiClient,
    private val json: Json = Json { ignoreUnknownKeys = true; explicitNulls = false; encodeDefaults = false },
) {
    /**
     * `GET /api/push/config`: the Android Firebase values, or null when this instance has push off. A 404 is an older
     * server without the endpoint and means the same as `{}`: keep polling. A body that does not decode is treated the
     * same way, so a broken optional endpoint can never take the app offline.
     */
    suspend fun androidConfig(): ApiPushConfigGet200ResponseAndroid? {
        val response = api.get(PUSH_CONFIG_PATH, authenticated = false, retry = false)
        if (response.status.value == 404) return null
        if (!response.status.isSuccess()) throw api.failure(response)
        return try {
            api.decode<ApiPushConfigGet200Response>(response).android
        } catch (_: DecodeException) {
            null
        }
    }

    /** Register or refresh this install's FCM token for the signed-in User and session; returns the row to remove. */
    suspend fun register(pushToken: String): ApiDevicesPost200Response {
        val body = json.encodeToString(ApiDevicesPostRequest(platform = PLATFORM, pushToken = pushToken))
        val response = api.post(DEVICES_PATH, body)
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode<ApiDevicesPost200Response>(response)
    }

    /** Remove this install's registration on sign-out. A row already gone answers 404, which is the same outcome. */
    suspend fun unregister(id: String) {
        val response = api.delete("$DEVICES_PATH/${id.encodeURLParameter()}")
        if (!response.status.isSuccess() && response.status.value != 404) throw api.failure(response)
    }

    private companion object {
        const val PUSH_CONFIG_PATH = "/api/push/config"
        const val DEVICES_PATH = "/api/devices"
        const val PLATFORM = "android"
    }
}
