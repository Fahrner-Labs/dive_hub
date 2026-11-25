package dev.divehub.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.divehub.android.ui.DiveHubApp
import dev.divehub.android.notifications.EXTRA_PUSH_DIVE_ID
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.MainViewModel
import dev.divehub.android.ui.dive.DiveDetailViewModel
import dev.divehub.android.ui.logbook.LogbookViewModel
import dev.divehub.android.ui.scan.ScanViewModel
import dev.divehub.android.ui.tools.GasPlanViewModel
import dev.divehub.android.ui.tools.LeadEstimateViewModel
import dev.divehub.android.ui.tools.LoadingsViewModel
import dev.divehub.android.ui.tools.ToolsViewModel
import dev.divehub.android.ui.writes.WritesViewModel

class MainActivity : ComponentActivity() {
    private val container: AppContainer
        get() = (application as DiveHubApplication).container

    // The splash's keep condition reads the restore state outside Compose, so the Activity owns this one ViewModel.
    private val viewModel: MainViewModel by viewModels { MainViewModel.factory(container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // Hold the system splash until session restore (GET /api/me) resolves; a warm start is already released.
        splashScreen.setKeepOnScreenCondition { viewModel.state.value.loading }
        enableEdgeToEdge()
        // A cold start from a notification carries the Dive; a burst after the app comes up starts fresh.
        if (savedInstanceState == null) handlePushIntent(intent)
        container.notifications.clearBurst()
        setContent {
            DiveHubTheme {
                val logbook: LogbookViewModel = viewModel(factory = LogbookViewModel.factory(container))
                val dive: DiveDetailViewModel = viewModel(factory = DiveDetailViewModel.factory(container))
                val tools: ToolsViewModel = viewModel(factory = ToolsViewModel.factory(container))
                val gasPlan: GasPlanViewModel = viewModel(factory = GasPlanViewModel.factory(container))
                val lead: LeadEstimateViewModel = viewModel(factory = LeadEstimateViewModel.factory(container))
                val loadings: LoadingsViewModel = viewModel(factory = LoadingsViewModel.factory(container))
                val writes: WritesViewModel = viewModel(factory = WritesViewModel.factory(container))
                val scan: ScanViewModel = viewModel(factory = ScanViewModel.factory(container))
                DiveHubApp(container, viewModel, logbook, dive, tools, gasPlan, lead, loadings, writes, scan)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handlePushIntent(intent)
    }

    private fun handlePushIntent(intent: Intent?) {
        intent?.getStringExtra(EXTRA_PUSH_DIVE_ID)?.let(container::openDiveFromNotification)
    }
}
