package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import dev.divehub.shared.write.JSON
import dev.divehub.shared.write.diveObject
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DiveEditDraftTest {
    private fun dive(detail: JsonObject = diveObject()): DiveView =
        JSON.decodeFromString(DiveView.serializer(), detail.toString())

    /** The same Dive with a Primary Recording, so a value has something to be given back to. */
    private fun recordedDive(overrides: List<String> = emptyList()): DiveView {
        val detail = diveObject(overrides = overrides).toMutableMap().apply {
            put(
                "fromRecording",
                buildJsonObject {
                    put(
                        "startsAt",
                        buildJsonObject {
                            put("at", "2026-11-01T09:00:00Z")
                            put("utcOffsetSeconds", 3600)
                        },
                    )
                    put("durationSeconds", 2400.0)
                    put("maxDepthM", 20.0)
                    put("avgDepthM", 10.0)
                    put("waterTemperatureC", 17.0)
                },
            )
        }
        return dive(JsonObject(detail))
    }

    private fun JsonObject.obj(key: String): JsonObject = this[key] as JsonObject

    @Test
    fun `a fresh draft is clean and sends no patch`() {
        val draft = DiveEditDraft.of(dive())

        assertFalse(draft.dirty)
        assertNull(draft.patch())
    }

    @Test
    fun `the draft reads the Dive's values, site and start`() {
        val draft = DiveEditDraft.of(dive())

        assertEquals("dive-1", draft.diveId)
        assertEquals("A good dive", draft.notes)
        assertEquals("site-1", draft.siteId)
        assertEquals(50, draft.durationMinutes)
        assertEquals(24.3, draft.maxDepthM)
        assertEquals(LocalDateTime(2026, 11, 1, 11, 0), draft.start.local)
        assertEquals(3600, draft.start.utcOffsetSeconds)
    }

    @Test
    fun `notes are sent as typed and an empty note clears them`() {
        val typed = DiveEditDraft.of(dive()).setNotes("  Better notes  ")
        assertEquals(JsonPrimitive("  Better notes  "), typed.patch()!!["notes"])

        val cleared = DiveEditDraft.of(dive()).setNotes("")
        assertEquals(JsonNull, cleared.patch()!!["notes"])
    }

    @Test
    fun `a site choice and its clearing go as siteId`() {
        val chosen = DiveEditDraft.of(dive()).setSite("site-2")
        assertEquals(JsonPrimitive("site-2"), chosen.patch()!!["siteId"])

        val cleared = DiveEditDraft.of(dive()).setSite(null)
        assertEquals(JsonNull, cleared.patch()!!["siteId"])
    }

    @Test
    fun `a set value becomes an Override with the field's wire name`() {
        val draft = DiveEditDraft.of(dive()).setMaxDepthM(30.0)
        val patch = draft.patch()!!

        assertEquals(JsonPrimitive(30.0), patch.obj("set")["maxDepthM"])
        assertNull(patch["reset"])
        assertTrue(draft.isEdited(DiveValue.MAX_DEPTH_M))
        assertTrue(draft.wasSetHere(DiveValue.MAX_DEPTH_M))
    }

    @Test
    fun `a cleared depth is an explicit null Override, not a reset`() {
        val patch = DiveEditDraft.of(dive()).setMaxDepthM(null).patch()!!

        assertEquals(JsonNull, patch.obj("set")["maxDepthM"])
        assertNull(patch["reset"])
    }

    @Test
    fun `the start is sent as the wall clock and its offset, as one Override`() {
        val draft = DiveEditDraft.of(dive())
            .setStart(DiveEditStart(LocalDateTime(2026, 11, 1, 12, 30), 7200))
        val startsAt = draft.patch()!!.obj("set").obj("startsAt")

        assertEquals("2026-11-01T10:30:00Z", (startsAt["at"] as JsonPrimitive).content)
        assertEquals(JsonPrimitive(7200), startsAt["utcOffsetSeconds"])
        assertTrue(draft.isEdited(DiveValue.STARTS_AT))
    }

    @Test
    fun `duration is edited in whole minutes and sent in seconds`() {
        val patch = DiveEditDraft.of(dive()).setDurationMinutes(42).patch()!!

        assertEquals(JsonPrimitive(2520), patch.obj("set")["durationSeconds"])
    }

    @Test
    fun `resetting an Override asks the server to drop it and keeps the value shown`() {
        val draft = DiveEditDraft.of(recordedDive(overrides = listOf("maxDepthM")))
            .setMaxDepthM(30.0)
            .resetToRecording(DiveValue.MAX_DEPTH_M)
        val patch = draft.patch()!!

        assertEquals(JsonArray(listOf(JsonPrimitive("maxDepthM"))), patch["reset"])
        assertNull(patch["set"])
        assertEquals(20.0, draft.maxDepthM)
        assertFalse(draft.isEdited(DiveValue.MAX_DEPTH_M))
        assertTrue(draft.dirty)
    }

    @Test
    fun `resetting a value the Dive never overrode sends nothing`() {
        val draft = DiveEditDraft.of(recordedDive())
            .setMaxDepthM(30.0)
            .resetToRecording(DiveValue.MAX_DEPTH_M)

        assertNull(draft.patch())
        assertFalse(draft.dirty)
    }

    @Test
    fun `resetting uses the Recording's value`() {
        val draft = DiveEditDraft.of(recordedDive())
            .setMaxDepthM(30.0)
            .resetToRecording(DiveValue.MAX_DEPTH_M)

        assertEquals(20.0, draft.maxDepthM)
        assertTrue(draft.hasRecording(DiveValue.MAX_DEPTH_M))
    }

    @Test
    fun `the suit goes as one whole field only when the User changed it`() {
        val draft = DiveEditDraft.of(dive())
        assertNull(draft.patch())

        val suit = DiveEditSuit(type = "wetsuit", thicknessMm = 5.0, hood = true)
        val patch = draft.setSuit(suit).patch()!!
        val sent = patch.obj("suit")

        assertEquals(JsonPrimitive("wetsuit"), sent["type"])
        assertEquals(JsonPrimitive(5.0), sent["thicknessMm"])
        assertEquals(JsonPrimitive(true), sent["hood"])
        assertNull(sent["undergarment"])
    }

    @Test
    fun `a suit with no thickness or hood sends only what its type has`() {
        val patch = DiveEditDraft.of(dive())
            .setSuit(DiveEditSuit(type = "none"))
            .patch()!!

        val sent = patch.obj("suit")
        assertEquals(JsonPrimitive("none"), sent["type"])
        assertNull(sent["thicknessMm"])
        assertNull(sent["undergarment"])
    }

    @Test
    fun `clearing the suit and the feedback sends explicit nulls`() {
        val withWeighting = diveObject(
            suit = buildJsonObject { put("type", "drysuit") },
            weightingFeedback = buildJsonObject { put("felt", "right") },
        )
        val patch = DiveEditDraft.of(dive(withWeighting))
            .setSuit(null)
            .setWeighting(null)
            .patch()!!

        assertEquals(JsonNull, patch["suit"])
        assertEquals(JsonNull, patch["weightingFeedback"])
    }

    @Test
    fun `a weighting that felt right carries no amount`() {
        val patch = DiveEditDraft.of(dive())
            .setWeighting(DiveEditWeighting(felt = "too_heavy", amountKg = 1.5))
            .patch()!!
        assertEquals(JsonPrimitive(1.5), patch.obj("weightingFeedback")["amountKg"])

        val right = DiveEditDraft.of(dive())
            .setWeighting(DiveEditWeighting(felt = "right", amountKg = 1.5))
            .patch()!!
        assertNull(right.obj("weightingFeedback")["amountKg"])
    }

    @Test
    fun `a value typed this session carries the edited mark before saving`() {
        val draft = DiveEditDraft.of(dive()).setMaxDepthM(30.0)
        assertTrue(draft.isEdited(DiveValue.MAX_DEPTH_M))
        assertTrue(draft.wasSetHere(DiveValue.MAX_DEPTH_M))
    }

    @Test
    fun `the number is never part of the patch`() {
        val patch = DiveEditDraft.of(dive()).setNotes("x").patch()!!
        assertFalse(patch.containsKey("number"))
        assertFalse(patch.containsKey("values"))
    }

    private fun gearDive() = dive(
        diveObject(
            cylinders = JsonArray(
                listOf(
                    buildJsonObject {
                        put("volumeL", 12.0)
                        put("workingPressureBar", 232.0)
                        put("material", "steel")
                        put("gas", buildJsonObject { put("o2", 21.0); put("he", 0.0) })
                        put("startPressureBar", 220.0)
                        put("endPressureBar", 70.0)
                        put("equipmentItemId", "item-1")
                        put(
                            "series",
                            buildJsonObject {
                                put("recordingId", "recording-1")
                                put("channel", "tankPressure")
                            },
                        )
                        put("fromPod", true)
                    },
                    buildJsonObject {
                        put("volumeL", 11.0)
                        put("workingPressureBar", 200.0)
                        put("material", "aluminium")
                        put("gas", buildJsonObject { put("o2", 32.0); put("he", 0.0) })
                        put("startPressureBar", 200.0)
                        put("endPressureBar", 90.0)
                        put("fromPod", false)
                    },
                ),
            ),
            lead = JsonArray(
                listOf(
                    buildJsonObject { put("amountKg", 4.0); put("placement", "belt") },
                    buildJsonObject { put("amountKg", 1.0); put("placement", "trim") },
                ),
            ),
        ),
    )

    @Test
    fun `a fresh draft starts with one empty lead row and sends no gear`() {
        val draft = DiveEditDraft.of(dive())

        assertEquals(1, draft.lead.size)
        assertNull(draft.lead.single().amountKg)
        assertFalse(draft.cylindersChanged)
        assertFalse(draft.leadChanged)
        assertFalse(draft.dirty)
        assertNull(draft.patch())
    }

    @Test
    fun `the gear is read as the Dive has it`() {
        val draft = DiveEditDraft.of(gearDive())

        assertEquals(2, draft.cylinders.size)
        assertEquals(12.0, draft.cylinders[0].volumeL)
        assertEquals("steel", draft.cylinders[0].material)
        assertEquals(21.0, draft.cylinders[0].o2)
        assertEquals(DiveEditSeries("recording-1", "tankPressure"), draft.cylinders[0].series)
        assertTrue(draft.cylinders[0].fromPod)
        assertEquals(listOf(4.0, 1.0), draft.lead.map { it.amountKg })
        assertFalse(draft.dirty)
    }

    @Test
    fun `editing one cylinder sends the whole list with each tie and item`() {
        val draft = DiveEditDraft.of(gearDive())
        val edited = draft.cylinders[0].copy(endPressureBar = 60.0)
        val patch = draft.setCylinders(listOf(edited, draft.cylinders[1])).patch()!!

        val cylinders = patch["cylinders"] as JsonArray
        assertEquals(2, cylinders.size)
        val sent = cylinders[0] as JsonObject
        assertEquals(JsonPrimitive(60.0), sent["endPressureBar"])
        assertEquals(JsonPrimitive("item-1"), sent["equipmentItemId"])
        assertEquals(
            JsonPrimitive("recording-1"),
            (sent["series"] as JsonObject)["recordingId"],
        )
        // A server-only mark never goes back.
        assertFalse(sent.containsKey("fromPod"))
        val untouched = cylinders[1] as JsonObject
        assertEquals(JsonPrimitive(90.0), untouched["endPressureBar"])
    }

    @Test
    fun `a blank added cylinder is not saved until it has a value`() {
        val draft = DiveEditDraft.of(gearDive())
        val added = draft.setCylinders(draft.cylinders + DiveEditCylinder())
        assertNull(added.patch())
        assertFalse(added.dirty)

        val filled = added.setCylinders(added.cylinders.dropLast(1) + DiveEditCylinder(volumeL = 15.0))
        val sent = (filled.patch()!!["cylinders"] as JsonArray)
        assertEquals(3, sent.size)
        assertEquals(JsonPrimitive(15.0), (sent[2] as JsonObject)["volumeL"])
    }

    @Test
    fun `removing every cylinder sends an empty list`() {
        val patch = DiveEditDraft.of(gearDive()).setCylinders(emptyList()).patch()!!

        assertEquals(0, (patch["cylinders"] as JsonArray).size)
    }

    @Test
    fun `a cylinder with an end pressure above the start does not fit`() {
        val draft = DiveEditDraft.of(gearDive())
        val changed = draft.setCylinders(
            listOf(draft.cylinders[0].copy(startPressureBar = 100.0, endPressureBar = 120.0), draft.cylinders[1]),
        )

        assertFalse(changed.cylindersFit)
        assertEquals(CylinderProblem.END_ABOVE_START, changed.cylinders[0].problem())
        assertEquals(CylinderProblem.END_ABOVE_START, changed.cylinders[0].pressureProblem)
        assertNull(changed.cylinders[0].gasProblem)
    }

    @Test
    fun `a cylinder gas over one hundred percent does not fit`() {
        val draft = DiveEditDraft.of(gearDive())
        val changed = draft.setCylinders(
            listOf(draft.cylinders[0].copy(o2 = 60.0, he = 50.0), draft.cylinders[1]),
        )

        assertFalse(changed.cylindersFit)
        assertEquals(CylinderProblem.GAS_OVER_100, changed.cylinders[0].problem())
        assertEquals(CylinderProblem.GAS_OVER_100, changed.cylinders[0].gasProblem)
        assertNull(changed.cylinders[0].pressureProblem)
    }

    @Test
    fun `the lead is sent as the whole list and an amountless row is left out`() {
        val draft = DiveEditDraft.of(gearDive())
        val edited = draft.setLead(
            listOf(draft.lead[0].copy(amountKg = 5.0), DiveEditLead(), draft.lead[1]),
        )

        val sent = edited.patch()!!["lead"] as JsonArray
        assertEquals(2, sent.size)
        val first = sent[0] as JsonObject
        assertEquals(JsonPrimitive(5.0), first["amountKg"])
        assertEquals(JsonPrimitive("belt"), first["placement"])
        assertFalse((sent[1] as JsonObject).containsKey("key"))
    }

    @Test
    fun `removing the lead sends an empty list, not an empty entry`() {
        val draft = DiveEditDraft.of(gearDive())
        val patch = draft.setLead(listOf(DiveEditLead())).patch()!!

        assertEquals(0, (patch["lead"] as JsonArray).size)
        assertTrue(draft.setLead(listOf(DiveEditLead())).leadChanged)
    }
}
