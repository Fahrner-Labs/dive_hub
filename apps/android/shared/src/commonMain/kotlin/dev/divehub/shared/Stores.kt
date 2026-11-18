package dev.divehub.shared

/** The session token, kept in the Android Keystore on the device (ADR 0074). */
interface TokenStore {
    suspend fun load(): String?
    suspend fun save(token: String)
    suspend fun clear()
}

/** The self-hosted instance's address, typed once at first start (ADR 0074). It is not a secret. */
interface InstanceUrlStore {
    suspend fun load(): String?
    suspend fun save(url: String)
}

/**
 * Everything cached for the signed-in User. It is dropped on sign-in, sign-out and a 401, so the next person at a
 * shared device sees nothing of the previous one (client contract §1 Security, a must).
 */
interface UserScopedStore {
    suspend fun forget()
}
