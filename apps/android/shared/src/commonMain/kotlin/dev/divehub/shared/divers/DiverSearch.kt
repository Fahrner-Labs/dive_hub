package dev.divehub.shared.divers

import dev.divehub.api.models.FoundDiver
import dev.divehub.shared.ApiClient
import io.ktor.http.encodeURLParameter
import io.ktor.http.isSuccess

/**
 * The live search over every Diver of the instance by name (ADR 0028). It needs an answer from the server, so the
 * caller offers the cached managed Divers while offline; a result says whether the User keeps its logbook or it is
 * external, which is what the add sheet's labels need.
 */
fun interface DiverSearch {
    suspend fun search(query: String): List<FoundDiver>
}

/** `GET /api/divers/search`, the User's own and managed Divers first. Only a successful answer is a result. */
class ApiDiverSearch(private val api: ApiClient) : DiverSearch {
    override suspend fun search(query: String): List<FoundDiver> {
        val response = api.get("/api/divers/search?q=${query.trim().encodeURLParameter()}&limit=$SEARCH_LIMIT")
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    private companion object {
        /** The web's own cap: enough names to recognise someone, small enough for a phone sheet. */
        const val SEARCH_LIMIT = 20
    }
}
