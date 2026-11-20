package dev.divehub.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.divehub.android.ui.DiveHubApp
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.MainViewModel
import dev.divehub.android.ui.dive.DiveDetailViewModel
import dev.divehub.android.ui.logbook.LogbookViewModel
import dev.divehub.android.ui.tools.GasPlanViewModel
import dev.divehub.android.ui.tools.LeadEstimateViewModel
import dev.divehub.android.ui.tools.LoadingsViewModel
import dev.divehub.android.ui.tools.ToolsViewModel

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
        setContent {
            DiveHubTheme {
                val logbook: LogbookViewModel = viewModel(factory = LogbookViewModel.factory(container))
                val dive: DiveDetailViewModel = viewModel(factory = DiveDetailViewModel.factory(container))
                val tools: ToolsViewModel = viewModel(factory = ToolsViewModel.factory(container))
                val gasPlan: GasPlanViewModel = viewModel(factory = GasPlanViewModel.factory(container))
                val lead: LeadEstimateViewModel = viewModel(factory = LeadEstimateViewModel.factory(container))
                val loadings: LoadingsViewModel = viewModel(factory = LoadingsViewModel.factory(container))
                DiveHubApp(viewModel, logbook, dive, tools, gasPlan, lead, loadings)
            }
        }
    }
}
