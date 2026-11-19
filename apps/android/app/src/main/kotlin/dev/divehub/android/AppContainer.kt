package dev.divehub.android

import android.content.Context
import dev.divehub.android.auth.KeystoreTokenStore
import dev.divehub.android.auth.PreferencesInstanceUrlStore
import dev.divehub.android.auth.UserCacheStore
import dev.divehub.android.database.AndroidDiveStore
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.TokenStore
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.UserScopedStore
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** The app's small object graph: one HTTP engine, the stores, and the session they serve. */
class AppContainer(context: Context) {
    private val application = context.applicationContext

    private val tokens: TokenStore = KeystoreTokenStore(application)
    private val instances: InstanceUrlStore = PreferencesInstanceUrlStore(application)
    private val cache = UserCacheStore(application)

    /** Opened on first use; the logbook screens read it from D4 on. */
    val diveStore: DiveStore by lazy { AndroidDiveStore(application).store }

    /** A change of User drops both the cache and the offline read store (client contract §1 Security). */
    private val userData = object : UserScopedStore {
        override suspend fun forget() {
            cache.forget()
            withContext(Dispatchers.IO) { diveStore.forget() }
        }
    }

    val session = SessionRepository(OkHttp.create(), tokens, instances, userData)

    /** The logbook's and dive page's read sides share the session's HTTP layer (instance URL and bearer token). */
    val logbook = LogbookRepository(session.client)
    val dive = DiveRepository(session.client)
}
