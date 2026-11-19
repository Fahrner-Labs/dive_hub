package dev.divehub.android

import android.app.Application
import androidx.work.Configuration

/**
 * The app's object graph. It also gives WorkManager its configuration, so the periodic sync works both under the
 * normal startup initializer and in the Robolectric tests, which do not run that initializer.
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
