package dev.divehub.shared.dive

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/** Where a Dive's UTC offset came from (ADR 0030). A value a newer server adds stays unknown to this client. */
enum class TimeZoneOrigin(val wire: String) {
    DEVICE("device"),
    POSITION("position"),
    NEARBY("nearby"),
    UNKNOWN("unknown"),
    ;

    companion object {
        fun of(source: String): TimeZoneOrigin? = entries.firstOrNull { it.wire == source }
    }
}

/**
 * The time-zone note a dive page says (client contract, Dives): where the offset came from when it wasn't the dive
 * computer, and nothing when it was the device or the User set the start by hand (`startsAt` overridden). An origin
 * this client doesn't know says nothing.
 */
fun timeZoneNote(source: String, startsAtOverridden: Boolean): TimeZoneOrigin? {
    if (startsAtOverridden) return null
    return when (val origin = TimeZoneOrigin.of(source)) {
        null, TimeZoneOrigin.DEVICE -> null
        TimeZoneOrigin.POSITION, TimeZoneOrigin.NEARBY, TimeZoneOrigin.UNKNOWN -> origin
    }
}

/**
 * The wall-clock time at the dive: the start plus its UTC offset. Without an offset the time was logged without a
 * zone and is kept as if UTC (ADR 0030), so it is shown in UTC as it was logged.
 */
fun diveLocalTime(startsAt: String, utcOffsetSeconds: Int?): LocalDateTime? = runCatching {
    val zone = utcOffsetSeconds?.let { TimeZone.of(offsetId(it)) } ?: TimeZone.UTC
    Instant.parse(startsAt).toLocalDateTime(zone)
}.getOrNull()

/** The UTC offset a page puts beside the time, "UTC+2", "UTC-3:30"; null where no offset is shown. */
fun utcOffsetText(utcOffsetSeconds: Int?): String? {
    if (utcOffsetSeconds == null) return null
    val sign = if (utcOffsetSeconds < 0) "-" else "+"
    val totalMinutes = (if (utcOffsetSeconds < 0) -utcOffsetSeconds else utcOffsetSeconds) / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return if (minutes == 0) "UTC$sign$hours" else "UTC$sign$hours:${minutes.toString().padStart(2, '0')}"
}

/** The offset as a zone id kotlinx-datetime reads, "+02:00" or "-02:30". */
private fun offsetId(totalSeconds: Int): String {
    val absolute = if (totalSeconds < 0) -totalSeconds else totalSeconds
    val sign = if (totalSeconds < 0) "-" else "+"
    val hours = (absolute / 3600).toString().padStart(2, '0')
    val minutes = ((absolute % 3600) / 60).toString().padStart(2, '0')
    val seconds = absolute % 60
    return if (seconds == 0) "$sign$hours:$minutes" else "$sign$hours:$minutes:" + seconds.toString().padStart(2, '0')
}
