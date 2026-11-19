package dev.divehub.android.ui

import java.util.Locale
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/** A sync timestamp reads on the Diver's own clock (FAH-296); an instant that does not parse stays as it came. */
class SyncTimeFormatTest {
    private var previousZone: TimeZone = TimeZone.getDefault()

    @Before
    fun pinZone() {
        previousZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"))
    }

    @After
    fun restoreZone() {
        TimeZone.setDefault(previousZone)
    }

    @Test
    fun `a sync instant renders in the device zone`() {
        assertEquals(
            "18 Nov 2026, 09:00",
            formatSyncTime("2026-11-18T08:00:00Z", Locale.UK, "d MMM yyyy, HH:mm"),
        )
    }

    @Test
    fun `an instant that does not parse stays as it came`() {
        assertEquals("not a time", formatSyncTime("not a time", Locale.UK, "d MMM yyyy, HH:mm"))
    }
}
