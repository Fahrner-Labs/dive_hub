package dev.divehub.android.ui.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticLevel
import dev.divehub.shared.settings.SettingsRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.write.WriteQueueSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The Debug subscreen (FAH-343) reads the diagnostics capability and buffer on each entry (FAH-318), not only the
 * first composition, so a buffer filled while the User was elsewhere still offers *Report a problem*.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SettingsRouteTest {
    @get:Rule
    val compose = createComposeRule()

    private lateinit var driver: JdbcSqliteDriver

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        Class.forName("org.sqlite.JDBC")
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    }

    @After
    fun tearDown() {
        driver.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `entering the debug route re-reads diagnostics after a tab switch`() {
        val diagnostics = FakeDiagnostics(entries = mutableListOf(entry("e0")))
        val viewModel = SettingsViewModel(
            settings = SettingsRepository(FakeInstances(), DiveStore.create(driver)),
            revision = MutableStateFlow(0L),
            connectedState = MutableStateFlow(true),
            offlineState = MutableStateFlow(false),
            refreshSync = {},
            diagnostics = diagnostics,
            queueRevision = MutableStateFlow(0L),
            readSnapshot = { WriteQueueSnapshot() },
            io = Dispatchers.Unconfined,
        )
        var entered by mutableStateOf(true)

        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                if (entered) {
                    SettingsDebugRoute(viewModel = viewModel, onBack = {}, onOpenQueue = {})
                } else {
                    Text("elsewhere")
                }
            }
        }
        compose.waitForIdle()
        assertEquals(1, diagnostics.statusReads)

        entered = false
        compose.waitForIdle()
        entered = true
        compose.waitForIdle()

        assertEquals(2, diagnostics.statusReads)
    }

    private fun entry(code: String) = DiagnosticEntry(
        occurredAt = "2026-11-20T12:00:00Z",
        level = DiagnosticLevel.ERROR,
        code = code,
        message = "message $code",
    )
}
