package dev.divehub.android.ui.dive

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
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
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

    private fun viewModel(store: StoreLogbook, revision: MutableStateFlow<Long>) = DiveDetailViewModel(
        store = store,
        api = DiveRepository(api()),
        revision = revision,
        offlineState = MutableStateFlow(false),
        connectedState = MutableStateFlow(false),
        refreshDive = {},
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

    private fun dive(notes: String) = Dive(
        id = "dive-1",
        diverId = "diver-1",
        version = 1,
        startsAt = "2026-11-01T10:00:00.000Z",
        durationSeconds = 3_000,
        maxDepthM = 20.0,
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
        notes = notes,
    )
}
