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
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.sync.SseLiveHints
import dev.divehub.shared.sync.SyncEngine
import dev.divehub.shared.sync.SyncRefresher
import dev.divehub.shared.sync.SyncResult
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
            // Stop first: a sync still in flight must not write the previous User's rows back after the wipe. The
            // scheduled worker runs outside `syncScope`, so it shares `wipeLock` with the wipe instead.
            stopSyncAndJoin()
            wipeLock.withLock {
                cache.forget()
                withContext(Dispatchers.IO) { diveStore.forget() }
            }
        }
    }

    /**
     * The live hint stream is silent between hints (the server's heartbeat is 15 s), so the read timeout must sit
     * above it or every stream would look dropped (ADR 0066). This applies to ordinary requests too, harmlessly.
     */
    private val httpEngine = OkHttp.create {
        config {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }
    }

    val session = SessionRepository(httpEngine, tokens, instances, userData)

    /** The logbook's and dive page's read sides share the session's HTTP layer (instance URL and bearer token). */
    val logbook = LogbookRepository(session.client)
    val dive = DiveRepository(session.client)

    /** The D4 read side: the list, one Dive, its neighbours and its profiles come from the store, never a request. */
    val storeLogbook = StoreLogbook(diveStore)

    /** The sync engine and its refresh loop; the UI reads the store, never the API, from D4 on. */
    val sync = SyncEngine(session.client, diveStore)
    private val refresher = SyncRefresher(sync, SseLiveHints(session.client))

    /** Open screens reread the store when a sync or hint writes it, and say "cached" when the last attempt got no answer. */
    val syncRevision = refresher.revision
    val syncOffline = refresher.offline
    private val periodicSync = WorkManagerPeriodicSync(application)
    private val syncScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val wipeLock = Mutex()
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
            val result = sync.sync()
            if (result == SyncResult.SessionEnded) return@launch
            refresher.initialFinished(result)
            refresher.profilesOnce()
        }
    }

    fun stopSync() {
        if (!syncing) return
        syncing = false
        refresher.stop()
        periodicSync.cancel()
        syncScope.coroutineContext.cancelChildren()
    }

    /** Stop and wait for the sync jobs; a wipe must not run while a page or hint is still being written. */
    private suspend fun stopSyncAndJoin() {
        stopSync()
        val job = syncScope.coroutineContext[Job] ?: return
        job.children.toList().joinAll()
    }

    /**
     * One scheduled run (WorkManager): phase 1, tombstones and pending profiles, only when this device holds a
     * session. [wipeLock] keeps it from writing after a sign-out wipe.
     */
    suspend fun refreshNow() {
        val url = session.knownInstance() ?: return
        session.client.baseUrl = url
        if (!session.hasSession()) return
        wipeLock.withLock {
            // Re-checked inside the lock: a sign-out may have cleared the token while this run waited.
            if (!session.hasSession()) return@withLock
            refresher.refreshOnce()
            refresher.profilesOnce()
        }
    }
}
