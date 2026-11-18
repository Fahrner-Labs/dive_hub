package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.providerDirectory
import io.ktor.http.isSuccess

/**
 * The read side of one Dive (client contract, Dives): the Dive, the Dives on either side in the list it was opened
 * from (ADR 0042), and the Primary recording's samples for the depth profile. All query building lives here, so the
 * Compose screen only draws.
 */
class DiveRepository(private val api: ApiClient) {
    suspend fun dive(id: String): DiveView {
        val response = api.get("/api/dives/$id")
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    /** The list's settings are the filters it was opened with; the page itself is not one of them (ADR 0042). */
    suspend fun neighbours(id: String, filters: Set<LogbookFilter>): NeighboursView {
        val response = api.get(neighboursPath(id, filters))
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    suspend fun samples(recordingId: String): RecordingSamplesView {
        val response = api.get(samplesPath(recordingId))
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    suspend fun providers(): ProviderDirectory = providerDirectory(api)
}

/**
 * `GET /api/dives/{id}/neighbours` with the list's own settings. An Android list has only the "Show only" filters
 * so far; the sort stays the list's default (newest first).
 */
fun neighboursPath(id: String, filters: Set<LogbookFilter>): String = buildString {
    append("/api/dives/").append(id).append("/neighbours")
    val only = LogbookFilter.entries.filter { it in filters }
    if (only.isNotEmpty()) append("?only=").append(only.joinToString(",") { it.wire })
}

/** The depth and temperature series the profile draws and its table lists. */
fun samplesPath(recordingId: String): String =
    "/api/recordings/$recordingId/samples?channels=depth,temperature"
