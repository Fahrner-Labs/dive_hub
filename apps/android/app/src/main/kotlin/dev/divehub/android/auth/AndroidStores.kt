package dev.divehub.android.auth

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.TokenStore
import dev.divehub.shared.UserScopedStore
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** The instance URL is not a secret; it stays in the app's private preferences. */
class PreferencesInstanceUrlStore(context: Context) : InstanceUrlStore {
    private val preferences = context.getSharedPreferences("divehub.settings", Context.MODE_PRIVATE)

    override suspend fun load(): String? = withContext(Dispatchers.IO) {
        preferences.getString(KEY_INSTANCE, null)
    }

    override suspend fun save(url: String): Unit = withContext(Dispatchers.IO) {
        preferences.edit().putString(KEY_INSTANCE, url).apply()
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) {
        preferences.edit().remove(KEY_INSTANCE).apply()
    }

    private companion object {
        const val KEY_INSTANCE = "instance_url"
    }
}

/**
 * The session token encrypted with an AES-GCM key that never leaves the Android Keystore (ADR 0074): the
 * preferences hold only the IV and ciphertext.
 */
class KeystoreTokenStore(context: Context) : TokenStore {
    private val preferences = context.getSharedPreferences("divehub.auth", Context.MODE_PRIVATE)

    override suspend fun load(): String? = withContext(Dispatchers.IO) {
        val stored = preferences.getString(KEY_TOKEN, null) ?: return@withContext null
        runCatching { decrypt(stored) }.getOrNull()
    }

    override suspend fun save(token: String): Unit = withContext(Dispatchers.IO) {
        preferences.edit().putString(KEY_TOKEN, encrypt(token)).apply()
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) {
        preferences.edit().remove(KEY_TOKEN).apply()
    }

    private fun secretKey(): SecretKey {
        val store = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (store.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.secretKey?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        generator.init(
            KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build(),
        )
        return generator.generateKey()
    }

    private fun encrypt(token: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey())
        val encrypted = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(cipher.iv + encrypted, Base64.NO_WRAP)
    }

    private fun decrypt(stored: String): String {
        val bytes = Base64.decode(stored, Base64.NO_WRAP)
        require(bytes.size > IV_BYTES) { "stored token is truncated" }
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey(), GCMParameterSpec(TAG_BITS, bytes.copyOfRange(0, IV_BYTES)))
        return String(cipher.doFinal(bytes.copyOfRange(IV_BYTES, bytes.size)), Charsets.UTF_8)
    }

    private companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val KEY_ALIAS = "divehub.session-token"
        const val KEY_TOKEN = "session_token"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val IV_BYTES = 12
        const val TAG_BITS = 128
    }
}

/**
 * Everything the app cached for the signed-in User. Sign-in and sign-out call this before the next User sees a screen
 * (client contract §1 Security); there is no response cache in this slice, so the preferences and cache directory are
 * the whole of it.
 */
class UserCacheStore(private val context: Context) : UserScopedStore {
    override suspend fun forget(): Unit = withContext(Dispatchers.IO) {
        context.getSharedPreferences("divehub.cache", Context.MODE_PRIVATE).edit().clear().commit()
        context.cacheDir.listFiles()?.forEach { it.deleteRecursively() }
    }
}
