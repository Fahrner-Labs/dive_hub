// What the personalized tools read from the synced store (FAH-276; ADR 0031, ADR 0071): the Diver's own Dives with
// their lead, feedback, suit, site water and Cylinders, the latest body weight, the replay's depth samples and NDL
// series, and the summaries for the GF high, CNS and OTU. The same plain data the server's service shells read from its
// database; nothing here calls the API.
package dev.divehub.shared.tools

import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewSuit
import dev.divehub.api.models.DiveViewWeightingFeedback
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.EstimateDive
import dev.divehub.shared.planning.EstimateDiveCylinder
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.LoadingDive
import dev.divehub.shared.planning.N2_REPLAY_WINDOW_HOURS
import dev.divehub.shared.planning.SeriesDive
import dev.divehub.shared.planning.SeriesDiveInput
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.Suit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import dev.divehub.shared.planning.TissuePoint
import dev.divehub.shared.planning.WeightingFeedback
import dev.divehub.shared.planning.WeightingFelt
import dev.divehub.shared.planning.enteredDecoFromNdl
import dev.divehub.shared.planning.seriesDiveOf
import dev.divehub.shared.planning.seriesRulesApply
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlin.time.Instant

private const val HOUR_MS: Long = 3_600_000
private const val RECENT_RECORDINGS = 100

/** The checkpoint entity the profiles phase (FAH-273) writes when it syncs the series. */
private const val PROFILES_ENTITY = "profiles"
// The sync writes the API's JSON with nulls omitted (SyncMapping), so a nullable field it dropped must read as
// null here: without `explicitNulls = false` every suit (undergarment or thickness null) fails to decode and the
// lead estimate loses the Dive's suit entirely.
private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

/** A Dive with the link data the answer names beside each dive the estimate rests on. */
data class LeadDiveSource(
    val id: String,
    val startsAt: String,
    val number: Long?,
    val dive: EstimateDive,
)

/** The latest Recording's CNS, decayed from the end of its Dive (ADR 0071). */
data class CnsSource(val cnsEnd: Double, val endMs: Long)

/** What the loadings need: the replay's dives, the series rules' dives, the GF high and the CNS. */
data class LoadingsSources(
    val replayDives: List<LoadingDive>,
    val seriesDives: List<SeriesDive>,
    val latestDiveId: String?,
    val gfHigh: Double,
    val cns: CnsSource?,
)

/**
 * The synced store's values for the personalized tools. Everything is plain data so the service functions can be
 * exercised without a database; [assemblePersonalizedSources] is the only reader.
 */
data class PersonalizedSources(
    val ownDiverId: String? = null,
    /** Every non-deleted Dive of the Diver, the lead estimate's input. */
    val leadDives: List<LeadDiveSource> = emptyList(),
    /** The Diver's latest body weight in kg; null when none is set. */
    val bodyWeightKg: Double? = null,
    /** Null without an own Diver. */
    val loadings: LoadingsSources? = null,
    val syncedAt: String? = null,
    /** Whether the profiles phase has every listed channel in the store (FAH-273). */
    val profilesComplete: Boolean = true,
    /** No Dive has synced yet: an empty state, not an error. */
    val storeEmpty: Boolean = true,
)

/**
 * Reads the Diver's own values in one transaction, so a concurrent sync cannot mix two snapshots. Every read is the
 * server's: the site's water type (never the computer's), the latest body weight, the dives that ended in the window,
 * the primary Recording's depth samples and NDL series, and the summaries for GF high, CNS and OTU.
 */
fun assemblePersonalizedSources(store: DiveStore, nowMs: Long): PersonalizedSources = store.transaction {
    val divers = store.divers()
    val own = divers.firstOrNull { it.isOwn }
    val dives = store.allDives().filter { it.deletedAt == null }
    val syncedAt = store.syncState().lastFullSyncAt
    val profilesComplete = profilesComplete(store, dives)
    if (own == null) {
        return@transaction PersonalizedSources(
            syncedAt = syncedAt,
            profilesComplete = profilesComplete,
            storeEmpty = dives.isEmpty(),
        )
    }

    val ownDives = dives.filter { it.diverId == own.id }
    val diveIds = ownDives.mapTo(HashSet()) { it.id }
    val sites = store.allDiveSites().associateBy { it.id }
    val recordings = store.allRecordings()
        .filter { it.deletedAt == null && it.diveId in diveIds }
        .sortedByDescending { epochMs(it.startsAt) ?: Long.MIN_VALUE }
    val recordingsByDive = recordings.groupBy { it.diveId }
    val seriesByRecording = store.allSeries().groupBy { it.recordingId }
    val cylindersByDive = store.allCylinders().filter { it.diveId in diveIds }.groupBy { it.diveId }

    fun selectedRecording(diveId: String): Recording? {
        val list = recordingsByDive[diveId].orEmpty()
        return list.firstOrNull { it.isPrimary } ?: list.firstOrNull()
    }

    fun summaryOf(recording: Recording?): DiveViewRecordingsItemSummary? = recording?.let { found ->
        try {
            JSON.decodeFromString(DiveViewRecordingsItemSummary.serializer(), found.summaryJson)
        } catch (_: Exception) {
            null
        }
    }

    val leadDives = ownDives.mapNotNull { dive ->
        val date = epochMs(dive.startsAt)?.let { Instant.fromEpochMilliseconds(it) } ?: return@mapNotNull null
        val siteWater = dive.siteId?.let { sites[it]?.waterType }
        val cylinders = cylindersByDive[dive.id].orEmpty().mapNotNull { cylinder ->
            val volume = cylinder.volumeL ?: return@mapNotNull null
            EstimateDiveCylinder(
                volumeL = volume,
                material = materialOf(cylinder.material),
                endPressureBar = cylinder.endPressureBar,
            )
        }
        LeadDiveSource(
            id = dive.id,
            startsAt = dive.startsAt,
            number = dive.number,
            dive = EstimateDive(
                id = dive.id,
                date = date,
                leadKg = dive.leadTotalKg,
                feedback = feedbackOf(dive.weightingFeedbackJson),
                suit = suitOf(dive.suitJson),
                water = waterOf(siteWater),
                cylinders = cylinders,
            ),
        )
    }

    val bodyWeightKg = store.bodyWeights(own.id).maxByOrNull { it.date }?.kg

    val ended = ownDives.mapNotNull { dive ->
        val start = epochMs(dive.startsAt) ?: return@mapNotNull null
        dive to (start + (dive.durationSeconds ?: 0L) * 1000)
    }.filter { (_, end) -> end <= nowMs }
    val latest = ended.maxByOrNull { (_, end) -> end }?.first
    val window = ended.filter { (_, end) -> nowMs - end <= N2_REPLAY_WINDOW_HOURS * HOUR_MS }
    val replayDives = window
        .sortedBy { (dive, _) -> epochMs(dive.startsAt) ?: Long.MIN_VALUE }
        .mapNotNull { (dive, end) ->
            val recording = selectedRecording(dive.id)
            val summary = summaryOf(recording)
            val start = epochMs(dive.startsAt) ?: return@mapNotNull null
            val depth = seriesByRecording[recording?.id].orEmpty().firstOrNull { it.channel == "depth" }
            LoadingDive(
                startMs = start,
                endMs = end,
                samples = depthPoints(depth),
                gas = gasOf(cylindersByDive[dive.id].orEmpty().firstOrNull { it.position == 0L }),
                otuStart = summary?.otuStart,
                otuEnd = summary?.otuEnd,
            )
        }.toMutableList()
    if (latest != null && window.none { (dive, _) -> dive.id == latest.id }) {
        val start = epochMs(latest.startsAt)
        if (start != null) {
            replayDives.add(
                LoadingDive(startMs = start, endMs = start + (latest.durationSeconds ?: 0L) * 1000, samples = emptyList()),
            )
        }
    }

    val seriesDives = ownDives.mapNotNull { dive ->
        val recording = selectedRecording(dive.id)
        val summary = summaryOf(recording)
        if (!seriesRulesApply(summary?.diveMode)) return@mapNotNull null
        val start = epochMs(dive.startsAt) ?: return@mapNotNull null
        val ndl = seriesByRecording[recording?.id].orEmpty().firstOrNull { it.channel == "ndl" }
        seriesDiveOf(
            SeriesDiveInput(
                id = dive.id,
                startMs = start,
                utcOffsetSeconds = dive.utcOffsetSeconds?.toInt(),
                durationSeconds = (dive.durationSeconds ?: 0L).toDouble(),
                maxDepthM = dive.maxDepthM,
            ),
            enteredDeco = enteredDecoFromNdl(seriesValues(ndl)),
        )
    }

    val gfHigh = recordings.take(RECENT_RECORDINGS).asSequence()
        .mapNotNull { summaryOf(it)?.gfHigh }
        .firstOrNull() ?: DEFAULT_GF_HIGH
    val cnsRecording = recordings.take(RECENT_RECORDINGS).firstOrNull { summaryOf(it)?.cnsEnd != null }
    val cnsSummary = summaryOf(cnsRecording)
    val cnsDive = cnsRecording?.let { found -> ownDives.firstOrNull { it.id == found.diveId } }
    val cnsEnd = cnsSummary?.cnsEnd
    val cns = if (cnsEnd != null && cnsDive != null) {
        val start = epochMs(cnsDive.startsAt)
        if (start != null) CnsSource(cnsEnd, start + (cnsDive.durationSeconds ?: 0L) * 1000) else null
    } else {
        null
    }

    PersonalizedSources(
        ownDiverId = own.id,
        leadDives = leadDives,
        bodyWeightKg = bodyWeightKg,
        loadings = LoadingsSources(
            replayDives = replayDives,
            seriesDives = seriesDives,
            latestDiveId = latest?.id,
            gfHigh = gfHigh,
            cns = cns,
        ),
        syncedAt = syncedAt,
        profilesComplete = profilesComplete,
        storeEmpty = dives.isEmpty(),
    )
}

/**
 * The profiles phase's completion, as FAH-273 records it (a checkpoint per Dive, or one aggregate row); before that
 * phase lands the store's own data decides, so the marker never lies about what is on the device.
 */
private fun profilesComplete(store: DiveStore, dives: List<Dive>): Boolean {
    val checkpoints = store.checkpoints().filter { it.entity == PROFILES_ENTITY }
    if (checkpoints.isNotEmpty()) return checkpoints.all { it.done }
    val diveIds = dives.mapTo(HashSet()) { it.id }
    val recordings = store.allRecordings().filter { it.deletedAt == null && it.diveId in diveIds }
    if (recordings.isEmpty()) return true
    val seriesByRecording = store.allSeries().groupBy { it.recordingId }
    return recordings.all { recording ->
        val channels = channelsOf(recording.channelsJson)
        channels.isEmpty() || channels.all { channel ->
            seriesByRecording[recording.id].orEmpty().any { it.channel == channel }
        }
    }
}

private fun channelsOf(json: String): List<String> = try {
    JSON.decodeFromString(json)
} catch (_: Exception) {
    emptyList()
}

private fun summaryOf(json: String): DiveViewRecordingsItemSummary? = try {
    JSON.decodeFromString(DiveViewRecordingsItemSummary.serializer(), json)
} catch (_: Exception) {
    null
}

private fun summaryOf(recording: Recording?): DiveViewRecordingsItemSummary? =
    recording?.let { summaryOf(it.summaryJson) }

private fun depthPoints(series: RecordingSeries?): List<TissuePoint> {
    if (series == null) return emptyList()
    val offsets: List<Int> = try {
        JSON.decodeFromString(series.offsetsMsJson)
    } catch (_: Exception) {
        return emptyList()
    }
    val values: List<Double> = try {
        JSON.decodeFromString(series.valuesJson)
    } catch (_: Exception) {
        return emptyList()
    }
    return offsets.mapIndexedNotNull { index, ms -> values.getOrNull(index)?.let { TissuePoint(ms / 1000.0, it) } }
}

private fun seriesValues(series: RecordingSeries?): List<Double> {
    if (series == null) return emptyList()
    return try {
        JSON.decodeFromString(series.valuesJson)
    } catch (_: Exception) {
        emptyList()
    }
}

private fun gasOf(cylinder: dev.divehub.shared.store.Cylinder?): GasMix? {
    if (cylinder == null) return null
    val o2 = cylinder.o2 ?: return null
    val he = cylinder.he ?: return null
    return GasMix(o2, he)
}

private fun materialOf(name: String?): CylinderMaterial? = when (name) {
    "aluminium" -> CylinderMaterial.aluminium
    "steel" -> CylinderMaterial.steel
    "carbon" -> CylinderMaterial.carbon
    else -> null
}

private fun waterOf(name: String?): SiteWaterType? = when (name) {
    "fresh" -> SiteWaterType.fresh
    "salt" -> SiteWaterType.salt
    "brackish" -> SiteWaterType.brackish
    else -> null
}

private fun suitOf(json: String?): Suit? {
    if (json == null) return null
    val found = try {
        JSON.decodeFromString(DiveViewSuit.serializer(), json)
    } catch (_: Exception) {
        return null
    }
    val type = when (found.type) {
        "none" -> SuitType.none
        "skin" -> SuitType.skin
        "wetsuit" -> SuitType.wetsuit
        "semidry" -> SuitType.semidry
        "drysuit" -> SuitType.drysuit
        else -> return null
    }
    return Suit(
        type = type,
        thicknessMm = found.thicknessMm,
        hood = hoodOf(found.hood),
        undergarment = when (found.undergarment) {
            "light" -> SuitUndergarment.light
            "medium" -> SuitUndergarment.medium
            "heavy" -> SuitUndergarment.heavy
            else -> null
        },
    )
}

private fun feedbackOf(json: String?): WeightingFeedback? {
    if (json == null) return null
    val found = try {
        JSON.decodeFromString(DiveViewWeightingFeedback.serializer(), json)
    } catch (_: Exception) {
        return null
    }
    val felt = when (found.felt) {
        "right" -> WeightingFelt.right
        "too_heavy" -> WeightingFelt.too_heavy
        "too_light" -> WeightingFelt.too_light
        else -> return null
    }
    return WeightingFeedback(felt, found.amountKg)
}

private fun hoodOf(element: JsonElement?): Boolean? = when (element) {
    null, JsonNull -> null
    is JsonPrimitive -> element.booleanOrNull
    else -> null
}

private fun epochMs(text: String): Long? = try {
    Instant.parse(text).toEpochMilliseconds()
} catch (_: Exception) {
    null
}
