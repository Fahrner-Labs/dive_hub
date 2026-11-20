package dev.divehub.android.ui.settings

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import dev.divehub.android.R
import java.io.File

/** The MIME type of the buffer's export: one JSON object per line (ADR 0080 D2). */
internal const val LOG_MIME: String = "application/x-ndjson"

/**
 * Write the local buffer's NDJSON export to the app's cache and build the share-sheet chooser (ADR 0080 D1). The
 * file is shared through our [FileProvider] so any app the User picks can read it and save or send it on their terms.
 */
internal fun logShareChooser(context: Context, ndjson: String): Intent {
    val directory = File(context.cacheDir, "diagnostics").apply { mkdirs() }
    val file = File(directory, "divehub-diagnostics.ndjson").apply { writeText(ndjson) }
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.diagnostics", file)
    val send = Intent(Intent.ACTION_SEND).apply {
        type = LOG_MIME
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.settings_debug_share_subject))
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    return Intent.createChooser(send, context.getString(R.string.settings_debug_share_title))
}
