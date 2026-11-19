package dev.divehub.android

import android.app.Application
import androidx.work.Configuration

/**
 * The app's object graph. It also gives WorkManager its configuration for on-demand initialization (the manifest
 * removes the library's startup initializer), so the periodic sync works in the app and in the Robolectric tests.
 */
class DiveHubApplication : Application(), Configuration.Provider {
    lateinit var container: AppContainer
        private set

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().build()

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
