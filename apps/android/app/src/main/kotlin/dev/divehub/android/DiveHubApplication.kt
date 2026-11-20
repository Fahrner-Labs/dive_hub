package dev.divehub.android

import android.app.Application
import androidx.work.Configuration
import dev.divehub.shared.diagnostics.DiagnosticLevel

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
        installCrashHandler()
    }

    /**
     * Record an uncaught exception in the local buffer best-effort, then hand it to the previous handler so the app
     * still crashes normally (ADR 0080 D1). No third-party crash SDK.
     */
    internal fun installCrashHandler() {
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            runCatching {
                container.diagnostics.capture(
                    level = DiagnosticLevel.ERROR,
                    code = UNCAUGHT_EXCEPTION,
                    message = throwable.message ?: throwable.javaClass.simpleName,
                    stack = throwable.stackTraceToString(),
                    context = mapOf("thread" to thread.name),
                )
            }
            if (previous != null) {
                previous.uncaughtException(thread, throwable)
            } else {
                throw throwable
            }
        }
    }

    private companion object {
        const val UNCAUGHT_EXCEPTION = "uncaught_exception"
    }
}
