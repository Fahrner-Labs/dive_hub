package dev.divehub.android.ui

import dev.divehub.shared.CachedMeStore
import dev.divehub.shared.InstanceProbeResult
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.PendingWriteQueue
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.TokenStore
import dev.divehub.shared.UserScopedStore
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private class WarningTokens(private var token: String? = "token") : TokenStore {
    override suspend fun load(): String? = token
    override suspend fun save(token: String) {
        this.token = token
    }

    override suspend fun clear() {
        token = null
    }
}

private class WarningInstances(private var value: String? = "https://dive.example") : InstanceUrlStore {
    override suspend fun load(): String? = value
    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

private class WarningCache : UserScopedStore {
    override suspend fun forget() = Unit
}

private class WarningCachedMe(private var stored: String? = null) : CachedMeStore {
    override suspend fun load(): String? = stored
    override suspend fun save(meJson: String) {
        stored = meJson
    }

    override suspend fun clear() {
        stored = null
    }
}

private class WarningQueue(var count: Long = 0) : PendingWriteQueue {
    override suspend fun queuedWriteCount(): Long = count
}

private const val WARNING_ME_JSON =
    """{"user":{"id":"user-1","email":"elena@example.com","name":"Elena","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"diver-1","name":"Elena"},"preferences":{"language":null,"units":"metric"}}"""

private const val OTHER_ME_JSON =
    """{"user":{"id":"user-2","email":"other@example.com","name":"Other","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"diver-2","name":"Other"},"preferences":{"language":null,"units":"metric"}}"""

/** The D8 session guard (FAH-466): a non-empty queue blocks sign-out until *Sync now* or *Discard*. */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelPendingWarningTest {
    @Before
    fun setUp() = Dispatchers.setMain(Dispatchers.Default)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `signing out with queued writes asks before ending the session`() = runBlocking {
        val (viewModel, _) = signedInViewModel(WarningQueue(2))
        awaitUser(viewModel)

        viewModel.signOut()
        val warning = awaitWarning(viewModel)

        assertEquals(2L, warning.count)
        assertEquals(false, warning.changeInstance)
    }

    @Test
    fun `sync now drains and stays signed in so the next sign-out is a fresh choice`() = runBlocking {
        val queue = WarningQueue(2)
        val (viewModel, drains) = signedInViewModel(queue, drain = { queue.count = 0 })
        awaitUser(viewModel)
        viewModel.signOut()
        awaitWarning(viewModel)

        viewModel.syncPendingWarning()
        withTimeout(5_000) { viewModel.state.first { it.pendingWarning == null } }

        assertEquals(1, drains.value)
        assertTrue(viewModel.state.value.user != null)

        viewModel.signOut()
        awaitSignedOut(viewModel)
        assertNull(viewModel.state.value.pendingWarning)
    }

    @Test
    fun `a queue that still waits keeps the warning and says so`() = runBlocking {
        val (viewModel, _) = signedInViewModel(WarningQueue(2), drain = {})
        awaitUser(viewModel)
        viewModel.signOut()
        awaitWarning(viewModel)

        viewModel.syncPendingWarning()
        val warning = withTimeout(5_000) {
            viewModel.state.first { it.pendingWarning is PendingChangeWarning.SignOut && it.pendingWarning.stillWaiting }
        }.pendingWarning as PendingChangeWarning.SignOut

        assertEquals(2L, warning.count)
        assertTrue(warning.stillWaiting)
    }

    @Test
    fun `discarding pending writes signs out without a second warning`() = runBlocking {
        val (viewModel, _) = signedInViewModel(WarningQueue(2))
        awaitUser(viewModel)
        viewModel.signOut()
        awaitWarning(viewModel)

        viewModel.discardPendingAndLeave()
        awaitSignedOut(viewModel)

        assertNull(viewModel.state.value.pendingWarning)
        assertNull(viewModel.state.value.user)
    }

    @Test
    fun `signing in as another account warns that the queued writes belong to the previous one`() = runBlocking {
        val queue = WarningQueue(2)
        val session = SessionRepository(
            switchEngine(),
            WarningTokens(token = null),
            WarningInstances(),
            WarningCache(),
            WarningCachedMe(WARNING_ME_JSON),
            versionName = "0.1.0-dev",
            pendingWrites = queue,
        )
        val viewModel = MainViewModel(
            session,
            startSync = {},
            stopSync = {},
            probeInstance = { InstanceProbeResult.Reachable },
        )
        withTimeout(5_000) { viewModel.state.first { !it.loading && it.instanceUrl != null } }

        viewModel.signIn("other@example.com", "password")
        val warning = withTimeout(5_000) {
            viewModel.state.first { it.pendingWarning is PendingChangeWarning.SignIn }
        }.pendingWarning as PendingChangeWarning.SignIn

        assertEquals(2L, warning.count)
        assertEquals("other@example.com", warning.email)

        viewModel.discardPendingAndSignIn()
        val signedIn = withTimeout(5_000) { viewModel.state.first { it.user?.id == "user-2" } }

        assertNull(signedIn.pendingWarning)
        assertEquals("other@example.com", signedIn.user?.email)
    }

    @Test
    fun `changing the instance keeps the address only after the queue settles`() = runBlocking {
        val (viewModel, _) = signedInViewModel(WarningQueue(1))
        awaitUser(viewModel)

        viewModel.signOutAndChangeInstance()
        val warning = awaitWarning(viewModel)

        assertTrue(warning.changeInstance)
        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
    }

    private suspend fun awaitUser(viewModel: MainViewModel) {
        withTimeout(5_000) { viewModel.state.first { it.user != null && !it.loading } }
    }

    private suspend fun awaitWarning(viewModel: MainViewModel): PendingChangeWarning.SignOut {
        val state = withTimeout(5_000) {
            viewModel.state.first { it.pendingWarning is PendingChangeWarning.SignOut }
        }
        return state.pendingWarning as PendingChangeWarning.SignOut
    }

    private suspend fun awaitSignedOut(viewModel: MainViewModel) {
        withTimeout(5_000) { viewModel.state.first { it.user == null && !it.loading } }
    }

    private fun signedInViewModel(
        queue: WarningQueue,
        drain: suspend () -> Unit = {},
    ): Pair<MainViewModel, IntHolder> {
        val session = SessionRepository(
            engine(),
            WarningTokens(),
            WarningInstances(),
            WarningCache(),
            WarningCachedMe(),
            versionName = "0.1.0-dev",
            pendingWrites = queue,
        )
        val drains = IntHolder()
        val viewModel = MainViewModel(
            session,
            startSync = {},
            stopSync = {},
            drainWrites = {
                drains.value += 1
                drain()
            },
            probeInstance = { InstanceProbeResult.Reachable },
        )
        return viewModel to drains
    }

    private fun engine() = MockEngine { request ->
        val body = if (request.url.encodedPath == "/api/me") WARNING_ME_JSON else "{}"
        respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
    }

    private fun switchEngine() = MockEngine { request ->
        when (request.url.encodedPath) {
            "/api/me" -> respond(
                OTHER_ME_JSON,
                HttpStatusCode.OK,
                headersOf(HttpHeaders.ContentType, "application/json"),
            )
            "/api/auth/sign-in/email" -> respond(
                """{"token":"session"}""",
                HttpStatusCode.OK,
                headersOf(
                    HttpHeaders.ContentType to listOf("application/json"),
                    "set-auth-token" to listOf("session"),
                ),
            )
            else -> respond("{}", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }
    }

    private class IntHolder {
        var value = 0
    }
}
