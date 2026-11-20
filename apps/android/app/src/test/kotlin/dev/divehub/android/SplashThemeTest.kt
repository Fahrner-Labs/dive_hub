package dev.divehub.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The launch window follows the system theme (FAH-315): the day theme is slate with the flag-red Waypoint mark, the
 * night theme is ink with the reversed white mark. The system splash is framework-rendered, so this checks the
 * resources a night-qualified start resolves rather than driving an emulator.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SplashThemeTest {
    private val context = RuntimeEnvironment.getApplication()

    @Test
    fun `the day splash is slate with the red Waypoint mark`() {
        val splash = splashTheme()

        assertEquals(Color.parseColor("#EEF3F6"), splash.background)
        assertEquals("dev.divehub.android:drawable/ic_splash_mark", splash.iconName)
        assertChannelNear(Color.parseColor("#C8102E"), splash.markPixel)
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the night splash is ink with the reversed white Waypoint mark`() {
        val splash = splashTheme()

        assertEquals(Color.parseColor("#0E1A23"), splash.background)
        assertEquals("dev.divehub.android:drawable/ic_splash_mark", splash.iconName)
        val pixel = splash.markPixel
        assertTrue("the night mark must be white, was #${Integer.toHexString(pixel)}", Color.red(pixel) > 240 && Color.green(pixel) > 240 && Color.blue(pixel) > 240)
    }

    private data class SplashTheme(val background: Int, val iconName: String, val markPixel: Int)

    private fun splashTheme(): SplashTheme {
        context.setTheme(R.style.Theme_DiveHub_Splash)
        val attrs = context.theme.obtainStyledAttributes(
            intArrayOf(
                androidx.core.splashscreen.R.attr.windowSplashScreenBackground,
                androidx.core.splashscreen.R.attr.windowSplashScreenAnimatedIcon,
            ),
        )
        try {
            val background = attrs.getColor(0, -1)
            val iconId = attrs.getResourceId(1, 0)
            return SplashTheme(
                background = background,
                iconName = context.resources.getResourceName(iconId),
                markPixel = renderPixel(iconId),
            )
        } finally {
            attrs.recycle()
        }
    }

    private fun renderPixel(iconId: Int): Int {
        val drawable = requireNotNull(context.getDrawable(iconId))
        drawable.setBounds(0, 0, 256, 256)
        val bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
        drawable.draw(Canvas(bitmap))
        return bitmap.getPixel(128, 50)
    }

    private fun assertChannelNear(expected: Int, actual: Int) {
        val tolerance = 8
        assertTrue(
            "expected #${Integer.toHexString(expected)}, was #${Integer.toHexString(actual)}",
            kotlin.math.abs(Color.red(expected) - Color.red(actual)) <= tolerance &&
                kotlin.math.abs(Color.green(expected) - Color.green(actual)) <= tolerance &&
                kotlin.math.abs(Color.blue(expected) - Color.blue(actual)) <= tolerance,
        )
    }
}
