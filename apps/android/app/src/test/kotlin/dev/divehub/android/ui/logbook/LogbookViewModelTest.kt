package dev.divehub.android.ui.logbook

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

    private fun viewModel(
        store: StoreLogbook,
        revision: MutableStateFlow<Long> = MutableStateFlow(0L),
        refreshSync: suspend () -> Unit = {},
    ) = LogbookViewModel(
        store = store,
        api = LogbookRepository(api()),
        revision = revision,
        offlineState = MutableStateFlow(false),
        syncProgress = MutableStateFlow(SyncProgress.EMPTY),
        refreshSync = refreshSync,
        ioDispatcher = dispatcher,
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
