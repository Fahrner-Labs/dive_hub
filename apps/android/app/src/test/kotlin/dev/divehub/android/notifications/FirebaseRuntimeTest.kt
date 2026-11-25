package dev.divehub.android.notifications

import android.content.Context
import android.content.pm.PackageInfo
import com.google.firebase.FirebaseApp
import dev.divehub.api.models.ApiPushConfigGet200ResponseAndroid
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

/**
 * The runtime Firebase setup (ADR 0085): the public client config from the instance becomes the default app, no
 * Play services leaves Firebase alone so polling keeps working, and another project replaces the app rather than
 * sitting beside it.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FirebaseRuntimeTest {
    private lateinit var context: Context
    private lateinit var preferences: PushPreferences

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        preferences = PushPreferences(context)
        preferences.clearConfig()
        preferences.clearRegistration()
        FirebaseRuntime(context, preferences).reset()
    }

    @After
    fun tearDown() {
        FirebaseRuntime(context, preferences).reset()
        preferences.clearConfig()
    }

    @Test
    fun `without Play services Firebase stays uninitialized so polling continues`() {
        preferences.saveConfig(INSTANCE, config())
        val runtime = FirebaseRuntime(context, preferences)

        assertFalse(hasPlayServices(context))
        assertNull(runtime.initialize())
        assertTrue(FirebaseApp.getApps(context).isEmpty())
    }

    @Test
    fun `the instance's client config initializes the default Firebase app`() {
        installPlayServices()
        preferences.saveConfig(INSTANCE, config())

        val app = FirebaseRuntime(context, preferences).initialize()

        assertNotNull(app)
        assertEquals(FirebaseApp.DEFAULT_APP_NAME, app!!.name)
        assertEquals("dive-hub-test", app.options.projectId)
        assertEquals("1:2:android:abc", app.options.applicationId)
        assertEquals("public-key", app.options.apiKey)
        assertEquals("42", app.options.gcmSenderId)
    }

    @Test
    fun `another project replaces the default app instead of keeping the old one`() {
        installPlayServices()
        preferences.saveConfig(INSTANCE, config())
        val runtime = FirebaseRuntime(context, preferences)
        assertNotNull(runtime.initialize())

        preferences.saveConfig(INSTANCE, config(projectId = "other-project", applicationId = "1:3:android:def"))
        val rebuilt = runtime.initialize()

        assertNotNull(rebuilt)
        assertEquals("other-project", rebuilt!!.options.projectId)
        assertEquals("1:3:android:def", rebuilt.options.applicationId)
        assertEquals(1, FirebaseApp.getApps(context).size)
    }

    @Test
    fun `reset forgets the runtime app`() {
        installPlayServices()
        preferences.saveConfig(INSTANCE, config())
        val runtime = FirebaseRuntime(context, preferences)
        assertNotNull(runtime.initialize())

        runtime.reset()

        assertTrue(FirebaseApp.getApps(context).isEmpty())
    }

    private fun installPlayServices() {
        shadowOf(context.packageManager).installPackage(PackageInfo().apply {
            packageName = "com.google.android.gms"
        })
    }

    private fun config(
        projectId: String = "dive-hub-test",
        applicationId: String = "1:2:android:abc",
    ) = ApiPushConfigGet200ResponseAndroid(
        projectId = projectId,
        applicationId = applicationId,
        apiKey = "public-key",
        gcmSenderId = "42",
    )

    private companion object {
        const val INSTANCE = "https://dive.example"
    }
}
