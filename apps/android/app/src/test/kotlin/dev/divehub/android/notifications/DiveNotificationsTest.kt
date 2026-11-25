package dev.divehub.android.notifications

import android.Manifest
import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import dev.divehub.android.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

/** The channel is created once with its locked importance, and a burst folds into one summary (ADR 0085). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class DiveNotificationsTest {
    private lateinit var context: Context
    private lateinit var notifications: DiveNotifications
    private lateinit var manager: NotificationManager

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        shadowOf(context as Application).grantPermissions(Manifest.permission.POST_NOTIFICATIONS)
        notifications = DiveNotifications(context)
        notifications.createChannels()
        manager = context.getSystemService(NotificationManager::class.java)
    }

    @Test
    fun `the dives channel is created with default importance and localizable text`() {
        val channel = manager.getNotificationChannel("dives")

        assertNotNull(channel)
        assertEquals(NotificationManager.IMPORTANCE_DEFAULT, channel.importance)
        assertEquals(context.getString(R.string.notification_channel_dives_name), channel.name)
        assertEquals(context.getString(R.string.notification_channel_dives_description), channel.description)
    }

    @Test
    fun `one dive posts one notification`() {
        notifications.showDive("dive-1")

        val posted = manager.activeNotifications.single().notification
        assertEquals(context.getString(R.string.notification_new_dive_title), posted.extras.getCharSequence(Notification.EXTRA_TITLE))
        assertEquals(context.getString(R.string.notification_new_dive_body), posted.extras.getCharSequence(Notification.EXTRA_TEXT))
    }

    @Test
    fun `a burst updates the same notification to a summary`() {
        notifications.showDive("dive-1")
        notifications.showDive("dive-2")

        val posted = manager.activeNotifications.single().notification
        assertEquals("2 new dives synced", posted.extras.getCharSequence(Notification.EXTRA_TITLE))
        assertEquals(
            context.getString(R.string.notification_new_dives_body),
            posted.extras.getCharSequence(Notification.EXTRA_TEXT),
        )
    }
}
