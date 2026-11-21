package dev.divehub.android.ui.logbook

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewCounts
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewTotals
import dev.divehub.shared.ApiClient
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.sync.SyncProgress
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.coroutines.CoroutineContext

/** The logbook's state holder: the restored page, and a sync's writes never take the screen over (FAH-316, FAH-333). */
@OptIn(ExperimentalCoroutinesApi::class)
class LogbookViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `start on a restored page opens that page`() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            val viewModel = viewModel(StoreLogbook(store))

            viewModel.start("diver-1", page = 3)

            assertEquals(3, viewModel.state.value.page)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `sync writes reread the list without the pull-to-refresh spinner`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision = revision)
            viewModel.start("diver-1")
            advanceUntilIdle()
            assertNotNull(viewModel.state.value.data)
            val states = mutableListOf<LogbookUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { states += it }
            }
            val opened = states.size

            store.upsertDive(dive("dive-2", "2026-10-01T10:00:00.000Z"))
            for (value in 1L..5L) {
                revision.value = value
                advanceTimeBy(100)
            }
            advanceUntilIdle()

            assertEquals(2, viewModel.state.value.data?.total)
            val followed = states.drop(opened)
            assertTrue(followed.isNotEmpty())
            assertTrue(followed.none { it.refreshing || it.loading })
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `only the user gesture turns the spinner on`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val revision = MutableStateFlow(0L)
            val gate = CompletableDeferred<Unit>()
            val viewModel = viewModel(StoreLogbook(store), revision = revision, refreshSync = { gate.await() })
            viewModel.start("diver-1")
            advanceUntilIdle()

            viewModel.refresh()
            runCurrent()
            assertTrue(viewModel.state.value.refreshing)

            gate.complete(Unit)
            advanceUntilIdle()
            assertFalse(viewModel.state.value.refreshing)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a write during an error page keeps it renderable`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            val revision = MutableStateFlow(0L)
            var calls = 0
            val viewModel = viewModel(StoreLogbook(store), revision = revision, api = failingApi { calls += 1 })
            viewModel.start("diver-1")
            advanceUntilIdle()
            val states = mutableListOf<LogbookUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { states += it }
            }

            viewModel.toggle(LogbookFilter.CHANGED_SINCE_SENT)
            awaitState { viewModel.state.value.error != null }
            advanceUntilIdle()
            assertEquals("server_error", viewModel.state.value.error)
            assertNull(viewModel.state.value.data)

            revision.value = 1
            advanceTimeBy(300)
            awaitState { calls >= 2 }
            advanceUntilIdle()

            assertEquals("server_error", viewModel.state.value.error)
            assertNull(viewModel.state.value.data)
            assertTrue(states.none { it.data == null && it.error == null && !it.loading && !it.offline })
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a follow read replaces the read in flight`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            val gate = CompletableDeferred<Unit>()
            var calls = 0
            val headers = headersOf(HttpHeaders.ContentType, "application/json")
            val engine = MockEngine { request ->
                when (request.url.encodedPath) {
                    "/api/providers", "/api/connections" -> respond("[]", HttpStatusCode.OK, headers)
                    "/api/dives" -> {
                        calls += 1
                        if (calls == 1) {
                            gate.await()
                            respond(listJson("stale"), HttpStatusCode.OK, headers)
                        } else {
                            respond(listJson("fresh"), HttpStatusCode.OK, headers)
                        }
                    }
                    else -> respond("{}", HttpStatusCode.OK, headers)
                }
            }
            val api = ApiClient(engine, "test", { null }, {}, Json { ignoreUnknownKeys = true }).also {
                it.baseUrl = "http://test"
            }
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision = revision, api = api)
            viewModel.start("diver-1")
            advanceUntilIdle()

            viewModel.toggle(LogbookFilter.CHANGED_SINCE_SENT)
            awaitState { calls == 1 }

            revision.value = 1
            advanceTimeBy(300)
            awaitState { viewModel.state.value.data?.dives?.firstOrNull()?.id == "fresh" }

            gate.complete(Unit)
            awaitState { calls == 2 }
            advanceUntilIdle()

            assertEquals("fresh", viewModel.state.value.data?.dives?.firstOrNull()?.id)
            assertEquals(2, calls)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a write during the gesture's read costs exactly one more read`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val counting = CountingDispatcher(dispatcher)
            val refreshGate = CompletableDeferred<Unit>()
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(
                StoreLogbook(store),
                revision = revision,
                refreshSync = { refreshGate.await() },
                api = providerApi(),
                ioDispatcher = counting,
            )
            viewModel.start("diver-1")
            advanceUntilIdle()
            awaitState { viewModel.state.value.providers.names.isNotEmpty() }
            counting.reset()

            viewModel.refresh()
            runCurrent()
            assertTrue(viewModel.state.value.refreshing)

            counting.hold()
            refreshGate.complete(Unit)
            runCurrent()
            assertEquals(1, counting.count)

            store.upsertDive(dive("dive-2", "2026-10-01T10:00:00.000Z"))
            revision.value = 1
            counting.releaseHeld()
            advanceUntilIdle()

            assertEquals(2, counting.count)
            assertEquals(2, viewModel.state.value.data?.total)
            assertFalse(viewModel.state.value.refreshing)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a filter read in flight keeps the last counts`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val counting = CountingDispatcher(dispatcher)
            val viewModel = viewModel(StoreLogbook(store), api = api(), ioDispatcher = counting)
            viewModel.start("diver-1")
            advanceUntilIdle()
            val counts = viewModel.state.value.data!!.counts
            assertEquals(counts, viewModel.state.value.lastCounts)

            counting.hold()
            viewModel.toggle(LogbookFilter.NO_RECORDING)
            runCurrent()

            assertNull(viewModel.state.value.data)
            assertFalse(viewModel.state.value.loading)
            assertEquals(counts, viewModel.state.value.lastCounts)

            counting.releaseHeld()
            advanceUntilIdle()

            val data = viewModel.state.value.data!!
            assertEquals(data.counts, viewModel.state.value.lastCounts)
            assertEquals(1, data.counts.noRecording)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `an unanswered server filter keeps the last counts and says the filter is unavailable`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val gate = CompletableDeferred<Unit>()
            var started = false
            val headers = headersOf(HttpHeaders.ContentType, "application/json")
            val engine = MockEngine { request ->
                when (request.url.encodedPath) {
                    "/api/providers", "/api/connections" -> respond("[]", HttpStatusCode.OK, headers)
                    "/api/dives" -> {
                        started = true
                        gate.await()
                        throw IOException("offline")
                    }
                    else -> respond("{}", HttpStatusCode.OK, headers)
                }
            }
            val api = ApiClient(engine, "test", { null }, {}, Json { ignoreUnknownKeys = true }).also {
                it.baseUrl = "http://test"
            }
            val viewModel = viewModel(StoreLogbook(store), api = api)
            viewModel.start("diver-1")
            advanceUntilIdle()
            val counts = viewModel.state.value.data!!.counts
            assertEquals(counts, viewModel.state.value.lastCounts)

            viewModel.toggle(LogbookFilter.CHANGED_SINCE_SENT)
            awaitState { started }

            assertNull(viewModel.state.value.data)
            assertEquals(counts, viewModel.state.value.lastCounts)

            gate.complete(Unit)
            awaitState { viewModel.state.value.filterUnavailable }

            assertTrue(viewModel.state.value.offline)
            assertNotNull(viewModel.state.value.data)
            assertEquals(counts, viewModel.state.value.lastCounts)
            assertEquals(viewModel.state.value.data!!.counts, viewModel.state.value.lastCounts)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `forget and another user's start drop the last counts`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive("dive-1", "2026-11-01T10:00:00.000Z"))
            val viewModel = viewModel(StoreLogbook(store), api = api())
            viewModel.start("diver-1")
            advanceUntilIdle()
            assertNotNull(viewModel.state.value.lastCounts)

            viewModel.start("diver-2")
            assertNull(viewModel.state.value.lastCounts)

            advanceUntilIdle()
            assertNotNull(viewModel.state.value.lastCounts)
            viewModel.forget()
            assertNull(viewModel.state.value.lastCounts)
        } finally {
            driver.close()
        }
    }

    private fun viewModel(
        store: StoreLogbook,
        revision: MutableStateFlow<Long> = MutableStateFlow(0L),
        refreshSync: suspend () -> Unit = {},
        api: ApiClient = api(),
        ioDispatcher: CoroutineDispatcher = dispatcher,
    ) = LogbookViewModel(
        store = store,
        api = LogbookRepository(api),
        revision = revision,
        offlineState = MutableStateFlow(false),
        connectedState = MutableStateFlow(false),
        syncProgress = MutableStateFlow(SyncProgress.EMPTY),
        refreshSync = refreshSync,
        ioDispatcher = ioDispatcher,
    )

    /** Counts each store read's dispatch, and can hold them until released. */
    private class CountingDispatcher(private val delegate: CoroutineDispatcher) : CoroutineDispatcher() {
        var count = 0
            private set
        private var holding = false
        private val held = mutableListOf<Pair<CoroutineContext, Runnable>>()

        fun reset() {
            count = 0
        }

        fun hold() {
            holding = true
        }

        fun releaseHeld() {
            holding = false
            val pending = held.toList()
            held.clear()
            pending.forEach { (context, block) -> delegate.dispatch(context, block) }
        }

        override fun dispatch(context: CoroutineContext, block: Runnable) {
            count += 1
            if (holding) held += context to block else delegate.dispatch(context, block)
        }
    }

    private fun listJson(id: String): String = Json.encodeToString(
        DiveListView(
            dives = listOf(listItem(id)),
            total = 1,
            counts = DiveListViewCounts(
                noRecording = 0,
                noSite = 0,
                withFindings = 0,
                notAtProvider = 0,
                changedSinceSent = 0,
            ),
            totals = DiveListViewTotals(dives = 1, durationSeconds = 3_000.0, deepestM = 20.0, lastDiveAt = null),
            months = emptyList(),
        ),
    )

    private fun listItem(id: String) = DiveListViewDivesItem(
        id = id,
        diverId = "diver-1",
        number = null,
        startsAt = "2026-11-01T10:00:00.000Z",
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
        durationSeconds = 3_000.0,
        maxDepthM = 20.0,
        avgDepthM = null,
        site = null,
        findings = 0,
        recordings = 0,
        fromProvider = null,
        gases = emptyList(),
        surfaceIntervalSeconds = null,
        participants = emptyList(),
        waiting = emptyList(),
        profile = null,
    )

    private fun api(): ApiClient {
        val headers = headersOf(HttpHeaders.ContentType, "application/json")
        val engine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/api/providers", "/api/connections" -> respond("[]", HttpStatusCode.OK, headers)
                else -> respond("{}", HttpStatusCode.OK, headers)
            }
        }
        return ApiClient(engine, "test", { null }, {}, Json { ignoreUnknownKeys = true }).also {
            it.baseUrl = "http://test"
        }
    }

    /** A Provider that caches the directory, so later reads do not make an API call the test would have to await. */
    private fun providerApi(): ApiClient {
        val headers = headersOf(HttpHeaders.ContentType, "application/json")
        val provider = """
            {"id":"provider-1","name":"Provider",
             "signIn":{"kind":"password","login":null,"canKeepPassword":false,"createUrl":null,"expires":false},
             "data":{"dives":null,"diveSites":null,"buddies":null,"certifications":null},
             "notices":[],
             "limits":{"pauseMs":0,"run":null,"fetchEveryMinutes":null}}
        """.trimIndent()
        val engine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/api/providers" -> respond("[$provider]", HttpStatusCode.OK, headers)
                "/api/connections" -> respond("[]", HttpStatusCode.OK, headers)
                else -> respond("{}", HttpStatusCode.OK, headers)
            }
        }
        return ApiClient(engine, "test", { null }, {}, Json { ignoreUnknownKeys = true }).also {
            it.baseUrl = "http://test"
        }
    }

    private fun failingApi(onCall: () -> Unit = {}): ApiClient {
        val headers = headersOf(HttpHeaders.ContentType, "application/json")
        val engine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/api/providers", "/api/connections" -> respond("[]", HttpStatusCode.OK, headers)
                else -> {
                    onCall()
                    respond(
                        "{\"code\":\"server_error\"}",
                        HttpStatusCode.BadRequest,
                        headers,
                    )
                }
            }
        }
        return ApiClient(engine, "test", { null }, {}, Json { ignoreUnknownKeys = true }).also {
            it.baseUrl = "http://test"
        }
    }

    /** The API runs off the test dispatcher; poll real time while pumping the test scheduler. */
    private fun TestScope.awaitState(predicate: () -> Boolean) {
        repeat(200) {
            advanceUntilIdle()
            if (predicate()) return
            Thread.sleep(10)
        }
        advanceUntilIdle()
        assertTrue(predicate())
    }

    private fun dive(id: String, startsAt: String) = Dive(
        id = id,
        diverId = "diver-1",
        version = 1,
        startsAt = startsAt,
        durationSeconds = 3_000,
        maxDepthM = 20.0,
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
    )
}
