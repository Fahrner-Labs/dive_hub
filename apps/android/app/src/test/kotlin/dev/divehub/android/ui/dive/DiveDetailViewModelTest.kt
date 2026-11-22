package dev.divehub.android.ui.dive

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
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
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext

/** A sync running while the Dive page is open must update it in place, never put the loading state back (FAH-333). */
@OptIn(ExperimentalCoroutinesApi::class)
class DiveDetailViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `sync writes reread the open dive without blanking the page`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(notes = "First"))
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision)
            val states = mutableListOf<DiveDetailUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { states += it }
            }

            viewModel.open("dive-1")
            advanceUntilIdle()
            assertEquals("First", viewModel.state.value.dive?.notes)
            assertFalse(viewModel.state.value.loading)
            val opened = states.size

            store.upsertDive(dive(notes = "Second"))
            for (value in 1L..5L) {
                revision.value = value
                advanceTimeBy(100)
            }
            advanceUntilIdle()

            assertEquals("Second", viewModel.state.value.dive?.notes)
            val followed = states.drop(opened)
            assertTrue(followed.isNotEmpty())
            assertTrue(followed.none { it.loading })
            assertTrue(followed.none { it.dive == null })
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a neighbour step never blanks the page and reads the adjacent pages ahead`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(notes = "First"))
            store.upsertDive(dive(id = "dive-2", notes = "Second"))
            store.saveSyncState(store.syncState().copy(lastFullSyncAt = "2026-11-01T12:00:00.000Z"))
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision)
            val states = mutableListOf<DiveDetailUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { states += it }
            }

            viewModel.open("dive-1")
            awaitState { viewModel.state.value.previousDive?.dive?.id == "dive-2" }
            assertEquals("First", viewModel.state.value.dive?.notes)
            assertEquals("2026-11-01T12:00:00.000Z", viewModel.state.value.previousDive?.syncedAt)
            val opened = states.size

            viewModel.openNeighbour("dive-2")
            awaitState { viewModel.state.value.nextDive?.dive?.id == "dive-1" }

            assertEquals("dive-2", viewModel.state.value.diveId)
            assertEquals("Second", viewModel.state.value.dive?.notes)
            val followed = states.drop(opened)
            assertTrue(followed.isNotEmpty())
            assertTrue(followed.none { it.loading })
            assertTrue(followed.none { it.dive == null })
            // The new Dive never goes out with the previous list's neighbours: they swap together.
            val swapped = followed.filter { it.dive?.id == "dive-2" }
            assertTrue(swapped.isNotEmpty())
            assertTrue(swapped.all { it.previousDive == null && it.nextDive?.dive?.id == "dive-1" })
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a deleted dive keeps its notice through later sync writes`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(notes = "First"))
            store.saveSyncState(store.syncState().copy(lastFullSyncAt = "2026-11-01T12:00:00.000Z"))
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision)
            val states = mutableListOf<DiveDetailUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { states += it }
            }

            viewModel.open("dive-1")
            advanceUntilIdle()
            assertNotNull(viewModel.state.value.dive)

            store.markDiveDeleted("dive-1", "2026-11-02T10:00:00.000Z")
            revision.value = 1
            advanceTimeBy(300)
            advanceUntilIdle()
            assertEquals("not_found", viewModel.state.value.error)
            assertNull(viewModel.state.value.dive)

            val before = states.size
            revision.value = 2
            advanceTimeBy(300)
            advanceUntilIdle()

            assertEquals("not_found", viewModel.state.value.error)
            assertNull(viewModel.state.value.dive)
            assertTrue(states.none { it.dive == null && it.error == null && !it.loading && !it.offline })
            assertTrue(states.size >= before)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `recordings gone clears the profile state`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(notes = "First"))
            store.upsertRecording(recording())
            store.upsertSeries(series())
            val revision = MutableStateFlow(0L)
            val viewModel = viewModel(StoreLogbook(store), revision)

            viewModel.open("dive-1")
            awaitState { viewModel.state.value.samples != null }

            store.deleteRecording("rec-1")
            revision.value = 1
            advanceTimeBy(300)
            awaitState { viewModel.state.value.samples == null }

            assertFalse(viewModel.state.value.samplesPending)
            assertNull(viewModel.state.value.samplesError)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    @Test
    fun `an older read cannot overwrite a newly opened dive`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(notes = "one"))
            store.upsertDive(dive(id = "dive-2", notes = "two"))
            val gate = GatedDispatcher(dispatcher)
            val viewModel = viewModel(StoreLogbook(store), MutableStateFlow(0L), ioDispatcher = gate)

            gate.hold()
            viewModel.open("dive-1")
            runCurrent()
            gate.disarm()

            viewModel.open("dive-2")
            advanceUntilIdle()
            assertEquals("dive-2", viewModel.state.value.diveId)
            assertEquals("two", viewModel.state.value.dive?.notes)

            gate.release()
            advanceUntilIdle()

            assertEquals("dive-2", viewModel.state.value.diveId)
            assertEquals("two", viewModel.state.value.dive?.notes)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }

    private fun viewModel(
        store: StoreLogbook,
        revision: MutableStateFlow<Long>,
        ioDispatcher: CoroutineDispatcher = dispatcher,
    ) = DiveDetailViewModel(
        store = store,
        api = DiveRepository(api()),
        revision = revision,
        offlineState = MutableStateFlow(false),
        connectedState = MutableStateFlow(false),
        refreshDive = {},
        ioDispatcher = ioDispatcher,
    )

    /** Holds the first store read's dispatch until [release], while later reads run straight through. */
    private class GatedDispatcher(private val delegate: CoroutineDispatcher) : CoroutineDispatcher() {
        private var holding = false
        private val held = mutableListOf<Pair<CoroutineContext, Runnable>>()

        fun hold() {
            holding = true
        }

        fun disarm() {
            holding = false
        }

        fun release() {
            val pending = held.toList()
            held.clear()
            pending.forEach { (context, block) -> delegate.dispatch(context, block) }
        }

        override fun dispatch(context: CoroutineContext, block: Runnable) {
            if (holding) held += context to block else delegate.dispatch(context, block)
        }
    }

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

    private fun dive(id: String = "dive-1", notes: String) = Dive(
        id = id,
        diverId = "diver-1",
        version = 1,
        startsAt = "2026-11-01T10:00:00.000Z",
        durationSeconds = 3_000,
        maxDepthM = 20.0,
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
        notes = notes,
    )

    private fun recording() = Recording(
        id = "rec-1",
        diveId = "dive-1",
        isPrimary = true,
        startsAt = "2026-11-01T10:00:00.000Z",
        durationSeconds = 3_000,
        maxDepthM = 20.0,
        parser = "test",
    )

    private fun series() = RecordingSeries(
        recordingId = "rec-1",
        channel = "depth",
        offsetsMsJson = "[0,1000]",
        valuesJson = "[1.0,2.0]",
        fetchedAt = "2026-11-01T12:00:00.000Z",
    )
}
