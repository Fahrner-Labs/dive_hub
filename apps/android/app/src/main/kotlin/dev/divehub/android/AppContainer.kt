package dev.divehub.android

import android.content.Context
import dev.divehub.android.auth.KeystoreTokenStore
import dev.divehub.android.auth.PreferencesCachedMeStore
import dev.divehub.android.auth.PreferencesInstanceUrlStore
import dev.divehub.android.auth.UserCacheStore
import dev.divehub.android.database.AndroidDiveStore
import dev.divehub.android.sync.WorkManagerPeriodicSync
import dev.divehub.shared.CachedMeStore
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.PendingWriteQueue
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.TokenStore
import dev.divehub.shared.diagnostics.Diagnostics
import dev.divehub.shared.diagnostics.DiagnosticsBuffer
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.diagnostics.DiagnosticLevel
import dev.divehub.shared.settings.SettingsRepository
import dev.divehub.shared.sync.SseLiveHints
import dev.divehub.shared.sync.SyncEngine
import dev.divehub.shared.sync.SyncRefresher
import dev.divehub.shared.sync.SyncResult
import dev.divehub.shared.write.ConflictResolution
import dev.divehub.shared.write.ConflictSide
import dev.divehub.shared.write.WriteCapability
import dev.divehub.shared.write.WriteQueue
import dev.divehub.shared.write.WriteReplayer
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    private val cachedMe: CachedMeStore = PreferencesCachedMeStore(application)
    private val cache = UserCacheStore(application)

    /** The one database file, opened on first use: the read store and the diagnostics ring buffer. */
    private val androidStore: AndroidDiveStore by lazy { AndroidDiveStore(application) }

    /** Opened on first use; the logbook screens read it from D4 on. */
    val diveStore: DiveStore get() = androidStore.store

    /** The always-on local error buffer (ADR 0080 D1); the settings Debug section consumes it in FAH-318. */
    internal val diagnosticsBuffer: DiagnosticsBuffer get() = androidStore.diagnosticsBuffer

    /** A change of User drops both the cache and the offline read store (client contract §1 Security). */
    private val userData = object : UserScopedStore {
        override suspend fun forget() {
            // Stop first: a sync still in flight must not write the previous User's rows back after the wipe. The
            // scheduled worker runs outside `syncScope`, so it shares `wipeLock` with the wipe instead.
            stopSyncAndJoin()
            wipeLock.withLock {
                cache.forget()
                withContext(Dispatchers.IO) {
                    diveStore.forget()
                    diagnosticsBuffer.forget()
                }
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

    val session = SessionRepository(
        httpEngine,
        tokens,
        instances,
        userData,
        cachedMe,
        versionName = BuildConfig.VERSION_NAME,
        pendingWrites = object : PendingWriteQueue {
            override suspend fun queuedWriteCount(): Long =
                withContext(Dispatchers.IO) { diveStore.queuedWriteCount() }
        },
    )

    /**
     * The offline write queue: screens enqueue here and the store shows the edit at once (ADR 0084 D4). [queueRevision]
     * lets the pending indicator re-read the queue after an edit, before any sync trigger runs. The scanner's actions
     * replay through it too; T7 owns the session/sync triggers that drain it, and until then the scanner triggers its
     * own replay.
     */
    private val _queueRevision = MutableStateFlow(0L)
    val queueRevision: StateFlow<Long> = _queueRevision.asStateFlow()
    val writeQueue: WriteQueue by lazy {
        WriteQueue(diveStore, onEnqueued = { _queueRevision.value = _queueRevision.value + 1 })
    }

    /** The replay/conflict side: drains the queue on sync triggers and answers the resolution surface (ADR 0084 D3). */
    val writeReplayer: WriteReplayer by lazy { WriteReplayer(session.client, diveStore) }

    /** The logbook's and dive page's read sides share the session's HTTP layer (instance URL and bearer token). */
    val logbook = LogbookRepository(session.client)

    /** Capture, export and report-a-problem upload (ADR 0080); the crash hook records into the same facade. */
    val diagnostics: Diagnostics by lazy {
        Diagnostics(diagnosticsBuffer, session.client, BuildConfig.VERSION_NAME)
    }
    val dive = DiveRepository(session.client)

    /** The D4 read side: the list, one Dive, its neighbours and its profiles come from the store, never a request. */
    val storeLogbook = StoreLogbook(diveStore)

    /** D7's capability probe: only an instance that answers the feed gets the write UI at all. */
    val writeCapability = WriteCapability(session.client)
    val writeable = writeCapability.writeable

    /** What the settings screen reads: the stored instance address and the sync state the logbook also reads. */
    val settings: SettingsRepository by lazy { SettingsRepository(instances, diveStore) }

    /** The sync engine and its refresh loop; the UI reads the store, never the API, from D4 on. */
    val sync = SyncEngine(session.client, diveStore)
    private val refresher = SyncRefresher(
        sync,
        SseLiveHints(session.client),
        onStreamFailure = { failure ->
            // ADR 0080: name why the stream could not connect (404/503/429/timeout) so a field report is diagnosable.
            diagnostics.capture(
                level = DiagnosticLevel.WARN,
                code = failure.code,
                message = failure.message,
                context = buildMap {
                    failure.status?.let { put("status", it.toString()) }
                },
            )
        },
        writes = writeReplayer,
    )

    /** Open screens reread the store when a sync or hint writes it, and say "cached" when the last attempt got no answer. */
    val syncRevision = refresher.revision
    val syncProgress = refresher.progress
    val syncOffline = refresher.offline
    val syncConnected = refresher.streamHealthy
    /** Dropped-change notices from the last replay, until the User reads them (ADR 0084 D3 step 4). */
    val writeNotices = refresher.notices
    private val periodicSync = WorkManagerPeriodicSync(application)
    private val syncScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    // The container outlives every session, so a UI-only combination must not sit in `syncScope` (which a sign-out
    // cancels): a later sign-in would otherwise keep a stale value.
    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val wipeLock = Mutex()
    private var syncing = false

    /**
     * Whether the live stream is answering: online-only actions (the destructive verbs) are offered while it is true.
     * The offline flag wins, so a dropped poll shows them as unavailable even before the stream notices.
     */
    val online: StateFlow<Boolean> = combine(refresher.streamHealthy, refresher.offline) { connected, offline ->
        connected && !offline
    }.stateIn(uiScope, SharingStarted.Eagerly, false)

    /** A local edit reached the store (the queue's optimistic write): open screens reread it without a sync. */
    fun noteStoreWrite() = refresher.noteStoreWrite()

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
            // D7: one capability probe per session, so a pre-feed server hides the write UI instead of failing edits.
            val url = session.knownInstance()
            if (url != null) {
                session.client.baseUrl = url
                if (session.hasSession()) writeCapability.probe()
            }
            val result = sync.sync()
            if (result == SyncResult.SessionEnded) return@launch
            refresher.initialFinished(result)
            // A resumed same-User session may already hold queued writes (ADR 0084 D8); replay them before the profiles.
            refresher.drainWritesNow()
            refresher.profilesOnce()
        }
        // A cold start with no answer must not hide writes for the whole session: ask again once the stream answers.
        syncScope.launch {
            online.collect { connected ->
                if (connected && writeCapability.writeable.value == null) writeCapability.probe()
            }
        }
    }

    fun stopSync() {
        if (!syncing) return
        syncing = false
        writeCapability.reset()
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

    /** The dive page's refresh: that Dive's detail and profiles, only when this device holds a session. */
    suspend fun refreshDiveNow(id: String) {
        val url = session.knownInstance() ?: return
        session.client.baseUrl = url
        if (!session.hasSession()) return
        wipeLock.withLock {
            if (!session.hasSession()) return@withLock
            refresher.refreshDiveOnce(id)
            refresher.profilesOnce()
        }
    }

    /**
     * *Sync now* from the sign-out warning (ADR 0084 D8): replay the queue without waiting for the poll or worker. The
     * session stays signed in; the caller re-reads the queue size to decide whether sign-out may proceed.
     */
    suspend fun drainWrites() {
        val url = session.knownInstance() ?: return
        session.client.baseUrl = url
        if (!session.hasSession()) return
        wipeLock.withLock {
            if (!session.hasSession()) return@withLock
            refresher.drainWritesNow()
        }
    }

    /** *Try again* on the pending indicator: failed entries rejoin the queue, then one replay attempt runs. */
    suspend fun retryWrites() {
        val url = session.knownInstance() ?: return
        session.client.baseUrl = url
        if (!session.hasSession()) return
        wipeLock.withLock {
            if (!session.hasSession()) return@withLock
            withContext(Dispatchers.IO) { writeReplayer.retryFailed() }
            refresher.drainWritesNow()
        }
    }

    /**
     * The queue screen's conflict decision (ADR 0084 D3): [wipeLock] and the session check keep a sign-out wipe from
     * interleaving with re-queueing the User's choices, like every other store write.
     */
    suspend fun resolveWriteConflict(entryId: Long, choices: Map<String, ConflictSide>): ConflictResolution? {
        val url = session.knownInstance() ?: return null
        session.client.baseUrl = url
        if (!session.hasSession()) return null
        return wipeLock.withLock {
            if (!session.hasSession()) return@withLock null
            withContext(Dispatchers.IO) { writeReplayer.resolve(entryId, choices) }
        }
    }

    /** The User has read the dropped-change notices (ADR 0084 D3 step 4). */
    fun dismissWriteNotices() {
        refresher.dismissNotices()
    }

    /**
     * One scheduled run (WorkManager): phase 1, tombstones and pending profiles, only when this device holds a
     * session. [wipeLock] keeps it from writing after a sign-out wipe. [SyncRefresher.refreshOnce] also drains the
     * offline write queue (ADR 0084 D3), so a background run sends pending edits, not only reads.
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
