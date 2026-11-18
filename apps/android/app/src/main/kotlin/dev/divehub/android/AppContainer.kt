package dev.divehub.android

import android.content.Context
import dev.divehub.android.auth.KeystoreTokenStore
import dev.divehub.android.auth.PreferencesInstanceUrlStore
import dev.divehub.android.auth.UserCacheStore
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.TokenStore
import dev.divehub.shared.UserScopedStore
import io.ktor.client.engine.okhttp.OkHttp

/** The app's small object graph: one HTTP engine, the stores, and the session they serve. */
class AppContainer(context: Context) {
    private val application = context.applicationContext

    private val tokens: TokenStore = KeystoreTokenStore(application)
    private val instances: InstanceUrlStore = PreferencesInstanceUrlStore(application)
    private val cache: UserScopedStore = UserCacheStore(application)

    val session = SessionRepository(OkHttp.create(), tokens, instances, cache)
}
