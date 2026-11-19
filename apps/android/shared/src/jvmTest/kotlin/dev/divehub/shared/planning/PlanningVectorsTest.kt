// The Kotlin side of the planning golden vectors (FAH-268): reads the same committed JSON files as the server's
// vitest runner and checks the Kotlin port against the kernel-level doubles. The other service views are the
// TypeScript service shells' rounding, checked by the server runner; SAC's rounded view is ported too and checked here.
package dev.divehub.shared.planning

import java.io.File
import kotlin.math.abs
import kotlin.math.floor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.long
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class PlanningVectorsTest {
    private val json = Json { ignoreUnknownKeys = true }
    private val vectors = File(
        System.getProperty("divehub.vectors.dir")
            ?: error("divehub.vectors.dir is not set; the Android lane points it at packages/test-vectors/planning"),
    )

    private fun each(kernel: String, check: (JsonObject, Double) -> Unit) {
        val file = json.parseToJsonElement(File(vectors, "$kernel.json").readText()).jsonObject
        assertEquals(kernel, file.getValue("kernel").jsonPrimitive.content, "$kernel: kernel")
        assertEquals(1, file.getValue("version").jsonPrimitive.int, "$kernel: version")
        val tolerance = file["tolerance"]?.jsonObject?.get("relative")?.jsonPrimitive?.double ?: 1e-9
        for (element in file.getValue("cases").jsonArray) {
            val case = element.jsonObject
            try {
                check(case, tolerance)
            } catch (failure: AssertionError) {
                throw AssertionError("$kernel/${case.getValue("id").jsonPrimitive.content}: ${failure.message}", failure)
            }
        }
    }

    private fun JsonObject.number(key: String): Double = getValue(key).jsonPrimitive.double
    private fun JsonObject.text(key: String): String = getValue(key).jsonPrimitive.content
    private fun JsonObject.flag(key: String): Boolean = getValue(key).jsonPrimitive.boolean
    private fun JsonObject.obj(key: String): JsonObject = getValue(key).jsonObject
    private fun JsonObject.array(key: String): JsonArray = getValue(key).jsonArray

    private fun JsonObject.nullableNumber(key: String): Double? =
        get(key)?.takeIf { it !is JsonNull }?.jsonPrimitive?.double

    private fun JsonObject.nullableLong(key: String): Long? =
        get(key)?.takeIf { it !is JsonNull }?.jsonPrimitive?.long

    private fun JsonObject.nullableInt(key: String): Int? =
        get(key)?.takeIf { it !is JsonNull }?.jsonPrimitive?.int

    private fun JsonObject.nullableText(key: String): String? =
        get(key)?.takeIf { it !is JsonNull }?.jsonPrimitive?.content

    private fun JsonObject.gas(key: String): GasMix {
        val mix = obj(key)
        return GasMix(mix.number("o2"), mix.number("he"))
    }

    private fun JsonObject.nullableGas(key: String): GasMix? =
        get(key)?.takeIf { it !is JsonNull }?.jsonObject?.let { GasMix(it.number("o2"), it.number("he")) }

    /** Integer-valued expected numbers are exact; every other number is within the relative tolerance. */
    private fun assertClose(path: String, actual: Double, expected: JsonElement?, tolerance: Double) {
        if (expected == null || expected is JsonNull) {
            assertTrue(actual.isInfinite(), "$path: expected infinity, got $actual")
            return
        }
        val value = expected.jsonPrimitive.double
        if (value == floor(value) && abs(value) < 1e15) {
            assertEquals(value, actual, path)
            return
        }
        val difference = abs(actual - value)
        val scale = maxOf(abs(actual), abs(value), 1.0)
        assertTrue(difference <= tolerance * scale, "$path: $actual is not within $tolerance of $value")
    }

    private fun assertNullableClose(path: String, actual: Double?, expected: JsonElement?, tolerance: Double) {
        if (expected == null || expected is JsonNull) {
            assertNull(actual, path)
            return
        }
        assertNotNull(actual, path)
        assertClose(path, actual, expected, tolerance)
    }

    private fun assertCloseArray(path: String, actual: List<Double>, expected: JsonElement?, tolerance: Double) {
        val values = expected?.jsonArray ?: error("$path: no expected array")
        assertEquals(values.size, actual.size, "$path: length")
        values.forEachIndexed { i, value -> assertClose("$path[$i]", actual[i], value, tolerance) }
    }

    private fun assertNullableCloseArray(path: String, actual: List<Double>?, expected: JsonElement?, tolerance: Double) {
        if (expected == null || expected is JsonNull) {
            assertNull(actual, path)
            return
        }
        assertCloseArray(path, assertNotNull(actual, path), expected, tolerance)
    }

    private fun assertMissing(path: String, expected: JsonElement?, actual: SacMissing?) {
        if (expected == null || expected is JsonNull) assertNull(actual, path)
        else assertEquals(expected.jsonPrimitive.content, actual?.name, path)
    }

    @Test
    fun `gas vectors match the Kotlin port`() = each("gas") { case, tolerance ->
        val input = case.obj("input")
        val gas = GasMix(input.number("o2"), input.number("he"))
        val conditions = Conditions(PlanningWater.valueOf(input.text("water")), input.number("altitudeM"))
        val depthM = input.number("depthM")
        val working = input.number("ppo2Working")
        val contingency = input.number("ppo2Contingency")
        val oxygenNarcotic = input.flag("oxygenNarcotic")
        val expected = case.obj("expected").obj("kernel")

        assertClose("surfacePressureBar", surfacePressureBar(conditions.altitudeM), expected["surfacePressureBar"], tolerance)
        assertClose("metresPerBar", metresPerBar(conditions), expected["metresPerBar"], tolerance)
        assertClose("ambientPressureBar", ambientPressureBar(depthM, conditions), expected["ambientPressureBar"], tolerance)
        assertClose("modWorkingM", modMetres(gas, working, conditions), expected["modWorkingM"], tolerance)
        assertClose("modContingencyM", modMetres(gas, contingency, conditions), expected["modContingencyM"], tolerance)
        assertEquals(expected.getValue("bestMixPercent").jsonPrimitive.int, bestMixPercent(depthM, working, conditions), "bestMixPercent")
        assertClose("eadM", eadMetres(gas, depthM, conditions), expected["eadM"], tolerance)
        assertClose("endM", endMetres(gas, depthM, conditions, oxygenNarcotic), expected["endM"], tolerance)
        assertEquals(expected.getValue("hypoxic").jsonPrimitive.boolean, isHypoxic(gas), "hypoxic")
    }

    @Test
    fun `tissue vectors match the Kotlin port`() = each("tissues") { case, tolerance ->
        val input = case.obj("input")
        val gas = input.gas("gas")
        val conditions = Conditions(PlanningWater.valueOf(input.obj("conditions").text("water")), input.obj("conditions").number("altitudeM"))
        val points = input.array("points").map {
            val point = it.jsonObject
            TissuePoint(point.number("tSeconds"), point.number("depthM"))
        }
        val initial = input["initialTissues"]?.takeIf { it !is JsonNull }?.jsonArray?.map { it.jsonPrimitive.double }
        val clean = surfaceSaturatedTissues(conditions)
        val expected = case.obj("expected").obj("kernel")

        assertCloseArray("surfaceSaturatedTissues", clean, expected["surfaceSaturatedTissues"], tolerance)
        val tissues = advanceTissues(initial ?: clean, points, gas, conditions)
        assertCloseArray("tissues", tissues, expected["tissues"], tolerance)
        assertClose(
            "worstCompartmentPercent",
            worstCompartmentPercent(tissues, input.number("gfHigh"), conditions),
            expected["worstCompartmentPercent"],
            tolerance,
        )
    }

    @Test
    fun `no-decompression vectors match the Kotlin port`() = each("ndl") { case, tolerance ->
        val input = case.obj("input")
        val initial = input["initialTissues"]?.takeIf { it !is JsonNull }?.jsonArray?.map { it.jsonPrimitive.double }
        val conditions = Conditions(PlanningWater.valueOf(input.obj("conditions").text("water")), input.obj("conditions").number("altitudeM"))
        val actual = ndlMinutes(
            NdlInput(input.gas("gas"), input.number("depthM"), input.number("gfHigh"), conditions, initial),
        )
        assertClose("minutes", actual, case.obj("expected").obj("kernel")["minutes"], tolerance)
    }

    @Test
    fun `oxygen vectors match the Kotlin port`() = each("oxygen") { case, tolerance ->
        val input = case.obj("input")
        val ppO2 = input.number("ppO2")
        val expected = case.obj("expected").obj("kernel")
        assertClose("limitMinutes", oxygenLimitMinutes(ppO2), expected["limitMinutes"], tolerance)
        assertClose("otu", otu(ppO2, input.number("minutes")), expected["otu"], tolerance)
    }

    @Test
    fun `real-gas vectors match the Kotlin port`() = each("real-gas") { case, tolerance ->
        val input = case.obj("input")
        val gas = input.gas("gas")
        val volumeL = input.number("volumeL")
        val bar = input.number("bar")
        val litres = input.number("litres")
        val expected = case.obj("expected").obj("kernel")

        assertClose("compressibility", compressibility(bar, gas), expected["compressibility"], tolerance)
        assertClose("surfaceLitres", surfaceLitres(volumeL, bar, gas), expected["surfaceLitres"], tolerance)
        assertClose("pressureForLitres", pressureForLitres(volumeL, litres, gas), expected["pressureForLitres"], tolerance)
    }

    @Test
    fun `SAC vectors match the Kotlin port`() = each("sac") { case, tolerance ->
        val input = case.obj("input")
        val diveInput = input.obj("dive")
        val dive = SacDive(diveInput.number("durationSeconds"), diveInput.nullableNumber("avgDepthM"))
        val cylinders = input.array("cylinders").map {
            val cylinder = it.jsonObject
            SacCylinder(
                cylinder.nullableNumber("volumeL"),
                cylinder.nullableNumber("startPressureBar"),
                cylinder.nullableNumber("endPressureBar"),
                cylinder.nullableGas("gas"),
            )
        }
        val values = sacValues(dive, cylinders)
        val expected = case.obj("expected").obj("kernel")

        assertNullableClose("litresPerMinute", values.sac?.litresPerMinute, expected["litresPerMinute"], tolerance)
        assertNullableClose("barPerMinute", values.sac?.barPerMinute, expected["barPerMinute"], tolerance)
        assertNullableClose("surfaceMinutes", values.sac?.surfaceMinutes, expected["surfaceMinutes"], tolerance)
        assertMissing("missing", expected["missing"], values.missing)

        // The port also ships the route's two-place view, so pin it too.
        val service = sacOfDive(dive, cylinders)
        val expectedService = case.obj("expected").obj("service")
        val expectedSac = expectedService["sac"]
        if (expectedSac == null || expectedSac is JsonNull) {
            assertNull(service.sac, "service.sac")
        } else {
            val rounded = assertNotNull(service.sac, "service.sac")
            val block = expectedSac.jsonObject
            assertClose("service.litresPerMinute", rounded.litresPerMinute, block["litresPerMinute"], tolerance)
            assertNullableClose("service.barPerMinute", rounded.barPerMinute, block["barPerMinute"], tolerance)
        }
        assertMissing("service.missing", expectedService["missing"], service.missing)
    }

    private fun gasRule(rule: JsonObject): GasRule = when (val kind = rule.text("kind")) {
        "rock_bottom" -> GasRule.RockBottom
        "reserve" -> GasRule.Reserve(rule.number("bar"))
        "ascent_pressure" -> GasRule.AscentPressure(rule.number("bar"))
        "halves" -> GasRule.Turn(TurnRule.halves)
        "thirds" -> GasRule.Turn(TurnRule.thirds)
        "sixths" -> GasRule.Turn(TurnRule.sixths)
        else -> error("unknown gas rule $kind")
    }

    private fun JsonObject.conditions(): Conditions =
        Conditions(PlanningWater.valueOf(obj("conditions").text("water")), obj("conditions").number("altitudeM"))

    private fun JsonObject.optionalArray(key: String): JsonArray? =
        get(key)?.takeIf { it !is JsonNull }?.jsonArray

    @Test
    fun `gas-plan vectors match the Kotlin port`() = each("gas-plan") { case, tolerance ->
        val input = case.obj("input")
        val levels = input.array("levels").map {
            GasPlanLevel(it.jsonObject.number("depthM"), it.jsonObject.number("minutes"))
        }
        val divers = input.array("divers").map {
            val diver = it.jsonObject
            GasPlanDiver(
                diver.text("name"),
                diver.number("volumeL"),
                diver.number("startPressureBar"),
                diver.number("sacLpm"),
            )
        }
        val plan = planGas(
            GasPlanInput(
                gas = input.gas("gas"),
                conditions = input.conditions(),
                levels = levels,
                divers = divers,
                rule = gasRule(input.obj("rule")),
                ppO2Contingency = input.number("ppO2Contingency"),
                descentRateMPerMin = input.nullableNumber("descentRateMPerMin"),
                ascentRateMPerMin = input.nullableNumber("ascentRateMPerMin"),
            ),
        )
        val expected = case.obj("expected").obj("kernel")
        when (plan) {
            is GasPlan.OutsideLimits -> {
                assertEquals("outside_limits", expected.text("status"), "status")
                assertEquals(expected.text("reason"), plan.reason.name, "reason")
                val levelIndex = expected["levelIndex"]
                if (levelIndex == null || levelIndex is JsonNull) assertNull(plan.levelIndex, "levelIndex")
                else assertEquals(levelIndex.jsonPrimitive.int, plan.levelIndex, "levelIndex")
            }
            is GasPlan.Ok -> {
                assertEquals("ok", expected.text("status"), "status")
                assertClose("runtimeMinutes", plan.runtimeMinutes, expected["runtimeMinutes"], tolerance)
                val expectedSegments = expected.array("segments")
                assertEquals(expectedSegments.size, plan.segments.size, "segments: length")
                expectedSegments.forEachIndexed { i, element ->
                    val segment = plan.segments[i]
                    val block = element.jsonObject
                    assertEquals(block.text("kind"), segment.kind.name, "segments[$i].kind")
                    assertClose("segments[$i].fromDepthM", segment.fromDepthM, block["fromDepthM"], tolerance)
                    assertClose("segments[$i].depthM", segment.depthM, block["depthM"], tolerance)
                    assertClose("segments[$i].minutes", segment.minutes, block["minutes"], tolerance)
                    assertClose("segments[$i].ambientBar", segment.ambientBar, block["ambientBar"], tolerance)
                }
                val expectedDivers = expected.array("divers")
                assertEquals(expectedDivers.size, plan.divers.size, "divers: length")
                expectedDivers.forEachIndexed { i, element ->
                    val diver = plan.divers[i]
                    val block = element.jsonObject
                    assertEquals(block.text("name"), diver.name, "divers[$i].name")
                    assertClose("divers[$i].litres", diver.litres, block["litres"], tolerance)
                    assertClose("divers[$i].endPressureBar", diver.endPressureBar, block["endPressureBar"], tolerance)
                    val rockBottom = block.array("rockBottom")
                    assertEquals(rockBottom.size, diver.rockBottom.size, "divers[$i].rockBottom: length")
                    rockBottom.forEachIndexed { j, point ->
                        val expectedPoint = point.jsonObject
                        assertClose("divers[$i].rockBottom[$j].depthM", diver.rockBottom[j].depthM, expectedPoint["depthM"], tolerance)
                        assertClose("divers[$i].rockBottom[$j].pressureBar", diver.rockBottom[j].pressureBar, expectedPoint["pressureBar"], tolerance)
                    }
                    assertClose("divers[$i].ascentPressureBar", diver.ascentPressureBar, block["ascentPressureBar"], tolerance)
                    assertNullableClose("divers[$i].turnPressureBar", diver.turnPressureBar, block["turnPressureBar"], tolerance)
                    assertEquals(block.flag("belowRockBottom"), diver.belowRockBottom, "divers[$i].belowRockBottom")
                    assertClose("divers[$i].atAscentStartBar", diver.atAscentStartBar, block["atAscentStartBar"], tolerance)
                    assertCloseArray("divers[$i].pressuresBar", diver.pressuresBar, block["pressuresBar"], tolerance)
                    assertClose("divers[$i].marginBar", diver.marginBar, block["marginBar"], tolerance)
                    assertClose("divers[$i].marginMinutes", diver.marginMinutes, block["marginMinutes"], tolerance)
                    assertEquals(block.flag("controlling"), diver.controlling, "divers[$i].controlling")
                }
                assertEquals(expected.getValue("controllingIndex").jsonPrimitive.int, plan.controllingIndex, "controllingIndex")
                assertEquals(expected.flag("fits"), plan.fits, "fits")
                assertClose("leaveLastLevelAtMinute", plan.leaveLastLevelAtMinute, expected["leaveLastLevelAtMinute"], tolerance)
            }
        }
    }

    @Test
    fun `bottom-time vectors match the Kotlin port`() = each("bottom-time") { case, tolerance ->
        val input = case.obj("input")
        val cylinder = input["cylinder"]?.takeIf { it !is JsonNull }?.jsonObject?.let {
            BottomTimeCylinder(it.number("volumeL"), it.number("startPressureBar"))
        }
        val initialTissues = input.optionalArray("initialTissues")?.map { it.jsonPrimitive.double }
        val ppO2 = input.obj("ppO2")
        val result = bottomTime(
            BottomTimeInput(
                gas = input.gas("gas"),
                depthM = input.number("depthM"),
                gfHigh = input.number("gfHigh"),
                conditions = input.conditions(),
                cylinder = cylinder,
                sacLpm = input.number("sacLpm"),
                rule = gasRule(input.obj("rule")),
                ppO2 = PpO2Limits(ppO2.number("working"), ppO2.number("contingency")),
                initialTissues = initialTissues,
            ),
        )
        val expected = case.obj("expected").obj("kernel")
        when (result) {
            is BottomTime.OutsideLimits -> {
                assertEquals("outside_limits", expected.text("status"), "status")
                assertEquals(expected.text("reason"), result.reason.name, "reason")
                assertNullableClose("modM", result.modM, expected["modM"], tolerance)
            }
            is BottomTime.Ok -> {
                assertEquals("ok", expected.text("status"), "status")
                assertClose("minutes", result.minutes, expected["minutes"], tolerance)
                assertEquals(
                    expected.array("binding").map { it.jsonPrimitive.content },
                    result.binding.map { it.name },
                    "binding",
                )
                val limits = expected.obj("limits")
                assertClose("limits.ndl", result.limits.ndl, limits["ndl"], tolerance)
                assertClose("limits.oxygen", result.limits.oxygen, limits["oxygen"], tolerance)
                assertNullableClose("limits.gas", result.limits.gas, limits["gas"], tolerance)
                assertClose("otu", result.otu, expected["otu"], tolerance)
                assertClose("ppO2AtDepth", result.ppO2AtDepth, expected["ppO2AtDepth"], tolerance)
                assertNullableClose("ascentPressureBar", result.ascentPressureBar, expected["ascentPressureBar"], tolerance)
                assertEquals(expected.flag("ppO2AboveWorking"), result.ppO2AboveWorking, "ppO2AboveWorking")
            }
        }
    }

    @Test
    fun `planning-sac vectors match the Kotlin port`() = each("planning-sac") { case, tolerance ->
        val dives = case.obj("input").array("dives").map {
            PlanningSacDive(it.jsonObject.nullableNumber("sacLpm"))
        }
        val result = planningSac(dives)
        val expected = case.obj("expected").obj("kernel")
        assertClose("lpm", result.lpm, expected["lpm"], tolerance)
        assertEquals(expected.getValue("basedOn").jsonPrimitive.int, result.basedOn, "basedOn")
        assertEquals(expected.text("source"), result.source.name, "source")
    }

    @Test
    fun `loadings vectors match the Kotlin port`() = each("loadings") { case, tolerance ->
        val input = case.obj("input")
        val dives = input.array("dives").map { element ->
            val dive = element.jsonObject
            LoadingDive(
                startMs = dive.getValue("startMs").jsonPrimitive.long,
                endMs = dive.getValue("endMs").jsonPrimitive.long,
                samples = dive.array("samples").map { point ->
                    val sample = point.jsonObject
                    TissuePoint(sample.number("tSeconds"), sample.number("depthM"))
                },
                gas = dive.nullableGas("gas"),
                otuStart = dive.nullableNumber("otuStart"),
                otuEnd = dive.nullableNumber("otuEnd"),
            )
        }
        val nowMs = input.getValue("nowMs").jsonPrimitive.long
        val gfHigh = input.number("gfHigh")
        val actual = tissuesAfterDives(dives, nowMs, gfHigh)
        val expected = case.obj("expected").obj("kernel")
        val estimate = actual as? N2Loadings.Estimate
        val unavailable = actual as? N2Loadings.Unavailable

        assertNullableClose("worstCompartmentPercent", estimate?.worstCompartmentPercent, expected["worstCompartmentPercent"], tolerance)
        assertEquals(expected.nullableText("reason"), unavailable?.reason?.name, "reason")
        assertClose("gfHigh", actual.gfHigh, expected["gfHigh"], tolerance)
        val basis = expected.obj("basis")
        assertEquals(basis.getValue("windowHours").jsonPrimitive.int, actual.basis.windowHours, "basis.windowHours")
        assertEquals(basis.getValue("divesUsed").jsonPrimitive.int, actual.basis.divesUsed, "basis.divesUsed")
        assertEquals(
            basis.getValue("divesSkippedNoProfile").jsonPrimitive.int,
            actual.basis.divesSkippedNoProfile,
            "basis.divesSkippedNoProfile",
        )
        assertEquals(basis.nullableText("lastDiveEndedAt"), actual.basis.lastDiveEndedAt, "basis.lastDiveEndedAt")
        assertNullableCloseArray("tissues", estimate?.tissues, expected["tissues"], tolerance)

        val cnsInput = input["cns"]?.takeIf { it !is JsonNull }?.jsonObject
        val cnsNow = cnsInput?.let {
            cnsDecayed(it.number("cnsEnd"), (nowMs - it.getValue("endedAtMs").jsonPrimitive.long) / 60_000.0)
        }
        assertNullableClose("cnsPercentNow", cnsNow, expected["cnsPercentNow"], tolerance)
        assertNullableClose("otuLast24Hours", otuLast24h(dives, nowMs), expected["otuLast24Hours"], tolerance)
    }

    @Test
    fun `no-fly vectors match the Kotlin port`() = each("no-fly") { case, tolerance ->
        val input = case.obj("input")
        val expected = case.obj("expected").obj("kernel")
        val expectedDives = expected.array("dives")
        val series = input.array("dives").mapIndexedNotNull { index, element ->
            val dive = element.jsonObject
            val ndl = dive["ndlSeconds"]?.takeIf { it !is JsonNull }?.jsonArray?.map { it.jsonPrimitive.double } ?: emptyList()
            val pinned = expectedDives[index].jsonObject
            assertEquals(pinned.nullableText("diveMode"), dive.nullableText("diveMode"), "diveMode")
            assertEquals(pinned.flag("applies"), seriesRulesApply(dive.nullableText("diveMode")), "applies")
            assertNullableClose("minNdlSeconds", minNdlSeconds(ndl), pinned["minNdlSeconds"], tolerance)
            assertEquals(pinned.flag("enteredDeco"), enteredDecoFromNdl(ndl), "enteredDeco")
            if (!seriesRulesApply(dive.nullableText("diveMode"))) {
                return@mapIndexedNotNull null
            }
            seriesDiveOf(
                SeriesDiveInput(
                    id = dive.text("id"),
                    startMs = dive.getValue("startMs").jsonPrimitive.long,
                    utcOffsetSeconds = dive.nullableInt("utcOffsetSeconds"),
                    durationSeconds = dive.number("durationSeconds"),
                    maxDepthM = dive.nullableNumber("maxDepthM"),
                ),
                enteredDecoFromNdl(ndl),
            ).also { seriesDive ->
                assertEquals(pinned.text("day"), seriesDive.day, "day")
                assertEquals(pinned.getValue("startMs").jsonPrimitive.long, seriesDive.startMs, "startMs")
                assertClose("endMs", seriesDive.endMs, pinned["endMs"], tolerance)
            }
        }
        val actual = noFlyAfter(series, input.text("id"))
        val pinned = expected["noFly"]
        if (pinned == null || pinned is JsonNull) {
            assertNull(actual, "noFly")
            return@each
        }
        val noFly = assertNotNull(actual, "noFly")
        val block = pinned.jsonObject
        assertEquals(block.getValue("hours").jsonPrimitive.int, noFly.hours, "noFly.hours")
        assertEquals(block.text("reason"), noFly.reason.name, "noFly.reason")
        assertEquals(block.text("until"), noFly.until, "noFly.until")
    }
}
