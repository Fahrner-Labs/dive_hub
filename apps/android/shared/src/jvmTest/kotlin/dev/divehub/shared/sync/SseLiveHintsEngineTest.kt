package dev.divehub.shared.sync

import dev.divehub.shared.ApiClient
import io.ktor.client.engine.okhttp.OkHttp
import java.io.OutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json

/**
 * The real-engine half of [LiveHintsTest], whose mock answers the whole body at once. It feeds a chunked response one
 * frame at a time from a real socket, because the regression only shows on the real engine path: a body-saving client
 * has nothing to hand back until the response ends.
 */
class SseLiveHintsEngineTest {
    @Test
    fun `the real engine surfaces a trickled first line while the stream is still open`() = runBlocking {
        val release = CountDownLatch(1)
        val server = TrickleServer { out ->
            out.write(HEADERS)
            out.write(chunk(":ok\n\n"))
            out.flush()
            // Hold the body open: a buffering client has nothing to hand back until the response ends.
            release.await(10, TimeUnit.SECONDS)
            out.write(chunk("event: hint\ndata: {\"v\":1,\"kind\":\"dive\",\"id\":\"dive-1\"}\n\n"))
            out.flush()
            out.write("0\r\n\r\n".toByteArray())
            out.flush()
        }
        val engine = OkHttp.create()
        try {
            val api = ApiClient(
                engine = engine,
                versionName = "0.1.0-dev",
                token = { "token" },
                onUnauthorized = {},
                json = Json { ignoreUnknownKeys = true; explicitNulls = false },
            ).apply { baseUrl = "http://127.0.0.1:${server.port}" }

            val events = Channel<LiveEvent>(Channel.UNLIMITED)
            val stream = launch { SseLiveHints(api).events().collect { events.send(it) } }

            assertIs<LiveEvent.Connected>(withTimeout(5_000) { events.receive() })
            release.countDown()
            assertEquals(LiveEvent.Hint(LiveHint("dive", "dive-1")), withTimeout(5_000) { events.receive() })
            stream.cancelAndJoin()
        } finally {
            release.countDown()
            server.close()
            engine.close()
        }
    }

    private fun chunk(payload: String): ByteArray {
        val bytes = payload.toByteArray(Charsets.UTF_8)
        return "${bytes.size.toString(16)}\r\n".toByteArray(Charsets.UTF_8) + bytes + "\r\n".toByteArray(Charsets.UTF_8)
    }

    /** One request, one chunked `text/event-stream` response; the body is written by [serve] to a real socket. */
    private class TrickleServer(private val serve: (OutputStream) -> Unit) : AutoCloseable {
        private val socket = ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))
        val port: Int = socket.localPort
        private val serving = thread(isDaemon = true, name = "sse-trickle-server") {
            runCatching {
                socket.accept().use { client ->
                    val reader = client.getInputStream().bufferedReader()
                    while (true) {
                        val line = reader.readLine() ?: break
                        if (line.isEmpty()) break
                    }
                    serve(client.getOutputStream())
                    client.getOutputStream().flush()
                }
            }
        }

        override fun close() {
            socket.close()
            serving.join(2_000)
        }
    }

    private companion object {
        val HEADERS = "HTTP/1.1 200 OK\r\nContent-Type: text/event-stream\r\nTransfer-Encoding: chunked\r\n\r\n"
            .toByteArray(Charsets.UTF_8)
    }
}
