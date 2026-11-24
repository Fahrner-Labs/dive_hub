package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import dev.divehub.shared.write.JSON
import dev.divehub.shared.write.diveObject
import dev.divehub.shared.write.participants
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

    @Test
    fun `a fresh draft keeps the Dive's participants and sends no roster`() {
        val draft = DiveEditDraft.of(dive(diveObject(participants = participants("a" to "buddy"))))

        assertEquals(listOf("a"), draft.participants.map { it.diverId })
        assertEquals(listOf("a"), draft.participants.map { it.name })
        assertFalse(draft.participantsChanged)
        assertFalse(draft.dirty)
        assertNull(draft.participantsJson())
    }

    @Test
    fun `adding, changing and removing people is the roster the PUT takes`() {
        val draft = DiveEditDraft.of(dive(diveObject(participants = participants("a" to "buddy"))))
            .addParticipant("b", "Bea", "guide")
            .setParticipantRole("a", "instructor")
            .removeParticipant("b")

        assertEquals(listOf("a" to "instructor"), draft.participants.map { it.diverId to it.role })
        assertTrue(draft.participantsChanged)
        assertTrue(draft.dirty)
        val roster = draft.participantsJson()!!
        assertEquals(
            listOf("a" to "instructor"),
            roster.map {
                val entry = it as JsonObject
                (entry["diverId"] as JsonPrimitive).content to (entry["role"] as JsonPrimitive).content
            },
        )
        assertFalse(roster.toString().contains("name"))
    }

    @Test
    fun `a roster returned to its first state is clean again`() {
        val draft = DiveEditDraft.of(dive(diveObject(participants = participants("a" to "buddy"))))
            .addParticipant("b", "Bea", "guide")
            .removeParticipant("b")

        assertFalse(draft.participantsChanged)
        assertFalse(draft.dirty)
        assertNull(draft.participantsJson())
    }

    @Test
    fun `a Diver already on the roster is not added twice`() {
        val draft = DiveEditDraft.of(dive(diveObject(participants = participants("a" to "buddy"))))
            .addParticipant("a", "Ada", "guide")

        assertEquals(listOf("a" to "buddy"), draft.participants.map { it.diverId to it.role })
        assertFalse(draft.participantsChanged)
        assertEquals("a", draft.participants.single().name)
    }

    @Test
    fun `a participant the feed added since opening is carried along`() {
        val draft = DiveEditDraft.of(dive(diveObject(participants = participants("a" to "buddy"))))
            .setParticipantRole("a", "guide")

        val roster = draft.participantsJson(
            alsoKeep = listOf(
                DiveEditParticipant("a", "Ada", "buddy"),
                DiveEditParticipant("b", "Bea", "instructor"),
            ),
        )!!

        assertEquals(
            listOf("a" to "guide", "b" to "instructor"),
            roster.map {
                val entry = it as JsonObject
                (entry["diverId"] as JsonPrimitive).content to (entry["role"] as JsonPrimitive).content
            },
        )
    }
}
