package dev.divehub.shared.sync

import dev.divehub.shared.ApiClient
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.isSuccess
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/** One live hint: only a kind and an id, never data or the audience (ADR 0066). */
data class LiveHint(val kind: String, val id: String)

/** What the live stream tells the client: each (re)connect, then the hints it receives. */
sealed interface LiveEvent {
    /** The stream delivered its first line. Hints are not replayed, so this is the resync point (ADR 0066). */
    data object Connected : LiveEvent

    data class Hint(val hint: LiveHint) : LiveEvent
}

/** The hint source behind the refresh loop; a test fake replaces the HTTP stream. */
fun interface LiveHints {
    fun events(): Flow<LiveEvent>
}

/**
 * `GET /api/live` with the bearer token (ADR 0074): one `text/event-stream` of hint frames. A body that ends or fails
 * closes the flow, which the refresher treats as a dropped stream and reconnects; polling covers the gap meanwhile.
 * Headers alone are not a connection: a proxy can accept the response and buffer the body forever, so the stream counts
 * as connected only once it has framed its first line.
 */
class SseLiveHints(
    private val api: ApiClient,
    private val json: Json = Json { ignoreUnknownKeys = true; explicitNulls = false },
) : LiveHints {
    override fun events(): Flow<LiveEvent> = flow {
        val response = api.stream("/api/live")
        if (!response.status.isSuccess()) throw api.failure(response)

        val channel = response.bodyAsChannel()
        var event = ""
        var connected = false
        while (true) {
            val line = channel.readUTF8Line() ?: break
            if (!connected) {
                connected = true
                emit(LiveEvent.Connected)
            }
            when {
                line.startsWith("event:") -> event = line.substringAfter(':').trim()
                line.startsWith("data:") -> {
                    if (event == "hint") decode(line.substringAfter(':').trim())?.let { emit(LiveEvent.Hint(it)) }
                    event = ""
                }
                line.isBlank() -> event = ""
            }
        }
    }

    /** A malformed frame or a newer protocol version is dropped; it must not end a stream that is otherwise fine. */
    private fun decode(data: String): LiveHint? = try {
        val frame = json.decodeFromString<HintFrame>(data)
        if (frame.v == 1 && frame.kind.isNotEmpty() && frame.id.isNotEmpty()) LiveHint(frame.kind, frame.id) else null
    } catch (_: Exception) {
        null
    }

    @Serializable
    private data class HintFrame(val v: Int = 1, val kind: String, val id: String)
}
