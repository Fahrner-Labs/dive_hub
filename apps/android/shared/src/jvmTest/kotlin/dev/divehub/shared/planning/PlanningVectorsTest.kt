// The Kotlin side of the golden vectors (FAH-268, lead estimate FAH-272): reads the same committed JSON files as
// the server's vitest runner and checks the Kotlin port against the kernel-level doubles. The other service views
// are the TypeScript service shells' rounding, checked by the server runner; SAC's and the lead estimate's rounded
// views are ported too and checked here.
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
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.time.Instant

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

    private fun assertMissing(path: String, expected: JsonElement?, actual: SacMissing?) {
        if (expected == null || expected is JsonNull) assertNull(actual, path)
        else assertEquals(expected.jsonPrimitive.content, actual?.name, path)
    }

    // ---- The lead estimate's vectors (FAH-272).

    private fun JsonObject.nullableText(key: String): String? =
        get(key)?.takeIf { it !is JsonNull }?.jsonPrimitive?.content

    private fun JsonObject.suit(key: String): Suit? = get(key)?.takeIf { it !is JsonNull }?.jsonObject?.let { suit ->
        Suit(
            type = SuitType.valueOf(suit.text("type")),
            thicknessMm = suit.nullableNumber("thicknessMm"),
            hood = suit["hood"]?.takeIf { it !is JsonNull }?.jsonPrimitive?.boolean,
            undergarment = suit.nullableText("undergarment")?.let { SuitUndergarment.valueOf(it) },
        )
    }

    private fun JsonObject.cylinder(): EstimateCylinder =
        EstimateCylinder(number("volumeL"), nullableText("material")?.let { CylinderMaterial.valueOf(it) })

    private fun JsonObject.diveCylinder(): EstimateDiveCylinder = EstimateDiveCylinder(
        volumeL = number("volumeL"),
        material = nullableText("material")?.let { CylinderMaterial.valueOf(it) },
        endPressureBar = nullableNumber("endPressureBar"),
    )

    private fun assertLeadBasis(path: String, actual: EstimateBasis?, expected: JsonElement?, tolerance: Double) {
        if (expected == null || expected is JsonNull) {
            assertNull(actual, path)
            return
        }
        val block = expected.jsonObject
        when (val basis = assertNotNull(actual, path)) {
            is EstimateBasis.Dives -> {
                assertEquals("dives", block.text("kind"), "$path.kind")
                assertClose("$path.personal", basis.personal, block["personal"], tolerance)
                val dives = block.array("dives")
                assertEquals(dives.size, basis.dives.size, "$path.dives: length")
                dives.forEachIndexed { i, element ->
                    val dive = element.jsonObject
                    val use = basis.dives[i]
                    assertEquals(dive.text("id"), use.id, "$path.dives[$i].id")
                    assertClose("$path.dives[$i].carried", use.carried, dive["carried"], tolerance)
                    assertClose("$path.dives[$i].needed", use.needed, dive["needed"], tolerance)
                    assertClose("$path.dives[$i].weight", use.weight, dive["weight"], tolerance)
                }
            }
            is EstimateBasis.RuleOfThumb -> {
                assertEquals("rule_of_thumb", block.text("kind"), "$path.kind")
                assertClose("$path.rule", basis.rule, block["rule"], tolerance)
                assertNullableClose("$path.personalOffset", basis.personalOffset, block["personalOffset"], tolerance)
                assertEquals(block.getValue("offsetFromSuits").jsonPrimitive.int, basis.offsetFromSuits, "$path.offsetFromSuits")
            }
        }
    }

    private fun assertLeadAdjustments(path: String, actual: List<EstimateAdjustment>, expected: JsonArray, tolerance: Double) {
        assertEquals(expected.size, actual.size, "$path: length")
        expected.forEachIndexed { i, element ->
            val block = element.jsonObject
            when (val adjustment = actual[i]) {
                is EstimateAdjustment.Cylinder -> {
                    assertEquals("cylinder", block.text("kind"), "$path[$i].kind")
                    assertClose("$path[$i].amount", adjustment.amount, block["amount"], tolerance)
                }
                is EstimateAdjustment.Water -> {
                    assertEquals("water", block.text("kind"), "$path[$i].kind")
                    assertClose("$path[$i].amount", adjustment.amount, block["amount"], tolerance)
                    assertEquals(SiteWaterType.valueOf(block.text("water")), adjustment.water, "$path[$i].water")
                }
                is EstimateAdjustment.PersonalOffset -> {
                    assertEquals("personal_offset", block.text("kind"), "$path[$i].kind")
                    assertClose("$path[$i].amount", adjustment.amount, block["amount"], tolerance)
                }
            }
        }
    }

    private fun assertLeadMissing(path: String, actual: List<EstimateMissing>, expected: JsonArray) {
        assertEquals(expected.size, actual.size, "$path: length")
        expected.forEachIndexed { i, element ->
            val block = element.jsonObject
            val missing = actual[i]
            val kind = when (missing) {
                is EstimateMissing.BodyWeight -> "body_weight"
                is EstimateMissing.CylinderMaterial -> "cylinder_material"
                is EstimateMissing.Cylinder -> "cylinder"
                is EstimateMissing.SuitThickness -> "suit_thickness"
                is EstimateMissing.SuitUndergarment -> "suit_undergarment"
            }
            assertEquals(kind, block.text("kind"), "$path[$i].kind")
            if (missing is EstimateMissing.CylinderMaterial) {
                assertEquals(
                    block.array("diveIds").map { it.jsonPrimitive.content },
                    missing.diveIds,
                    "$path[$i].diveIds",
                )
                assertEquals(block.flag("planned"), missing.planned, "$path[$i].planned")
            }
            if (missing is EstimateMissing.Cylinder) {
                assertEquals(block.array("diveIds").map { it.jsonPrimitive.content }, missing.diveIds, "$path[$i].diveIds")
            }
        }
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

    @Test
    fun `lead-estimate vectors match the Kotlin port`() = each("lead-estimate") { case, tolerance ->
        val input = case.obj("input")
        val dives = input.array("dives").map {
            val dive = it.jsonObject
            EstimateDive(
                id = dive.text("id"),
                date = Instant.parse(dive.text("date")),
                leadKg = dive.nullableNumber("leadKg"),
                feedback = dive["feedback"]?.takeIf { feedback -> feedback !is JsonNull }?.jsonObject?.let { feedback ->
                    WeightingFeedback(WeightingFelt.valueOf(feedback.text("felt")), feedback.nullableNumber("amountKg"))
                },
                suit = dive.suit("suit"),
                water = dive.nullableText("water")?.let { SiteWaterType.valueOf(it) },
                cylinders = dive.array("cylinders").map { cylinder -> cylinder.jsonObject.diveCylinder() },
            )
        }
        val planInput = input.obj("plan")
        val plan = PlannedConditions(
            water = SiteWaterType.valueOf(planInput.text("water")),
            suit = assertNotNull(planInput.suit("suit"), "plan.suit"),
            cylinders = planInput.array("cylinders").map { cylinder -> cylinder.jsonObject.cylinder() },
        )
        val result = estimateLead(
            EstimateInput(
                dives = dives,
                bodyWeightKg = input.nullableNumber("bodyWeightKg"),
                plan = plan,
                unit = LeadUnit.valueOf(input.text("unit")),
                today = Instant.parse(input.text("today")),
            ),
        )
        val expected = case.obj("expected").obj("kernel")

        assertEquals(expected.text("unit"), result.unit.name, "unit")
        assertNullableClose("total", result.total, expected["total"], tolerance)
        val expectedRange = expected["range"]?.takeIf { it !is JsonNull }?.jsonObject
        if (expectedRange == null) {
            assertNull(result.range, "range")
        } else {
            val range = assertNotNull(result.range, "range")
            assertClose("range.low", range.low, expectedRange["low"], tolerance)
            assertClose("range.high", range.high, expectedRange["high"], tolerance)
        }
        assertEquals(expected.text("confidence"), result.confidence.name, "confidence")
        assertLeadBasis("basis", result.basis, expected["basis"], tolerance)
        assertLeadAdjustments("adjustments", result.adjustments, expected.array("adjustments"), tolerance)
        assertLeadMissing("missing", result.missing, expected.array("missing"))
        val reserve = expected.obj("reserve")
        assertClose("reserve.bar", result.reserve.bar, reserve["bar"], tolerance)
        assertEquals(reserve.flag("fromDives"), result.reserve.fromDives, "reserve.fromDives")
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
}
