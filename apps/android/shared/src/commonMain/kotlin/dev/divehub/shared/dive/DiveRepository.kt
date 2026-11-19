package dev.divehub.shared.dive

import dev.divehub.api.models.NeighboursView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.onlyParam
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.providerDirectory
import io.ktor.http.isSuccess

/**
 * The reads one Dive still needs from the API (client contract, Dives): the Dives on either side in the list it was
 * opened from (ADR 0042), and the Providers its values may name. The Dive itself and its profile series come from the
 * store ([dev.divehub.shared.logbook.StoreLogbook]).
 */
class DiveRepository(private val api: ApiClient) {
    /** The list's settings are the filters it was opened with; the page itself is not one of them (ADR 0042). */
    suspend fun neighbours(id: String, filters: Set<LogbookFilter>): NeighboursView {
        val response = api.get(neighboursPath(id, filters))
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
    onlyParam(filters)?.let { append("?only=").append(it) }
}
