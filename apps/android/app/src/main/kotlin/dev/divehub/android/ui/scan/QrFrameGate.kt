package dev.divehub.android.ui.scan

import android.os.SystemClock

/**
 * Decides which camera frames are worth decoding and which code wins. CameraX delivers frames faster than a QR
 * decode needs, so only one frame per interval is read; the first non-blank text finishes the gate, and every later
 * frame is ignored while the camera is released. The format itself stays the server's: this only carries the text.
 */
class QrFrameGate(
    private val minIntervalMs: Long = DEFAULT_MIN_INTERVAL_MS,
    private val clock: () -> Long = SystemClock::elapsedRealtime,
) {
    private var lastAnalyzedAt: Long? = null
    private var finished = false

    /** True when this frame may be decoded: nothing is finished and the last due frame was at least one interval ago. */
    fun due(): Boolean {
        if (finished) return false
        val now = clock()
        val last = lastAnalyzedAt
        if (last != null && now - last < minIntervalMs) return false
        lastAnalyzedAt = now
        return true
    }

    /** The first non-blank text, returned once; a blank read is not a code and leaves the gate open. */
    fun firstText(text: String?): String? {
        if (finished || text.isNullOrBlank()) return null
        finished = true
        return text
    }

    private companion object {
        /** Four decodes a second is plenty for a code held in front of the camera and keeps the preview smooth. */
        const val DEFAULT_MIN_INTERVAL_MS = 250L
    }
}
