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
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.api.models.DiveProfileView
import dev.divehub.shared.logbook.SKETCH_HEIGHT
import dev.divehub.shared.logbook.SKETCH_WIDTH
import dev.divehub.shared.logbook.SketchCurve
import dev.divehub.shared.logbook.sketchGeometry
import dev.divehub.shared.logbook.toSketchProfile

/** The sketch's fixed marks, not spacing steps: the brief's `Radius.sm` and hairline widths. */
private val SketchCorner = 2.dp
private val SketchHairline = 1.dp
private val SketchOutlineWidth = 1.2.dp
private val SketchDashOn = 5.dp
private val SketchDashOff = 4.dp
private val AscentWidth1 = 2.dp
private val AscentWidth2 = 3.5.dp
private val AscentWidth3 = 5.dp

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
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(SketchCorner.toPx()),
                    style = Stroke(
                        width = SketchHairline.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(SketchDashOn.toPx(), SketchDashOff.toPx())),
                    ),
                )
            }
        } else {
            androidx.compose.foundation.layout.Spacer(box)
        }
        return
    }

    val geometry = sketchGeometry(profile.toSketchProfile(), scaleM) ?: return
    val shallow = DiveHubColors.depthShallow
    val deep = DiveHubColors.depthDeep
    val ascent1 = DiveHubColors.ascent1
    val ascent2 = DiveHubColors.ascent2
    val ascent3 = DiveHubColors.ascent3
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
        drawPath(outline, line, style = Stroke(width = SketchOutlineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
        geometry.bands.forEach { band -> drawBand(band, scaleX, scaleY, ascent1, ascent2, ascent3) }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBand(
    band: SketchCurve,
    scaleX: Float,
    scaleY: Float,
    ascent1: Color,
    ascent2: Color,
    ascent3: Color,
) {
    if (band.points.size < 2) return
    val width = when (band.band) {
        3 -> AscentWidth3
        2 -> AscentWidth2
        else -> AscentWidth1
    }
    val colour = when (band.band) {
        3 -> ascent3
        2 -> ascent2
        else -> ascent1
    }
    val path = Path().apply {
        moveTo(band.points.first().x.toFloat() * scaleX, band.points.first().y.toFloat() * scaleY)
        band.points.drop(1).forEach { lineTo(it.x.toFloat() * scaleX, it.y.toFloat() * scaleY) }
    }
    drawPath(path, colour, style = Stroke(width = width.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
}
