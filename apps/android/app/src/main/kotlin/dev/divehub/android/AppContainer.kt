package dev.divehub.android

import android.content.Context
import dev.divehub.android.auth.KeystoreTokenStore
import dev.divehub.android.auth.PreferencesInstanceUrlStore
import dev.divehub.android.auth.UserCacheStore
import dev.divehub.android.database.AndroidDiveStore
import dev.divehub.android.sync.WorkManagerPeriodicSync
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.TokenStore
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.sync.SseLiveHints
import dev.divehub.shared.sync.SyncEngine
import dev.divehub.shared.sync.SyncRefresher
import dev.divehub.shared.sync.SyncResult
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
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
            // Stop first: a sync still in flight must not write the previous User's rows back after the wipe.
            stopSync()
            cache.forget()
            withContext(Dispatchers.IO) { diveStore.forget() }
        }
    }

    val session = SessionRepository(OkHttp.create(), tokens, instances, userData)

    /** The logbook's and dive page's read sides share the session's HTTP layer (instance URL and bearer token). */
    val logbook = LogbookRepository(session.client)
    val dive = DiveRepository(session.client)

    /** The sync engine and its refresh loop; the UI reads the store, never the API, from D4 on. */
    val sync = SyncEngine(session.client, diveStore)
    private val refresher = SyncRefresher(sync, SseLiveHints(session.client))
    private val periodicSync = WorkManagerPeriodicSync(application)
    private val syncScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var syncing = false

    /**
     * Start syncing for a signed-in User: the initial phases in the background, the live/poll refresh loop, and the
     * periodic worker. Idempotent while signed in; [stopSync] is the matching end on sign-out or a 401.
     */
    fun startSync() {
        if (syncing) return
        syncing = true
        refresher.start(syncScope)
        periodicSync.schedule()
        syncScope.launch {
            if (sync.sync() == SyncResult.SessionEnded) return@launch
            sync.syncProfiles()
        }
    }

    fun stopSync() {
        if (!syncing) return
        syncing = false
        refresher.stop()
        periodicSync.cancel()
        syncScope.coroutineContext.cancelChildren()
    }

    /** One scheduled run (WorkManager): phase 1 and tombstones, only when this device holds a session. */
    suspend fun refreshNow() {
        val url = session.knownInstance() ?: return
        session.client.baseUrl = url
        if (!session.hasSession()) return
        sync.refresh()
    }
}
