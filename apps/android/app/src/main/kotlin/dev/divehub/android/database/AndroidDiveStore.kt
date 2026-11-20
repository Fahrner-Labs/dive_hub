package dev.divehub.android.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.divehub.shared.diagnostics.DiagnosticsBuffer
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.db.DiveHubDatabase

/** The app's one database file: the offline read store and the diagnostics ring buffer (ADR 0075, ADR 0080). */
class AndroidDiveStore(context: Context) {
    private val driver = AndroidSqliteDriver(
        schema = DiveHubDatabase.Schema,
        context = context.applicationContext,
        name = DATABASE_NAME,
    )

    private val database = DiveHubDatabase(driver)

    val store: DiveStore = DiveStore.open(driver)

    val diagnosticsBuffer: DiagnosticsBuffer = DiagnosticsBuffer(database)

    companion object {
        const val DATABASE_NAME = "divehub.db"
    }
}
