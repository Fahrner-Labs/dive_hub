package dev.divehub.android.ui.settings

import android.content.Intent
import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** *Share local logs* builds one chooser over an NDJSON file any app can read (FAH-318, ADR 0080 D1). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LogShareTest {
    @Test
    fun `the chooser carries the NDJSON export as a readable file`() {
        val context = RuntimeEnvironment.getApplication()
        val ndjson = """{"code":"e1","message":"failure"}""" + "\n"

        val chooser = logShareChooser(context, ndjson)

        assertEquals(Intent.ACTION_CHOOSER, chooser.action)
        val send = chooser.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
        assertNotNull(send)
        assertEquals(Intent.ACTION_SEND, send!!.action)
        assertEquals(LOG_MIME, send.type)
        assertTrue(send.flags and Intent.FLAG_GRANT_READ_URI_PERMISSION != 0)
        val uri = send.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        assertNotNull(uri)
        assertEquals("content", uri!!.scheme)
        val shared = context.contentResolver.openInputStream(uri)!!.bufferedReader().use { it.readText() }
        assertEquals(ndjson, shared)
    }
}
