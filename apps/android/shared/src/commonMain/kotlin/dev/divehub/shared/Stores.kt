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

    /** Forget the address when the User changes instance; the next start asks for one again. */
    suspend fun clear()
}

/**
 * Everything cached for the signed-in User. It is dropped on sign-in, sign-out and any other change of User, so the
 * next person at a shared device sees nothing of the previous one (client contract §1 Security, a must). A 401 is not
 * a change of User: a revoked session keeps the read store readable offline (client contract, Offline reads).
 */
interface UserScopedStore {
    suspend fun forget()
}

/**
 * The last successful `GET /api/me` answer, as the JSON the API sent, so an offline cold start can show the
 * signed-in User rather than the sign-in screen (FAH-419). It follows the read store's scoping rules: a change of
 * User drops it, and a 401 that ends the session keeps it (client contract, Offline reads).
 */
interface CachedMeStore {
    suspend fun load(): String?
    suspend fun save(meJson: String)
    suspend fun clear()
}
