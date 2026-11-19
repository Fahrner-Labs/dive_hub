package dev.divehub.android.ui.tools

import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.tools.RULE_RESERVE
import dev.divehub.shared.tools.ToolDiverSource
import dev.divehub.shared.tools.ToolSources
import dev.divehub.shared.tools.ToolsError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** The screen's typed text into a plan (FAH-275): blanks, units, partial rows and the SAC a synced Diver brings. */
class GasPlanInputTest {
    private fun inputs(
        o2: String = "",
        depth: String = "18",
        minutes: String = "20",
        volume: String = "12",
        pressure: String = "200",
        sac: String = "",
        diverId: String? = null,
        name: String = "Elena",
        rule: String? = null,
        bar: String = "",
    ) = GasPlanInputs(
        o2 = o2,
        levels = listOf(GasPlanLevelInput(depth, minutes)),
        divers = listOf(GasPlanDiverInput(diverId = diverId, name = name, volume = volume, pressure = pressure, sac = sac)),
        rule = rule,
        bar = bar,
    )

    @Test
    fun `an incomplete plan is neither an answer nor an error`() {
        val answer = gasPlanAnswer(GasPlanInputs(), imperial = false, sources = ToolSources())
        assertNull(answer.answer)
        assertNull(answer.error)
    }

    @Test
    fun `a blank mix plans with air`() {
        val answer = gasPlanAnswer(inputs(), imperial = false, sources = ToolSources()).answer
        assertNotNull(answer)
        assertEquals("ok", answer!!.status)
        assertEquals(21.0, answer.gas.o2, 1e-9)
    }

    @Test
    fun `imperial depth and pressure are converted before the kernel`() {
        val answer = gasPlanAnswer(
            inputs(o2 = "21", depth = "60", minutes = "20", pressure = "2901"),
            imperial = true,
            sources = ToolSources(),
        ).answer
        assertNotNull(answer)
        val descent = answer!!.segments?.firstOrNull()
        assertNotNull(descent)
        assertTrue(kotlin.math.abs(descent!!.depthM - 18.288) < 0.01)
        val start = answer.divers?.single()?.startPressureBar ?: 0.0
        assertTrue(kotlin.math.abs(start - 200.0) < 0.1)
    }

    @Test
    fun `a partial level or an out-of-range value is refused`() {
        assertEquals(ToolsError.INVALID_INPUT, gasPlanAnswer(inputs(minutes = ""), false, ToolSources()).error)
        assertEquals(ToolsError.INVALID_INPUT, gasPlanAnswer(inputs(depth = "0"), false, ToolSources()).error)
        assertEquals(
            ToolsError.INVALID_INPUT,
            gasPlanAnswer(inputs(rule = RULE_RESERVE), false, ToolSources()).error,
        )
    }

    @Test
    fun `a synced Diver's planning SAC fills an empty SAC field`() {
        val sources = ToolSources(
            divers = listOf(
                ToolDiverSource(
                    id = "diver",
                    name = "Elena",
                    isOwn = true,
                    planningSac = PlanningSacResult(17.5, 12, PlanningSacSource.logbook),
                ),
            ),
        )
        val answer = gasPlanAnswer(inputs(diverId = "diver"), imperial = false, sources = sources).answer
        assertNotNull(answer)
        val sac = answer!!.divers?.single()?.sacLpm
        assertNotNull(sac)
        assertEquals("logbook", sac!!.source)
        assertEquals(17.5, sac.value, 1e-9)
        assertEquals(12, sac.basedOn)
    }
}
