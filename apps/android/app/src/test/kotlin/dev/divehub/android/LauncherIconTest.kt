package dev.divehub.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The launcher icon is the diver-down flag from the brand palette in `docs/spec/design-system.md`: a white diagonal
 * on the dive-flag red. Robolectric renders the adaptive icon's layers into a picture, so a changed drawing or a
 * manifest that stops naming the icon shows up here.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w108dp-h108dp-xxhdpi")
class LauncherIconTest {
    private val context = RuntimeEnvironment.getApplication()

    @Test
    fun `the manifest names the adaptive icon and the round icon exists`() {
        val info = context.packageManager.getApplicationInfo(context.packageName, 0)
        assertEquals("dev.divehub.android:mipmap/ic_launcher", context.resources.getResourceName(info.icon))
        requireNotNull(context.getDrawable(R.mipmap.ic_launcher_round))
    }

    @Test
    fun `the launcher icon is the diver-down flag`() {
        val icon = requireNotNull(context.getDrawable(R.mipmap.ic_launcher))
        val size = (108 * context.resources.displayMetrics.density).toInt()
        val view = ImageView(context).apply {
            setImageDrawable(icon)
            setBackgroundColor(Color.TRANSPARENT)
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
        )
        view.layout(0, 0, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        bitmap.captureRoboImage("src/test/screenshots/launcher_icon.png")
    }
}
