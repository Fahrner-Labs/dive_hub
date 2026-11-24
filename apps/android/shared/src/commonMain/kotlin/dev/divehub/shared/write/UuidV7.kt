package dev.divehub.shared.write

import kotlin.random.Random
import kotlin.time.Clock

/**
 * A uuidv7 for a client-made create (ADR 0084 D5, T10/T11): the 48-bit unix-millisecond timestamp, the version and
 * variant bits, and random bits for the rest. It is the create's replay idempotency key, not a secret.
 */
object UuidV7 {
    fun generate(
        nowMillis: Long = Clock.System.now().toEpochMilliseconds(),
        random: Random = Random.Default,
    ): String {
        val bytes = ByteArray(16)
        var millis = nowMillis
        for (index in 5 downTo 0) {
            bytes[index] = (millis and 0xff).toByte()
            millis = millis shr 8
        }
        val tail = random.nextBytes(ByteArray(10))
        for (index in 0 until 10) bytes[6 + index] = tail[index]
        bytes[6] = ((bytes[6].toInt() and 0x0f) or 0x70).toByte()
        bytes[8] = ((bytes[8].toInt() and 0x3f) or 0x80).toByte()
        val hex = bytes.joinToString("") { byte -> (byte.toInt() and 0xff).toString(16).padStart(2, '0') }
        return "${hex.substring(0, 8)}-${hex.substring(8, 12)}-${hex.substring(12, 16)}-${hex.substring(16, 20)}-${hex.substring(20)}"
    }
}
