package dev.divehub.android.notifications

import android.content.Context
import dev.divehub.api.models.ApiPushConfigGet200ResponseAndroid
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * The small push state that must survive process death: the instance's public Firebase config (so the FCM service can
 * initialize without the UI), the registered row id, the last token and whether the notification permission was asked.
 * Nothing here is a secret; the send credential never leaves the server (ADR 0085).
 */
internal class PushPreferences(context: Context) {
    private val preferences = context.getSharedPreferences("divehub.push", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    var instanceUrl: String?
        get() = preferences.getString(KEY_INSTANCE, null)
        private set(value) {
            preferences.edit().putString(KEY_INSTANCE, value).apply()
        }

    var deviceId: String?
        get() = preferences.getString(KEY_DEVICE_ID, null)
        private set(value) {
            preferences.edit().putString(KEY_DEVICE_ID, value).apply()
        }

    var lastToken: String?
        get() = preferences.getString(KEY_LAST_TOKEN, null)
        private set(value) {
            preferences.edit().putString(KEY_LAST_TOKEN, value).apply()
        }

    var permissionAsked: Boolean
        get() = preferences.getBoolean(KEY_PERMISSION_ASKED, false)
        private set(value) {
            preferences.edit().putBoolean(KEY_PERMISSION_ASKED, value).apply()
        }

    fun config(): ApiPushConfigGet200ResponseAndroid? {
        val stored = preferences.getString(KEY_CONFIG, null) ?: return null
        return try {
            json.decodeFromString<ApiPushConfigGet200ResponseAndroid>(stored)
        } catch (_: Exception) {
            null
        }
    }

    /** Keep the public config for [instanceUrl]; a different instance replaces the previous one's state. */
    fun saveConfig(instanceUrl: String, config: ApiPushConfigGet200ResponseAndroid) {
        if (instanceUrl != this.instanceUrl) clearRegistration()
        this.instanceUrl = instanceUrl
        preferences.edit().putString(KEY_CONFIG, json.encodeToString(config)).apply()
    }

    /** Push is off on the instance: forget the Firebase config so no stale project is initialized. */
    fun clearConfig() {
        preferences.edit().remove(KEY_CONFIG).remove(KEY_INSTANCE).apply()
        deviceId = null
    }

    fun saveDeviceId(id: String) {
        deviceId = id
    }

    fun clearDeviceId() {
        deviceId = null
    }

    fun saveToken(token: String) {
        lastToken = token
    }

    fun markPermissionAsked() {
        permissionAsked = true
    }

    /** A new instance URL starts clean: another server's row, token and config must not be reused. */
    fun clearRegistration() {
        preferences.edit().remove(KEY_DEVICE_ID).remove(KEY_LAST_TOKEN).apply()
    }

    private companion object {
        const val KEY_INSTANCE = "instance_url"
        const val KEY_CONFIG = "firebase_client_config"
        const val KEY_DEVICE_ID = "push_device_id"
        const val KEY_LAST_TOKEN = "last_token"
        const val KEY_PERMISSION_ASKED = "permission_asked"
    }
}
