package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemDevice
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewRecordingsItemSummarySurfaceBreaksItem
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import dev.divehub.shared.ApiClient
import dev.divehub.shared.logbook.LogbookFilter
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

private fun recording(
    id: String = "r1",
    primary: Boolean = true,
    partOf: String? = null,
    startsAt: String = "2026-04-02T09:00:00Z",
    waterType: String? = null,
    breaks: List<DiveViewRecordingsItemSummarySurfaceBreaksItem>? = null,
) = DiveViewRecordingsItem(
    id = id,
    isPrimary = primary,
    device = DiveViewRecordingsItemDevice(manufacturer = "garmin", product = "Descent Mk3", serialNumber = "777"),
    startsAt = startsAt,
    durationSeconds = 3000.0,
    maxDepthM = 24.3,
    parser = "fit",
    partOf = partOf,
    summary = DiveViewRecordingsItemSummary(waterType = waterType, surfaceBreaks = breaks),
    channels = listOf("depth", "temperature"),
)

private fun dive(
    overrides: List<String> = emptyList(),
    source: String = "device",
    offset: Int? = 7200,
    recordings: List<DiveViewRecordingsItem> = listOf(recording()),
    fromRecording: DiveViewFromRecording? = DiveViewFromRecording(
        startsAt = DiveViewFromRecordingStartsAt("2026-04-02T09:00:00Z", 7200),
        durationSeconds = 3000.0,
        maxDepthM = 20.0,
        avgDepthM = 12.0,
        waterTemperatureC = 18.0,
    ),
) = DiveView(
    id = "d1",
    diverId = "diver",
    version = 3,
    values = DiveViewValues(
        number = 42,
        startsAt = DiveViewValuesStartsAt("2026-04-02T09:00:00Z", offset),
        durationSeconds = 3000.0,
        maxDepthM = 24.3,
        avgDepthM = 12.0,
        waterTemperatureC = 18.0,
    ),
    overrides = overrides,
    utcOffsetSource = source,
    fromProvider = null,
    fromRecording = fromRecording,
    notes = null,
    site = null,
    waterType = null,
    waterMismatch = null,
    position = null,
    recordings = recordings,
    cylinders = emptyList(),
    lead = emptyList(),
    leadTotalKg = null,
    weightingFeedback = null,
    suit = null,
    sac = null,
    sacMissing = null,
    participants = emptyList(),
    verificationCodes = emptyList(),
)

private fun series(channel: String, offsets: List<Int>, values: List<Double>) =
    RecordingSamplesViewSeriesItem(channel = channel, offsetsMs = offsets, values = values)

class DiveTimeTest {
    @Test
    fun `the time zone note names where an offset came from and stays away for the device`() {
        assertNull(timeZoneNote("device", startsAtOverridden = false))
        assertEquals(TimeZoneOrigin.POSITION, timeZoneNote("position", startsAtOverridden = false))
        assertEquals(TimeZoneOrigin.NEARBY, timeZoneNote("nearby", startsAtOverridden = false))
        assertEquals(TimeZoneOrigin.UNKNOWN, timeZoneNote("unknown", startsAtOverridden = false))
        assertNull(timeZoneNote("a_new_source", startsAtOverridden = false))
    }

    @Test
    fun `a start set by hand silences the note`() {
        assertNull(timeZoneNote("position", startsAtOverridden = true))
        assertNull(timeZoneNote("unknown", startsAtOverridden = true))
    }

    @Test
    fun `the start is the local time at the dive`() {
        assertEquals(LocalDateTime(2026, 4, 2, 11, 0), diveLocalTime("2026-04-02T09:00:00Z", 7200))
        assertEquals(LocalDateTime(2026, 4, 2, 6, 30), diveLocalTime("2026-04-02T09:00:00Z", -9000))
    }

    @Test
    fun `a time logged without a zone is shown as logged, in UTC`() {
        assertEquals(LocalDateTime(2026, 4, 2, 9, 0), diveLocalTime("2026-04-02T09:00:00Z", null))
        assertNull(diveLocalTime("not a time", 7200))
    }

    @Test
    fun `the offset is said in hours and quarter hours`() {
        assertEquals("UTC+2", utcOffsetText(7200))
        assertEquals("UTC-3:30", utcOffsetText(-12600))
        assertEquals("UTC+0", utcOffsetText(0))
        assertNull(utcOffsetText(null))
    }
}

class DiveValuesTest {
    @Test
    fun `an overridden value is edited and carries what the recording says`() {
        val marked = dive(overrides = listOf("maxDepthM")).markOf(DiveValue.MAX_DEPTH_M)
        assertTrue(marked.edited)
        assertEquals(RecordedValue.Number(20.0), marked.recorded)
    }

    @Test
    fun `a value not set by hand is not marked, the recording's value still being known`() {
        val marked = dive().markOf(DiveValue.DURATION_SECONDS)
        assertFalse(marked.edited)
        assertEquals(RecordedValue.Number(3000.0), marked.recorded)
        assertEquals(
            RecordedValue.Time("2026-04-02T09:00:00Z", 7200),
            dive().markOf(DiveValue.STARTS_AT).recorded,
        )
    }

    @Test
    fun `the dive number is never a field a User can override`() {
        assertFalse(DiveValue.entries.any { it.wire == "number" })
        assertFalse(dive(overrides = listOf("number")).markOf(DiveValue.MAX_DEPTH_M).edited)
    }

    @Test
    fun `a start set by hand is the override that silences the time zone note`() {
        assertTrue(dive(overrides = listOf("startsAt")).startSetByHand)
        assertFalse(dive().startSetByHand)
    }

    @Test
    fun `a device is named by its maker and serial number`() {
        assertEquals("Garmin Descent Mk3", deviceName("garmin", "Descent Mk3"))
        assertEquals("Garmin", deviceName("garmin", null))
        assertEquals("Deep blue", deviceName("deep_blue", null))
        assertEquals("Shearwater Perdix 2", deviceName("SHEARWATER", "Perdix 2"))
    }

    @Test
    fun `a depth difference is read as shallow or deep, rounded`() {
        assertEquals(DepthReading.Shallow(2), depthReading(-2.4))
        assertEquals(DepthReading.Deep(4), depthReading(3.6))
        assertEquals(DepthReading.Deep(0), depthReading(0.0))
        assertEquals(DepthReading.Off, depthReading(null))
    }
}

class ProfileTextTest {
    private val depth = series("depth", listOf(0, 60_000, 120_000), listOf(0.0, 10.0, 5.0))
    private val temperature = series("temperature", listOf(0, 60_000, 120_000), listOf(19.0, 18.0, 21.0))

    @Test
    fun `the summary names the deepest point and when, the time and the temperature range`() {
        val summary = summarize(depth, temperature)!!
        assertEquals(10.0, summary.maxDepthM)
        assertEquals(60.0, summary.maxDepthAtSeconds)
        assertEquals(120.0, summary.durationSeconds)
        assertEquals(18.0, summary.minTemperatureC)
        assertEquals(21.0, summary.maxTemperatureC)
        assertNull(summarize(series("depth", emptyList(), emptyList())))
    }

    @Test
    fun `the table has one row per whole minute with the nearest sample`() {
        val rows = perMinute(depth, temperature)
        assertEquals(listOf(0, 1, 2), rows.map { it.minute })
        assertEquals(listOf(0.0, 10.0, 5.0), rows.map { it.depthM })
        assertEquals(listOf(19.0, 18.0, 21.0), rows.map { it.temperatureC })
        val nearest = series("depth", listOf(0, 90_000), listOf(0.0, 12.0))
        assertEquals(listOf(0, 1), perMinute(nearest).map { it.minute })
        assertEquals(12.0, perMinute(nearest).last().depthM)
        assertNull(nearest(series("depth", emptyList(), emptyList()), 0))
    }

    @Test
    fun `a surface break is said by its length and summed with the duration`() {
        val joined = recording(
            breaks = listOf(
                DiveViewRecordingsItemSummarySurfaceBreaksItem(30.0, 270.0),
                DiveViewRecordingsItemSummarySurfaceBreaksItem(1200.0, 1320.0),
            ),
        )
        assertEquals(listOf(240.0, 120.0), surfaceBreakSeconds(joined))
        assertEquals(360.0, surfaceBreakTotalSeconds(joined))
        assertEquals(emptyList(), surfaceBreakSeconds(recording()))
    }

    @Test
    fun `surface breaks become bands on the profile's time axis`() {
        val breaks = listOf(
            DiveViewRecordingsItemSummarySurfaceBreaksItem(30.0, 150.0),
            DiveViewRecordingsItemSummarySurfaceBreaksItem(240.0, 360.0),
        )
        assertEquals(
            listOf(SurfaceBand(0.1, 0.5), SurfaceBand(0.8, 1.0)),
            surfaceBands(breaks, 300.0),
        )
        assertEquals(emptyList(), surfaceBands(breaks, 0.0))
        assertEquals(emptyList(), surfaceBands(listOf(DiveViewRecordingsItemSummarySurfaceBreaksItem(5.0, 5.0)), 300.0))
    }

    @Test
    fun `a joined recording's parts are listed in their order and are no computers`() {
        val joined = recording("joined")
        val later = recording("later", primary = false, partOf = "joined", startsAt = "2026-04-02T10:00:00Z")
        val earlier = recording("earlier", primary = false, partOf = "joined", startsAt = "2026-04-02T09:00:00Z")
        val other = recording("other", primary = false)
        val all = listOf(joined, later, earlier, other)
        assertEquals(listOf("earlier", "later"), partsOf(joined, all).map { it.id })
        assertEquals(listOf("joined", "other"), computerRecordings(all).map { it.id })
    }

    @Test
    fun `the page draws the primary recording's profile, else the first computer`() {
        val primary = recording("primary")
        val part = recording("part", primary = false, partOf = "joined")
        val other = recording("other", primary = false)
        assertEquals("primary", profileRecording(listOf(part, other, primary))?.id)
        assertEquals("other", profileRecording(listOf(part, other))?.id)
        assertNull(profileRecording(listOf(part)))
        assertNull(profileRecording(emptyList()))
    }

    @Test
    fun `the series are found by channel and unknown channels are ignored`() {
        val samples = RecordingSamplesView("r1", listOf(depth, temperature, series("heartRate", listOf(0), listOf(90.0))))
        assertEquals(depth, depthSeries(samples))
        assertEquals(temperature, temperatureSeries(samples))
    }

    @Test
    fun `the record's water setting is separate from the dive's water`() {
        val dive = dive(recordings = listOf(recording(waterType = "salt")))
        assertEquals("salt", dive.recordings.single().summary.waterType)
        assertNull(dive.waterType)
    }
}

class DiveRepositoryTest {
    private fun harness(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): DiveRepository {
        val api = ApiClient(
            engine = MockEngine(handler),
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true; explicitNulls = false },
        ).apply { baseUrl = "https://dive.example" }
        return DiveRepository(api)
    }

    private fun MockRequestHandleScope.json(body: String): HttpResponseData =
        respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))

    private val diveJson = """{"id":"d1","diverId":"diver","version":3,
        "values":{"number":42,"startsAt":{"at":"2026-04-02T09:00:00Z","utcOffsetSeconds":7200},
        "durationSeconds":3000,"maxDepthM":24.3,"avgDepthM":12,"waterTemperatureC":18},
        "overrides":[],"utcOffsetSource":"device","recordings":[],"cylinders":[],"lead":[],
        "participants":[],"verificationCodes":[]}"""

    @Test
    fun `the dive, its neighbours and its samples are read from their own paths`() = runTest {
        var divePath: String? = null
        var neighbours: String? = null
        var samples: String? = null
        val repository = harness { request ->
            val path = request.url.encodedPath + request.url.encodedQuery.takeIf { it.isNotEmpty() }?.let { "?$it" }.orEmpty()
            when {
                path.startsWith("/api/dives/d1/neighbours") -> {
                    neighbours = path
                    json("""{"previous":null,"next":null,"position":12,"total":312}""")
                }
                path.startsWith("/api/dives/d1") -> {
                    divePath = path
                    json(diveJson)
                }
                path.startsWith("/api/recordings/r1/samples") -> {
                    samples = path
                    json("""{"recordingId":"r1","series":[{"channel":"depth","offsetsMs":[0,60000],"values":[0,10]}]}""")
                }
                else -> error("unexpected $path")
            }
        }
        assertEquals(42, repository.dive("d1").values.number)
        val page = repository.neighbours("d1", setOf(LogbookFilter.NO_RECORDING))
        assertEquals(12, page.position)
        assertEquals(312, page.total)
        assertEquals(2, repository.samples("r1").series.single().values.size)
        assertEquals("/api/dives/d1", divePath)
        assertEquals("/api/dives/d1/neighbours?only=no-recording", neighbours)
        assertEquals("/api/recordings/r1/samples?channels=depth,temperature", samples)
    }

    @Test
    fun `the neighbours path carries the list's filters and nothing else`() {
        assertEquals("/api/dives/d1/neighbours", neighboursPath("d1", emptySet()))
        assertEquals(
            "/api/dives/d1/neighbours?only=no-recording,with-findings",
            neighboursPath("d1", setOf(LogbookFilter.WITH_FINDINGS, LogbookFilter.NO_RECORDING)),
        )
        assertEquals("/api/recordings/r9/samples?channels=depth,temperature", samplesPath("r9"))
    }
}
