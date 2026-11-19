package dev.divehub.shared.tools

import dev.divehub.shared.planning.BottomTime
import dev.divehub.shared.planning.BottomTimeCylinder
import dev.divehub.shared.planning.BottomTimeInput
import dev.divehub.shared.planning.BottomTimeLimit
import dev.divehub.shared.planning.BottomTimeOutside
import dev.divehub.shared.planning.Conditions
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.GasRule
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.planning.PpO2Limits
import dev.divehub.shared.planning.bottomTime as kernelBottomTime
import dev.divehub.shared.planning.modMetres
import kotlin.math.floor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * The offline tools' shaping (FAH-274): defaults, `used`/`source`, `binding`, `outside_limits` and the route's
 * rounding. The values themselves are the kernels' and pinned by the shared golden vectors; these tests pin the shell.
 */
class ToolsServiceTest {
    private fun errorCode(block: () -> Unit): String =
        assertFailsWith<ToolsError> { block() }.code

    @Test
    fun `gas numbers answer the route's defaults and round the MOD down`() {
        val answer = gasNumbers(GasNumbersInput(o2 = 32.0, depthM = 30.0))

        assertEquals(32.0, answer.gas.o2)
        assertEquals(0.0, answer.gas.he)
        assertEquals(1.4, answer.ppO2Limits.working)
        assertEquals(1.6, answer.ppO2Limits.contingency)
        assertEquals("salt", answer.conditions.water)
        assertEquals(0.0, answer.conditions.altitudeM)
        assertTrue(answer.oxygenNarcotic)
        assertEquals(33.4, answer.modWorkingM)
        assertEquals(39.6, answer.modContingencyM)

        val atDepth = assertNotNull(answer.atDepth)
        assertEquals(30.0, atDepth.depthM)
        assertEquals(1.29, atDepth.ppO2)
        assertEquals(34, atDepth.bestMixPercent)
        assertEquals(24.4, atDepth.eadM)
        assertEquals(30.0, atDepth.endM)
    }

    @Test
    fun `the MOD is never rounded up`() {
        val answer = gasNumbers(GasNumbersInput(o2 = 36.0, water = PlanningWater.fresh))
        val exact = modMetres(GasMix(36.0, 0.0), 1.4, Conditions(PlanningWater.fresh, 0.0))
        assertTrue(answer.modWorkingM <= exact)
        assertTrue(exact - answer.modWorkingM < 0.1)
        assertTrue(floor(answer.modWorkingM * 10) == answer.modWorkingM * 10)
    }

    @Test
    fun `gas numbers keep their request sources`() {
        val answer = gasNumbers(
            GasNumbersInput(
                o2 = 21.0,
                he = 35.0,
                depthM = 40.0,
                water = PlanningWater.fresh,
                altitudeM = 300.0,
                ppo2Working = 1.3,
                ppo2Contingency = 1.5,
                oxygenNarcotic = false,
            ),
        )
        assertEquals(21.0, answer.gas.o2)
        assertEquals(35.0, answer.gas.he)
        assertEquals(1.3, answer.ppO2Limits.working)
        assertEquals(1.5, answer.ppO2Limits.contingency)
        assertEquals("fresh", answer.conditions.water)
        assertEquals(300.0, answer.conditions.altitudeM)
        assertEquals(false, answer.oxygenNarcotic)
    }

    @Test
    fun `gas numbers have no depth answer without a depth`() {
        assertNull(gasNumbers(GasNumbersInput(o2 = 32.0)).atDepth)
    }

    @Test
    fun `gas numbers refuse an impossible or unbreathable mix`() {
        assertEquals(ToolsError.INVALID_INPUT, errorCode { gasNumbers(GasNumbersInput(o2 = 60.0, he = 50.0)) })
        assertEquals(ToolsError.GAS_NOT_BREATHABLE, errorCode { gasNumbers(GasNumbersInput(o2 = 15.0)) })
    }

    @Test
    fun `bottom time uses the route's defaults when nothing else is known`() {
        val answer = bottomTime(BottomTimeToolInput(depthM = 18.0, o2 = 21.0))

        assertEquals("ok", answer.status)
        assertEquals("default", answer.used.gfHigh.source)
        assertEquals(85.0, answer.used.gfHigh.value)
        assertEquals("default", answer.used.sacLpm.source)
        assertEquals(20.0, answer.used.sacLpm.value)
        assertNull(answer.used.sacLpm.basedOn)
        assertEquals("default", answer.used.water.source)
        assertEquals("salt", answer.used.water.value)
        assertEquals("default", answer.used.altitudeM.source)
        assertEquals(0.0, answer.used.altitudeM.value)
        assertEquals("default", answer.used.gasRule.source)
        assertEquals("rock_bottom", answer.used.gasRule.value.kind)
        assertNull(answer.used.gasRule.value.bar)
        assertNull(answer.residualN2)
        assertNull(answer.recentDiveEndedAt)

        val kernel = kernelBottomTime(
            BottomTimeInput(
                gas = GasMix(21.0, 0.0),
                depthM = 18.0,
                gfHigh = 85.0,
                conditions = Conditions(PlanningWater.salt, 0.0),
                cylinder = null,
                sacLpm = 20.0,
                rule = GasRule.RockBottom,
                ppO2 = PpO2Limits(1.4, 1.6),
            ),
        ) as BottomTime.Ok
        assertEquals(kernel.minutes.toInt(), answer.minutes)
        assertEquals(kernel.binding.map(BottomTimeLimit::name), answer.binding)
        assertEquals(kernel.limits.ndl, answer.limits!!.ndl)
        assertNull(answer.limits!!.gas)
    }

    @Test
    fun `a synced Diver supplies the recording's GF, the logbook SAC and basedOn`() {
        val sources = ToolSources(
            ownDiverId = "diver",
            gfHigh = 80.0,
            planningSac = PlanningSacResult(17.5, 12, PlanningSacSource.logbook),
            recentDiveEndedAt = "2026-11-18T09:00:00Z",
        )
        val answer = bottomTime(BottomTimeToolInput(depthM = 20.0, o2 = 32.0), sources)

        assertEquals("recording", answer.used.gfHigh.source)
        assertEquals(80.0, answer.used.gfHigh.value)
        assertEquals("logbook", answer.used.sacLpm.source)
        assertEquals(17.5, answer.used.sacLpm.value)
        assertEquals(12, answer.used.sacLpm.basedOn)
        assertEquals("2026-11-18T09:00:00Z", answer.recentDiveEndedAt)
        assertNull(answer.residualN2)
    }

    @Test
    fun `the request overrides the synced Diver and the default stands until enough Dives`() {
        val few = ToolSources(
            ownDiverId = "diver",
            gfHigh = 80.0,
            planningSac = PlanningSacResult(20.0, 3, PlanningSacSource.default),
        )
        val answer = bottomTime(
            BottomTimeToolInput(depthM = 20.0, o2 = 32.0, gfHigh = 90.0, sacLpm = 14.0),
            few,
        )
        assertEquals("request", answer.used.gfHigh.source)
        assertEquals(90.0, answer.used.gfHigh.value)
        assertEquals("request", answer.used.sacLpm.source)
        assertEquals(14.0, answer.used.sacLpm.value)
        assertNull(answer.used.sacLpm.basedOn)
    }

    @Test
    fun `a default SAC with a Diver still says how many Dives it rests on`() {
        val sources = ToolSources(
            ownDiverId = "diver",
            planningSac = PlanningSacResult(20.0, 3, PlanningSacSource.default),
        )
        val answer = bottomTime(BottomTimeToolInput(depthM = 20.0, o2 = 32.0), sources)
        assertEquals("default", answer.used.sacLpm.source)
        assertEquals(3, answer.used.sacLpm.basedOn)
    }

    @Test
    fun `outside limits give the reason and a floored MOD, never a number`() {
        val answer = bottomTime(BottomTimeToolInput(depthM = 45.0, o2 = 21.0))
        assertEquals("outside_limits", answer.status)
        assertEquals("depth", answer.reason)
        assertNull(answer.minutes)
        assertNull(answer.limits)
        assertNull(answer.binding)
        assertNotNull(answer.modM)
        assertTrue(answer.modM!! <= 65.72)
    }

    @Test
    fun `an unsupported mix is outside the limits with no MOD`() {
        val answer = bottomTime(BottomTimeToolInput(depthM = 30.0, o2 = 21.0, he = 20.0))
        assertEquals("outside_limits", answer.status)
        assertEquals(BottomTimeOutside.gas.name, answer.reason)
        assertNull(answer.modM)
    }

    @Test
    fun `a cylinder fills the gas limit and the ascent pressure`() {
        val answer = bottomTime(
            BottomTimeToolInput(depthM = 20.0, o2 = 21.0, volumeL = 12.0, pressureBar = 200.0),
        )
        assertEquals("ok", answer.status)
        assertNotNull(answer.limits!!.gas)
        assertNotNull(answer.ascentPressureBar)
        assertTrue(answer.binding!!.contains(BottomTimeLimit.gas.name) || answer.binding!!.contains(BottomTimeLimit.ndl.name))
    }

    @Test
    fun `the request's gas rule is shaped with its bar`() {
        val answer = bottomTime(
            BottomTimeToolInput(depthM = 20.0, o2 = 21.0, volumeL = 12.0, pressureBar = 200.0, rule = RULE_RESERVE, bar = 70.0),
        )
        assertEquals("request", answer.used.gasRule.source)
        assertEquals(RULE_RESERVE, answer.used.gasRule.value.kind)
        assertEquals(70.0, answer.used.gasRule.value.bar)
    }

    @Test
    fun `bottom time refuses the inputs the route refuses`() {
        assertEquals(ToolsError.INVALID_INPUT, errorCode { bottomTime(BottomTimeToolInput(20.0, 21.0, volumeL = 12.0)) })
        assertEquals(
            ToolsError.INVALID_INPUT,
            errorCode { bottomTime(BottomTimeToolInput(20.0, 21.0, rule = RULE_RESERVE)) },
        )
        assertEquals(ToolsError.INVALID_INPUT, errorCode { bottomTime(BottomTimeToolInput(20.0, 60.0, he = 50.0)) })
    }

    private fun plan(
        divers: List<GasPlanToolDiver> = listOf(GasPlanToolDiver("Elena", 12.0, 200.0)),
        levels: List<GasPlanToolLevel> = listOf(GasPlanToolLevel(20.0, 20.0)),
        sources: ToolSources = ToolSources(),
        o2: Double = 21.0,
        rule: String? = null,
        bar: Double? = null,
    ) = gasPlan(
        GasPlanToolInput(o2 = o2, levels = levels, divers = divers, rule = rule, bar = bar),
        sources,
    )

    @Test
    fun `a plan answers the route's defaults`() {
        val answer = plan()

        assertEquals("ok", answer.status)
        assertEquals("default", answer.used.water.source)
        assertEquals("salt", answer.used.water.value)
        assertEquals("default", answer.used.altitudeM.source)
        assertEquals(0.0, answer.used.altitudeM.value)
        assertEquals("default", answer.used.gasRule.source)
        assertEquals("rock_bottom", answer.used.gasRule.value.kind)
        assertNull(answer.used.gasRule.value.bar)
        assertEquals("default", answer.used.descentRateMPerMin.source)
        assertEquals(18.0, answer.used.descentRateMPerMin.value)
        assertEquals("default", answer.used.ascentRateMPerMin.source)
        assertEquals(9.0, answer.used.ascentRateMPerMin.value)

        val diver = answer.divers!!.single()
        assertEquals("default", diver.sacLpm.source)
        assertEquals(20.0, diver.sacLpm.value)
        assertNull(diver.sacLpm.basedOn)
        assertTrue(diver.controlling)
        assertTrue(diver.rockBottom.isNotEmpty())
        assertEquals(0, answer.controllingIndex)
        assertEquals(listOf("descent", "level", "ascent", "safety_stop", "ascent"), answer.segments!!.map { it.kind })
    }

    @Test
    fun `a synced Diver brings their planning SAC and recent Dive`() {
        val sources = ToolSources(
            divers = listOf(
                ToolDiverSource(
                    id = "diver",
                    name = "Elena",
                    isOwn = true,
                    planningSac = PlanningSacResult(17.5, 12, PlanningSacSource.logbook),
                    recentDiveEndedAt = "2026-11-18T09:00:00Z",
                ),
            ),
        )
        val answer = plan(divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0, diverId = "diver")), sources = sources)

        val diver = answer.divers!!.single()
        assertEquals("logbook", diver.sacLpm.source)
        assertEquals(17.5, diver.sacLpm.value)
        assertEquals(12, diver.sacLpm.basedOn)
        assertEquals("2026-11-18T09:00:00Z", diver.recentDiveEndedAt)
    }

    @Test
    fun `a typed SAC wins and a guest has no logbook value`() {
        val sources = ToolSources(
            divers = listOf(
                ToolDiverSource(id = "diver", name = "Elena", isOwn = true, planningSac = PlanningSacResult(17.5, 12, PlanningSacSource.logbook)),
            ),
        )
        val typed = plan(
            divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0, sacLpm = 14.0, diverId = "diver")),
            sources = sources,
        ).divers!!.single()
        assertEquals("request", typed.sacLpm.source)
        assertEquals(14.0, typed.sacLpm.value)
        assertNull(typed.sacLpm.basedOn)

        val guest = plan(divers = listOf(GasPlanToolDiver("Bob", 12.0, 200.0, diverId = "stranger")), sources = sources)
            .divers!!.single()
        assertEquals("default", guest.sacLpm.source)
        assertNull(guest.sacLpm.basedOn)
        assertNull(guest.recentDiveEndedAt)
    }

    @Test
    fun `a default SAC with a synced Diver says how many Dives it rests on`() {
        val sources = ToolSources(
            divers = listOf(
                ToolDiverSource(
                    id = "diver",
                    name = "Elena",
                    isOwn = true,
                    planningSac = PlanningSacResult(20.0, 3, PlanningSacSource.default),
                ),
            ),
        )
        val diver = plan(divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0, diverId = "diver")), sources = sources)
            .divers!!.single()
        assertEquals("default", diver.sacLpm.source)
        assertEquals(3, diver.sacLpm.basedOn)
    }

    @Test
    fun `a turn rule gives a turn pressure and keeps rock bottom`() {
        val answer = plan(rule = RULE_THIRDS)
        assertEquals("request", answer.used.gasRule.source)
        assertEquals(RULE_THIRDS, answer.used.gasRule.value.kind)
        val diver = answer.divers!!.single()
        assertNotNull(diver.turnPressureBar)
        assertTrue(diver.rockBottom.isNotEmpty())
    }

    @Test
    fun `a plan outside the limits has no figures`() {
        val answer = plan(o2 = 50.0)
        assertEquals("outside_limits", answer.status)
        assertEquals("gas", answer.reason)
        assertNull(answer.levelIndex)
        assertNull(answer.divers)
        assertNull(answer.segments)
        assertNull(answer.runtimeMinutes)
    }

    @Test
    fun `a plan refuses the inputs the route refuses`() {
        assertEquals(
            ToolsError.INVALID_INPUT,
            errorCode {
                gasPlan(
                    GasPlanToolInput(
                        o2 = 60.0,
                        he = 50.0,
                        levels = listOf(GasPlanToolLevel(20.0, 20.0)),
                        divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0)),
                    ),
                )
            },
        )
        assertEquals(ToolsError.INVALID_INPUT, errorCode { plan(rule = RULE_RESERVE) })
        assertEquals(ToolsError.INVALID_INPUT, errorCode { plan(rule = "unknown") })
        assertEquals(ToolsError.INVALID_INPUT, errorCode { plan(divers = emptyList()) })
    }
}
