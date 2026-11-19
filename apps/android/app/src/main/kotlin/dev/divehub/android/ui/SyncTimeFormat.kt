package dev.divehub.android.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * A sync timestamp ("18 Nov 2026, 09:45") in the device's time zone, so the Diver reads it against their own clock
 * (FAH-296). The same moment may read differently on two phones; the pattern and locale still come from the caller.
 * An instant that does not parse stays as it came.
 */
internal fun formatSyncTime(iso: String, locale: Locale, pattern: String): String = try {
    Instant.parse(iso).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern, locale))
} catch (_: Exception) {
    iso
}
