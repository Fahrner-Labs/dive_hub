package dev.divehub.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.divehub.android.ui.DiveHubApp
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiveHubTheme {
                val container = (application as DiveHubApplication).container
                val viewModel: MainViewModel = viewModel(factory = MainViewModel.factory(container))
                DiveHubApp(viewModel)
            }
        }
    }
}
