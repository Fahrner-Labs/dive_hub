package dev.divehub.shared.logbook

import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.ConnectionView
import dev.divehub.api.models.ProviderSummary
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/** The Providers the logbook can name: `fromProvider` rows and the connected names in `not-at-provider` (ADR 0040). */
data class ProviderDirectory(
    /** Every Provider of the instance, by id. */
    val names: Map<String, String> = emptyMap(),
    /** The Providers with a Connection, by their own names, in the instance's order. */
    val connected: List<String> = emptyList(),
) {
    fun nameOf(id: String): String = names[id] ?: id

    companion object {
        val EMPTY = ProviderDirectory()
    }
}

/**
 * The read side of the logbook (ADR 0017, ADR 0040): one page at a time, and the Provider names its rows and filters
 * show. All paging and filter maths lives beside it in this package, so the Compose screen only draws.
 */
class LogbookRepository(private val api: ApiClient) {
    suspend fun page(page: Int, filters: Set<LogbookFilter> = emptySet()): DiveListView {
        val response = api.get(logbookPath(page, filters))
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    /**
     * Provider names for the rows and the `not-at-provider` filter. A failure here never fails the logbook: the list
     * still shows, with the Provider's id where its name would be.
     */
    suspend fun providers(): ProviderDirectory = providerDirectory(api)
}

/**
 * The instance's Provider names, for anything that must name one (the logbook's rows and filter, a Dive's
 * `fromProvider`). A failure here never fails the screen: the Provider's id stands where its name would be.
 */
suspend fun providerDirectory(api: ApiClient): ProviderDirectory = try {
    coroutineScope {
        val providers = async { decodeList<ProviderSummary>(api, api.get("/api/providers")) }
        val connections = async { decodeList<ConnectionView>(api, api.get("/api/connections")) }
        val connectedIds = connections.await().map { it.provider }.toSet()
        val all = providers.await()
        ProviderDirectory(
            names = all.associate { it.id to it.name },
            connected = all.filter { it.id in connectedIds }.map { it.name },
        )
    }
} catch (_: DiveHubException) {
    ProviderDirectory.EMPTY
} catch (_: NoAnswerException) {
    ProviderDirectory.EMPTY
}

private suspend inline fun <reified T> decodeList(api: ApiClient, response: HttpResponse): List<T> {
    if (!response.status.isSuccess()) throw api.failure(response)
    return api.decode(response)
}


/**
 * `GET /api/dives` for a 1-based page with the pressed filters. Page size is fixed at 25 (ADR 0040), the list is
 * newest first, and a comma in `only` needs no escaping (the contract's example).
 */
fun logbookPath(page: Int, filters: Set<LogbookFilter>): String {
    val query = buildString {
        append("/api/dives?limit=").append(LOGBOOK_PAGE_SIZE)
        append("&offset=").append(LogbookPaging.offset(page))
        onlyParam(filters)?.let { append("&only=").append(it) }
    }
    return query
}
