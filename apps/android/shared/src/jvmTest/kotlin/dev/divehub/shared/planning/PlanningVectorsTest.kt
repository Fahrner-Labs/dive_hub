// The Kotlin side of the planning golden vectors (FAH-268): reads the same committed JSON files as the server's
// vitest runner and checks the Kotlin port against the kernel-level doubles. The service view is the TypeScript
// service shells' rounding, checked by the server runner.
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
        val values = sacValues(dive, cylinders).sac
        val expected = case.obj("expected").obj("kernel")

        assertNullableClose("litresPerMinute", values?.litresPerMinute, expected["litresPerMinute"], tolerance)
        assertNullableClose("barPerMinute", values?.barPerMinute, expected["barPerMinute"], tolerance)
        assertNullableClose("surfaceMinutes", values?.surfaceMinutes, expected["surfaceMinutes"], tolerance)
        val missing = expected["missing"]
        if (missing == null || missing is JsonNull) assertNull(sacValues(dive, cylinders).missing, "missing")
        else assertEquals(missing.jsonPrimitive.content, sacValues(dive, cylinders).missing, "missing")
    }
}
