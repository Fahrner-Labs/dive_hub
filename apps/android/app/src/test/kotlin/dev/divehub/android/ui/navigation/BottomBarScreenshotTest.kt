package dev.divehub.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** The bottom bar's content frames (FAH-317): Logbook, Tools and Settings selected. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class BottomBarScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `logbook selected`() {
        render(AppTab.Logbook)
        capture("bottom_bar_logbook.png")
    }

    @Test
    fun `tools selected`() {
        render(AppTab.Tools)
        capture("bottom_bar_tools.png")
    }

    @Test
    fun `settings selected`() {
        render(AppTab.Settings)
        capture("bottom_bar_settings.png")
    }

    private fun render(tab: AppTab) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                Surface(Modifier.fillMaxWidth().testTag("bar")) {
                    DiveHubBottomBar(selected = tab, onSelect = {})
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onNodeWithTag("bar").captureRoboImage("src/test/screenshots/$name")
    }
}
