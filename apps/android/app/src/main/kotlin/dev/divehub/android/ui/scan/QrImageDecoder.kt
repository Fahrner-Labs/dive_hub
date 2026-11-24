package dev.divehub.android.ui.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.InputStream

/**
 * Reads a QR code from a picture (T11), the Android counterpart of the web's `qr-reader.ts`. Verification codes are
 * QR; the reader is deliberately not asked for other formats, because a barcode that is not a code would only become
 * a refusal from the server. A picture without a code answers null, not an error.
 */
object QrImageDecoder {
    /** Enough pixels for a code on another phone's screen, not a camera's full 12 MP frame in memory. */
    private const val MAX_SIDE = 1_600

    fun decode(bitmap: Bitmap): String? {
        val scaled = downscale(bitmap)
        val width = scaled.width
        val height = scaled.height
        val pixels = IntArray(width * height)
        scaled.getPixels(pixels, 0, width, 0, 0, width, height)
        val source = RGBLuminanceSource(width, height, pixels)
        val image = BinaryBitmap(HybridBinarizer(source))
        val hints = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE),
            DecodeHintType.TRY_HARDER to true,
        )
        return try {
            MultiFormatReader().apply { setHints(hints) }.decode(image).text
        } catch (_: NotFoundException) {
            null
        }
    }

    fun decode(stream: InputStream): String? = try {
        BitmapFactory.decodeStream(stream)?.let(::decode)
    } finally {
        runCatching { stream.close() }
    }

    private fun downscale(bitmap: Bitmap): Bitmap {
        val longest = maxOf(bitmap.width, bitmap.height)
        if (longest <= MAX_SIDE) return bitmap
        val scale = MAX_SIDE.toFloat() / longest
        return Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * scale).toInt().coerceAtLeast(1),
            (bitmap.height * scale).toInt().coerceAtLeast(1),
            true,
        )
    }
}
