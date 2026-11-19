package dev.divehub.android.ui.tools

import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import dev.divehub.shared.tools.PersonalizedSources
import dev.divehub.shared.tools.ToolsError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.time.Instant

/** The lead screen's typed conditions into the estimate (FAH-276): blanks, units and the suit's rules by code. */
class LeadInputTest {
    private val today = Instant.parse("2026-11-19T12:00:00.000Z")

    private fun inputs(
        suit: SuitType = SuitType.none,
        thickness: String = "",
        undergarment: SuitUndergarment? = null,
        volume: String = "12",
        material: CylinderMaterial? = CylinderMaterial.steel,
    ) = LeadInputs(
        suit = suit,
        thickness = thickness,
        undergarment = undergarment,
        cylinders = listOf(LeadCylinderInput(volume, material)),
    )

    private fun answer(
        inputs: LeadInputs,
        unit: LeadUnit = LeadUnit.kg,
        sources: PersonalizedSources = PersonalizedSources(),
    ) = leadAnswer(inputs, unit, sources, today)

    @Test
    fun `a blank cylinder size is neither an answer nor an error`() {
        val result = answer(inputs(volume = ""))
        assertNull(result.answer)
        assertNull(result.error)
    }

    @Test
    fun `an unreadable or out-of-range volume is refused`() {
        assertEquals(ToolsError.INVALID_INPUT, answer(inputs(volume = "abc")).error)
        assertEquals(ToolsError.INVALID_INPUT, answer(inputs(volume = "0")).error)
        assertEquals(ToolsError.INVALID_INPUT, answer(inputs(volume = "41")).error)
    }

    @Test
    fun `a thickness goes with a wetsuit, not a drysuit`() {
        assertEquals(ToolsError.SUIT_INVALID, answer(inputs(suit = SuitType.drysuit, thickness = "5")).error)
    }

    @Test
    fun `an undergarment goes with a drysuit only`() {
        assertEquals(
            ToolsError.SUIT_INVALID,
            answer(inputs(suit = SuitType.wetsuit, undergarment = SuitUndergarment.medium)).error,
        )
    }

    @Test
    fun `a body weight alone takes the rule of thumb in the asked unit`() {
        val result = answer(
            inputs(suit = SuitType.drysuit, undergarment = SuitUndergarment.medium),
            unit = LeadUnit.lb,
            sources = PersonalizedSources(ownDiverId = "diver", bodyWeightKg = 80.0),
        )
        val answer = result.answer
        assertNotNull(answer)
        assertEquals("lb", answer!!.unit)
        assertNotNull(answer.total)
        assertEquals("rule_of_thumb", answer.basis?.kind)
        assertEquals("low", answer.confidence)
    }
}
