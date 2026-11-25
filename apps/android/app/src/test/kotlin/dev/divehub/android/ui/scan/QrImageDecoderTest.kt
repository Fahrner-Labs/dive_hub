package dev.divehub.android.ui.scan

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * What the camera hands on is the code's text, unchanged: the server owns the format, so the client must not trim
 * or polish the payload on its way into the queue (ADR 0043, ADR 0084).
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36])
class QrImageDecoderTest {
    @Test
    fun `a QR code is read exactly as it was drawn`() {
        val text = "  SSI;buddy;ada  "

        assertEquals(text, QrImageDecoder.decode(qrCode(text)))
    }

    @Test
    fun `a picture with no code answers null rather than an error`() {
        val blank = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.WHITE) }

        assertNull(QrImageDecoder.decode(blank))
    }

    private fun qrCode(text: String): Bitmap {
        val matrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 480, 480)
        val bitmap = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_8888)
        for (y in 0 until matrix.height) {
            for (x in 0 until matrix.width) {
                bitmap.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}
