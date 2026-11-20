package dev.divehub.android.ui.logbook

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.sync.SyncProgress
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/** Process death restores the logbook page: `start` must open the restored page, not the first one (ADR 0079). */
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
            val api = ApiClient(OkHttp.create(), "test", { null }, {}, Json { ignoreUnknownKeys = true })
            val viewModel = LogbookViewModel(
                store = StoreLogbook(store),
                api = LogbookRepository(api),
                revision = MutableStateFlow(0L),
                offlineState = MutableStateFlow(false),
                syncProgress = MutableStateFlow(SyncProgress.EMPTY),
                refreshSync = {},
            )

            viewModel.start("diver-1", page = 3)

            assertEquals(3, viewModel.state.value.page)
            viewModel.forget()
        } finally {
            driver.close()
        }
    }
}
