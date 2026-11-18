package dev.divehub.shared.logbook

import dev.divehub.api.models.ApiDivesGet200Response
import dev.divehub.api.models.ApiDivesGet200ResponseCounts
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemGasesItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemParticipantsItem
import dev.divehub.api.models.ApiDivesGet200ResponseMonthsItem
import dev.divehub.api.models.ApiDivesGet200ResponseTotals
import dev.divehub.api.models.DiveProfileView
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private fun dive(
    id: String = "d1",
    startsAt: String = "2026-04-02T09:00:00Z",
    offset: Int? = 7200,
    recordings: Int = 1,
    findings: Int = 0,
    fromProvider: String? = null,
    gases: List<ApiDivesGet200ResponseDivesItemGasesItem> = emptyList(),
    participants: List<ApiDivesGet200ResponseDivesItemParticipantsItem> = emptyList(),
    profile: DiveProfileView? = null,
) = ApiDivesGet200ResponseDivesItem(
    id = id,
    diverId = "diver",
    number = 12,
    startsAt = startsAt,
    utcOffsetSeconds = offset,
    utcOffsetSource = "device",
    durationSeconds = 3000.0,
    maxDepthM = 20.0,
    avgDepthM = 12.0,
    site = null,
    findings = findings,
    recordings = recordings,
    fromProvider = fromProvider,
    gases = gases,
    surfaceIntervalSeconds = null,
    participants = participants,
    waiting = emptyList(),
    profile = profile,
)

private fun counts(noRecording: Int = 0, noSite: Int = 0, withFindings: Int = 0, notAtProvider: Int = 0, changed: Int = 0) =
    ApiDivesGet200ResponseCounts(noRecording, noSite, withFindings, notAtProvider, changed)

class LogbookPagingTest {
    @Test
    fun `pages are 25 dives and offsets follow the 1-based page`() {
        assertEquals(0, LogbookPaging.offset(0))
        assertEquals(0, LogbookPaging.offset(1))
        assertEquals(25, LogbookPaging.offset(2))
        assertEquals(3, LogbookPaging.pageCount(51))
        assertEquals(0, LogbookPaging.pageCount(0))
    }

    @Test
    fun `a range names the positions a page shows`() {
        assertEquals(1..25, LogbookPaging.range(1, 312))
        assertEquals(301..312, LogbookPaging.range(13, 312))
        assertNull(LogbookPaging.range(1, 0))
    }

    @Test
    fun `the query sends the page size, the offset and the pressed filters on the wire`() {
        assertEquals("/api/dives?limit=25&offset=0", logbookPath(1, emptySet()))
        assertEquals(
            "/api/dives?limit=25&offset=50&only=no-recording,with-findings",
            logbookPath(3, setOf(LogbookFilter.WITH_FINDINGS, LogbookFilter.NO_RECORDING)),
        )
    }
}

class LogbookFiltersTest {
    @Test
    fun `a filter with no dive is hidden unless it is pressed`() {
        val counts = counts(noRecording = 2, noSite = 0, withFindings = 4)
        assertEquals(
            listOf(LogbookFilter.NO_RECORDING, LogbookFilter.WITH_FINDINGS),
            filterChips(counts, emptySet()).map { it.filter },
        )
        assertEquals(
            listOf(LogbookFilter.NO_RECORDING, LogbookFilter.NO_SITE, LogbookFilter.WITH_FINDINGS),
            filterChips(counts, setOf(LogbookFilter.NO_SITE)).map { it.filter },
        )
    }

    @Test
    fun `a chip keeps its count and says whether it narrows the list`() {
        val chips = filterChips(counts(withFindings = 4), setOf(LogbookFilter.WITH_FINDINGS))
        assertEquals(FilterChip(LogbookFilter.WITH_FINDINGS, 4, true), chips.single())
    }

    @Test
    fun `toggling presses and releases one filter, leaving the others`() {
        val pressed = setOf(LogbookFilter.NO_SITE, LogbookFilter.WITH_FINDINGS)
        assertEquals(setOf(LogbookFilter.WITH_FINDINGS), toggleFilter(pressed, LogbookFilter.NO_SITE))
        assertEquals(pressed + LogbookFilter.NO_RECORDING, toggleFilter(pressed, LogbookFilter.NO_RECORDING))
    }
}

class MonthGroupsTest {
    @Test
    fun `a month is the local month, the start plus the dive's offset`() {
        assertEquals("2026-04", localMonthOf("2026-04-02T09:00:00Z", 7200))
        assertEquals("2026-05", localMonthOf("2026-04-30T23:30:00Z", 3600))
        assertEquals("2026-01", localMonthOf("2026-01-05T09:00:00Z", null))
        assertNull(localMonthOf("not a time", 0))
    }

    @Test
    fun `consecutive dives group under their month with the API's figures`() {
        val figures = listOf(ApiDivesGet200ResponseMonthsItem("2026-04", 9, 27000.0))
        val groups = monthGroups(
            listOf(dive("a"), dive("b", startsAt = "2026-04-01T09:00:00Z"), dive("c", startsAt = "2026-03-30T09:00:00Z")),
            figures,
        )
        assertEquals(listOf("2026-04", "2026-03"), groups.map { it.month })
        assertEquals(listOf(2, 1), groups.map { it.dives.size })
        assertEquals(figures.single(), groups.first().figures)
        assertNull(groups.last().figures)
    }

    @Test
    fun `without months the page is one group without a heading`() {
        val groups = monthGroups(listOf(dive("a"), dive("b")), emptyList())
        assertEquals(listOf<LogbookMonth>(LogbookMonth(null, null, listOf(dive("a"), dive("b")))), groups)
    }
}

class SketchTest {
    private fun profile(depths: List<Double>, bands: List<List<kotlinx.serialization.json.JsonElement>> = emptyList()) =
        DiveProfileView(depthsM = depths, spanSeconds = 1800.0, ascentBands = bands).toSketchProfile()

    @Test
    fun `the scale is the deepest dive of the logbook and never less than 10 m`() {
        assertEquals(40.0, sketchScale(40.0))
        assertEquals(10.0, sketchScale(4.0))
        assertEquals(10.0, sketchScale(null))
    }

    @Test
    fun `all rows share one depth scale, so a deeper dive draws deeper`() {
        val shallow = sketchGeometry(profile(listOf(0.0, 12.0, 5.0)), sketchScale(40.0))!!
        val deep = sketchGeometry(profile(listOf(0.0, 30.0, 10.0)), sketchScale(40.0))!!
        val shallowBottom = shallow.outline.maxOf { it.y }
        val deepBottom = deep.outline.maxOf { it.y }
        assertTrue(deepBottom > shallowBottom)
        assertEquals(SKETCH_WIDTH, deep.outline.last().x)
        assertEquals(0.0, deep.outline.first().y)
    }

    @Test
    fun `a sketch needs two depths`() {
        assertNull(sketchGeometry(profile(listOf(5.0)), 20.0))
        assertNull(sketchGeometry(profile(emptyList()), 20.0))
    }

    @Test
    fun `fast ascent bands keep their band and follow the outline`() {
        val band = listOf(JsonPrimitive(600.0), JsonPrimitive(900.0), JsonPrimitive(3))
        val sketch = sketchGeometry(profile(listOf(0.0, 10.0, 20.0, 10.0, 0.0), listOf(band)), 40.0)!!
        assertEquals(3, sketch.bands.single().band)
        val points = sketch.bands.single().points
        assertTrue(points.first().x < points.last().x)
        assertTrue(points.all { it.y in 0.0..SKETCH_HEIGHT })
    }
}

class LogbookFormatTest {
    @Test
    fun `a mix is named the way divers say it`() {
        assertEquals("Air", mixName(21.0, 0.0, "Air"))
        assertEquals("EAN32", mixName(32.0, 0.0, "Air"))
        assertEquals("18/45", mixName(18.0, 45.0, "Air"))
    }

    @Test
    fun `circles take two letters from the name`() {
        assertEquals("AB", initials("Ada Byron"))
        assertEquals("AD", initials("Ada"))
        assertEquals("", initials(" "))
    }

    @Test
    fun `durations round to whole minutes`() {
        assertEquals(50L, durationMinutes(2999.0))
        assertEquals(50L, durationMinutes(3000.0))
        assertEquals(1L, durationMinutes(31.0))
    }
}

private const val EMPTY_PAGE = """{"dives":[],"total":0,"counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},"totals":{"dives":0,"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}"""

private class RepositoryHarness(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData) {
    private val api = dev.divehub.shared.ApiClient(
        engine = MockEngine(handler),
        token = { "token" },
        onUnauthorized = {},
        json = Json { ignoreUnknownKeys = true; explicitNulls = false },
    ).apply { baseUrl = "https://dive.example" }
    val repository = LogbookRepository(api)
}

private fun MockRequestHandleScope.json(body: String): HttpResponseData =
    respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))

class LogbookRepositoryTest {
    @Test
    fun `a page is read from the path with its filters`() = runTest {
        var path: String? = null
        val harness = RepositoryHarness { request ->
            path = request.url.encodedPath + "?" + request.url.encodedQuery
            json(EMPTY_PAGE)
        }
        val page = harness.repository.page(2, setOf(LogbookFilter.NO_SITE))
        assertEquals("/api/dives?limit=25&offset=25&only=no-site", path)
        assertEquals(0, page.total)
        assertTrue(page.dives.isEmpty())
    }

    @Test
    fun `the provider directory names what the rows and the filter show`() = runTest {
        val harness = RepositoryHarness { request ->
            when (request.url.encodedPath) {
                "/api/providers" -> json(
                    """[
                      {"id":"ssi","name":"SSI","signIn":{"kind":"password","login":"email","canKeepPassword":false,"createUrl":null,"expires":false},
                       "data":{"dives":null,"diveSites":null,"buddies":null,"certifications":null},"notices":[],"limits":{"pauseMs":1000,"run":null,"fetchEveryMinutes":null}},
                      {"id":"other","name":"Other","signIn":{"kind":"none","login":null,"canKeepPassword":false,"createUrl":null,"expires":false},
                       "data":{"dives":null,"diveSites":null,"buddies":null,"certifications":null},"notices":[],"limits":{"pauseMs":1000,"run":null,"fetchEveryMinutes":null}}
                    ]""",
                )
                "/api/connections" -> json(
                    """[{"id":"c1","provider":"ssi","diverId":"diver","diverName":"Ada","accountId":"a","accountLabel":"ada@example.com",
                        "keepSignedIn":false,"state":"active","lastUsedAt":null,"createdAt":"2026-01-01T00:00:00Z","diveNumbers":null,
                        "diveImport":null,"fetchKinds":[],"keyExpiresOn":null,"keyExpiresSoon":false,"sendChanges":false,
                        "sendNewDives":false,"deleteAtProvider":false,"fetchEveryMinutes":null,"offers":{"sendChanges":false,"sendNewDives":false,"deleteAtProvider":false,"fetchEvery":[]},"waiting":{"waiting":0,"held":0,"stopped":0},"lastFetch":null,
                        "unsentDives":[]}]""",
                )
                else -> error("unexpected ${request.url}")
            }
        }
        val directory = harness.repository.providers()
        assertEquals(listOf("SSI"), directory.connected)
        assertEquals("SSI", directory.nameOf("ssi"))
        assertEquals("unknown", directory.nameOf("unknown"))
    }

    @Test
    fun `provider names never fail the logbook`() = runTest {
        val harness = RepositoryHarness { request ->
            when (request.url.encodedPath) {
                "/api/providers" -> respond("boom", HttpStatusCode.InternalServerError)
                else -> json("[]")
            }
        }
        assertEquals(ProviderDirectory.EMPTY, harness.repository.providers())
    }
}
