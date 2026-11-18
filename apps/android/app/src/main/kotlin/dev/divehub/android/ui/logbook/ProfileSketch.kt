package dev.divehub.android.ui.logbook

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import dev.divehub.api.models.DiveProfileView
import dev.divehub.shared.logbook.SKETCH_HEIGHT
import dev.divehub.shared.logbook.SKETCH_WIDTH
import dev.divehub.shared.logbook.SketchCurve
import dev.divehub.shared.logbook.sketchGeometry
import dev.divehub.shared.logbook.toSketchProfile

/**
 * The shape of a dive in a logbook row (ADR 0041): the water above the profile in the depth gradient, fast ascent over
 * it thicker and coloured. Decoration only, so it is hidden from assistive technology; depth and duration are text
 * beside it. A Dive without a Recording gets an empty dashed box (its row says "No recording").
 */
@Composable
fun ProfileSketch(profile: DiveProfileView?, hasRecording: Boolean, scaleM: Double, modifier: Modifier = Modifier) {
    val box = Modifier.size(SKETCH_WIDTH.dp, SKETCH_HEIGHT.dp)
    if (profile == null) {
        if (!hasRecording) {
            val outline = MaterialTheme.colorScheme.outline
            Canvas(box.then(modifier).clearAndSetSemantics {}) {
                drawRoundRect(
                    color = outline,
                    topLeft = Offset.Zero,
                    size = Size(size.width, size.height),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx()),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 4.dp.toPx())),
                    ),
                )
            }
        } else {
            androidx.compose.foundation.layout.Spacer(box)
        }
        return
    }

    val geometry = sketchGeometry(profile.toSketchProfile(), scaleM) ?: return
    val shallow = MaterialTheme.colorScheme.primaryContainer
    val deep = MaterialTheme.colorScheme.primary
    val line = MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(box.then(modifier).clearAndSetSemantics {}) {
        val scaleX = size.width / SKETCH_WIDTH.toFloat()
        val scaleY = size.height / SKETCH_HEIGHT.toFloat()
        val area = Path().apply {
            moveTo(geometry.outline.first().x.toFloat() * scaleX, geometry.outline.first().y.toFloat() * scaleY)
            geometry.outline.drop(1).forEach { lineTo(it.x.toFloat() * scaleX, it.y.toFloat() * scaleY) }
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        drawPath(area, Brush.verticalGradient(listOf(shallow, deep), endY = size.height))

        val outline = Path().apply {
            moveTo(geometry.outline.first().x.toFloat() * scaleX, geometry.outline.first().y.toFloat() * scaleY)
            geometry.outline.drop(1).forEach { lineTo(it.x.toFloat() * scaleX, it.y.toFloat() * scaleY) }
        }
        drawPath(outline, line, style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
        geometry.bands.forEach { band -> drawBand(band, scaleX, scaleY) }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBand(band: SketchCurve, scaleX: Float, scaleY: Float) {
    if (band.points.size < 2) return
    val width = when (band.band) {
        3 -> 5.dp
        2 -> 3.5.dp
        else -> 2.dp
    }
    val colour = when (band.band) {
        3 -> Color(0xFFC81E1E)
        2 -> Color(0xFFE06A00)
        else -> Color(0xFFE0A100)
    }
    val path = Path().apply {
        moveTo(band.points.first().x.toFloat() * scaleX, band.points.first().y.toFloat() * scaleY)
        band.points.drop(1).forEach { lineTo(it.x.toFloat() * scaleX, it.y.toFloat() * scaleY) }
    }
    drawPath(path, colour, style = Stroke(width = width.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
}
