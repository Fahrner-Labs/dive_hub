package dev.divehub.shared.logbook

import dev.divehub.api.models.DiveProfileView
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive

/** A stretch of fast ascent: [start s, end s, band 1–3] as the API sends it (ADR 0036, ADR 0041). */
data class AscentBand(val fromSeconds: Double, val toSeconds: Double, val band: Int)

/** The part of a Dive's profile the sketch draws. */
data class SketchProfile(
    val depthsM: List<Double>,
    val spanSeconds: Double,
    val ascentBands: List<AscentBand>,
)

data class SketchPoint(val x: Double, val y: Double)

/** A line of the sketch; `band` is null for the dive's outline, 1–3 for a fast ascent (thicker and coloured). */
data class SketchCurve(val band: Int?, val points: List<SketchPoint>)

data class SketchGeometry(val outline: List<SketchPoint>, val bands: List<SketchCurve>)

/** The sketch box (ADR 0041). */
const val SKETCH_WIDTH: Double = 112.0
const val SKETCH_HEIGHT: Double = 40.0

/** The scale never gets shorter than this, so a logbook of shallow dives isn't stretched to look deep. */
const val MIN_SKETCH_SCALE_M: Double = 10.0

/** Room for the stroke at the bottom of the box. */
private const val FLOOR: Double = 2.0

/** The metres the box's height stands for: the logbook's deepest dive, at least 10 m, the same for every row. */
fun sketchScale(deepestM: Double?): Double = maxOf(deepestM ?: 0.0, MIN_SKETCH_SCALE_M)

fun DiveProfileView.toSketchProfile(): SketchProfile = SketchProfile(
    depthsM = depthsM,
    spanSeconds = spanSeconds,
    ascentBands = ascentBands.mapNotNull { it.toAscentBand() },
)

private fun List<JsonElement>.toAscentBand(): AscentBand? {
    if (size < 3) return null
    val from = getOrNull(0)?.jsonPrimitive?.doubleOrNull ?: return null
    val to = getOrNull(1)?.jsonPrimitive?.doubleOrNull ?: return null
    val band = getOrNull(2)?.jsonPrimitive?.doubleOrNull?.toInt() ?: return null
    return AscentBand(from, to, band)
}

/**
 * The outline of the dive and its fast-ascent stretches in a [SKETCH_WIDTH] × [SKETCH_HEIGHT] box. Every row is drawn
 * on the same [scaleM], so a 40 m dive looks deeper than a 12 m one. Null under two depths, as the API sends no sketch.
 */
fun sketchGeometry(profile: SketchProfile, scaleM: Double): SketchGeometry? {
    val depths = profile.depthsM
    val last = depths.lastIndex
    if (last < 1) return null
    val scale = scaleM.takeIf { it > 0.0 } ?: return null
    val step = SKETCH_WIDTH / last
    val x = { index: Int -> index * step }
    val y = { depth: Double -> minOf(SKETCH_HEIGHT - FLOOR, (depth / scale) * (SKETCH_HEIGHT - FLOOR)) }
    val outline = depths.mapIndexed { index, depth -> SketchPoint(x(index), y(depth)) }

    val positionAt = { seconds: Double ->
        val position = if (profile.spanSeconds > 0) {
            (seconds / profile.spanSeconds * last).coerceIn(0.0, last.toDouble())
        } else {
            0.0
        }
        val index = minOf(last - 1, position.toInt().coerceAtLeast(0))
        val depth = depths[index] + (depths[index + 1] - depths[index]) * (position - index)
        SketchPoint(x(index) + step * (position - index), y(depth))
    }
    val bands = profile.ascentBands.mapNotNull { band ->
        val start = positionAt(band.fromSeconds)
        val end = positionAt(band.toSeconds)
        val startPosition = start.x / step
        val endPosition = end.x / step
        val inside = outline.filterIndexed { index, _ -> index > startPosition && index < endPosition }
        SketchCurve(band.band, listOf(start) + inside + end)
    }
    return SketchGeometry(outline, bands)
}
