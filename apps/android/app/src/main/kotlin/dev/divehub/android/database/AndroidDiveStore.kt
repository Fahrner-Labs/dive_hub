package dev.divehub.android.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.db.DiveHubDatabase

/** The app's one database file: the offline read store, opened with the Android driver (ADR 0075). */
class AndroidDiveStore(context: Context) {
    private val driver = AndroidSqliteDriver(
        schema = DiveHubDatabase.Schema,
        context = context.applicationContext,
        name = DATABASE_NAME,
    )

    val store: DiveStore = DiveStore.open(driver)

    companion object {
        const val DATABASE_NAME = "divehub.db"
    }
}
