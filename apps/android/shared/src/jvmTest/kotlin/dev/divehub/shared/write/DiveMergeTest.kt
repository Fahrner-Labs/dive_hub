package dev.divehub.shared.write

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DiveMergeTest {
    private fun merge(
        base: JsonObject = diveObject(),
        server: JsonObject = diveObject(),
        patch: JsonObject,
    ) = DiveMerge.mergeDive(base, server, patch)

    @Test
    fun `a field only the local side changed is re-sent`() {
        val result = merge(patch = jsonOf("""{"notes":"Local"}"""))

        assertEquals("Local", result.patch["notes"]?.let { (it as JsonPrimitive).content })
        assertFalse(result.hasConflicts)
    }

    @Test
    fun `a field only the server side changed is taken quietly`() {
        val result = merge(server = diveObject(notes = "Server"), patch = jsonOf("""{"set":{"durationSeconds":3600}}"""))

        assertFalse(result.hasConflicts)
        assertEquals(3600.0, (result.patch["set"] as JsonObject)["durationSeconds"]?.let { (it as JsonPrimitive).content.toDouble() })
        assertFalse(result.patch.containsKey("notes"))
    }

    @Test
    fun `the same value on both sides is a no-op`() {
        val result = merge(server = diveObject(notes = "Same"), patch = jsonOf("""{"notes":"Same"}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

    @Test
    fun `a field changed on both sides differently conflicts`() {
        val result = merge(server = diveObject(notes = "Server"), patch = jsonOf("""{"notes":"Local"}"""))

        val conflict = result.conflicts.single()
        assertEquals("notes", conflict.field)
        assertEquals("A good dive", (conflict.base as JsonPrimitive).content)
        assertEquals("Server", (conflict.server as JsonPrimitive).content)
        assertEquals("Local", (conflict.local as JsonPrimitive).content)
        assertFalse(result.patch.containsKey("notes"))
    }

    @Test
    fun `a number written differently is still equal`() {
        val base = diveObject(durationSeconds = 3000.0)
        val server = diveObject(durationSeconds = 3000.0).withValue("durationSeconds", JsonPrimitive(3000))
        val result = merge(base = base, server = server, patch = jsonOf("""{"notes":"Local"}"""))

        assertFalse(result.hasConflicts)
    }

    @Test
    fun `a set override conflicts when both sides changed that value`() {
        val base = diveObject(durationSeconds = 3000.0)
        val server = diveObject(durationSeconds = 3300.0, overrides = listOf("durationSeconds"))
        val result = merge(base = base, server = server, patch = jsonOf("""{"set":{"durationSeconds":3600}}"""))

        val conflict = result.conflicts.single()
        assertEquals("set.durationSeconds", conflict.field)
        assertEquals("set", conflict.localAction)
    }

    @Test
    fun `a reset merges quietly when the server left the override alone`() {
        val base = diveObject(maxDepthM = 30.0, overrides = listOf("maxDepthM"))
        val result = merge(base = base, server = base, patch = jsonOf("""{"reset":["maxDepthM"]}"""))

        assertFalse(result.hasConflicts)
        assertEquals(listOf("maxDepthM"), (result.patch["reset"] as JsonArray).map { (it as JsonPrimitive).content })
    }

    @Test
    fun `a reset is a no-op when the server also dropped the override`() {
        val base = diveObject(maxDepthM = 30.0, overrides = listOf("maxDepthM"))
        val server = diveObject(maxDepthM = 28.0, overrides = emptyList())
        val result = merge(base = base, server = server, patch = jsonOf("""{"reset":["maxDepthM"]}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

    @Test
    fun `a reset conflicts when the server changed the overridden value`() {
        val base = diveObject(maxDepthM = 30.0, overrides = listOf("maxDepthM"))
        val server = diveObject(maxDepthM = 32.0, overrides = listOf("maxDepthM"))
        val result = merge(base = base, server = server, patch = jsonOf("""{"reset":["maxDepthM"]}"""))

        val conflict = result.conflicts.single()
        assertEquals("reset.maxDepthM", conflict.field)
        assertEquals("reset", conflict.localAction)
    }

    @Test
    fun `suit is one field and conflicts as a whole`() {
        val base = diveObject(suit = jsonOf("""{"type":"wetsuit","thicknessMm":5}"""))
        val server = diveObject(suit = jsonOf("""{"type":"drysuit","thicknessMm":4}"""))
        val patch = jsonOf("""{"suit":{"type":"shorty","thicknessMm":3}}""")

        val result = merge(base = base, server = server, patch = patch)

        assertEquals("suit", result.conflicts.single().field)
    }

    @Test
    fun `cylinders and lead are whole-list fields`() {
        val base = diveObject()
        val server = diveObject(lead = JsonArray(listOf(jsonOf("""{"amountKg":6,"placement":"belt"}"""))))
        val patch = jsonOf("""{"cylinders":[{"volumeL":12,"workingPressureBar":232,"material":"steel","gas":{"o2":21,"he":0},"fromPod":false,"series":null}]}""")

        val result = merge(base = base, server = server, patch = patch)

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.containsKey("cylinders"))
        assertFalse(result.patch.containsKey("lead"))
    }

    @Test
    fun `the same list changed on both sides conflicts`() {
        val base = diveObject()
        val server = diveObject(lead = JsonArray(listOf(jsonOf("""{"amountKg":6,"placement":"belt"}"""))))
        val patch = jsonOf("""{"lead":[{"amountKg":7,"placement":"belt"}]}""")

        val result = merge(base = base, server = server, patch = patch)

        assertEquals("lead", result.conflicts.single().field)
    }

    @Test
    fun `participants merge element-wise and keep both additions`() {
        val base = diveObject(participants = participants("a" to "buddy"))
        val server = diveObject(participants = participants("a" to "buddy", "b" to "guide"))
        val local = participants("a" to "buddy", "c" to "instructor")

        val result = DiveMerge.mergeParticipants(base, server, local)

        assertFalse(result.hasConflicts)
        assertEquals(
            listOf("a" to "buddy", "c" to "instructor", "b" to "guide"),
            result.participants.map {
                val entry = it as JsonObject
                (entry["diverId"] as JsonPrimitive).content to (entry["role"] as JsonPrimitive).content
            },
        )
    }

    @Test
    fun `a participant removed on one side and untouched on the other is removed`() {
        val base = diveObject(participants = participants("a" to "buddy", "b" to "guide"))
        val server = diveObject(participants = participants("a" to "buddy", "b" to "guide"))
        val local = participants("a" to "buddy")

        val result = DiveMerge.mergeParticipants(base, server, local)

        assertFalse(result.hasConflicts)
        assertEquals(listOf("a"), result.participants.map { ((it as JsonObject)["diverId"] as JsonPrimitive).content })
    }

    @Test
    fun `a removed participant whose role the server changed conflicts`() {
        val base = diveObject(participants = participants("a" to "buddy", "b" to "buddy"))
        val server = diveObject(participants = participants("a" to "buddy", "b" to "guide"))
        val local = participants("a" to "buddy")

        val result = DiveMerge.mergeParticipants(base, server, local)

        assertEquals("participants.b", result.conflicts.single().field)
        assertEquals("remove", result.conflicts.single().localAction)
    }

    @Test
    fun `a role changed differently on both sides conflicts`() {
        val base = diveObject(participants = participants("a" to "buddy"))
        val server = diveObject(participants = participants("a" to "instructor"))
        val local = participants("a" to "guide")

        val result = DiveMerge.mergeParticipants(base, server, local)

        assertEquals("participants.a", result.conflicts.single().field)
    }

    @Test
    fun `a participant added on both sides with different roles conflicts`() {
        val base = diveObject()
        val server = diveObject(participants = participants("a" to "guide"))
        val local = participants("a" to "buddy")

        val result = DiveMerge.mergeParticipants(base, server, local)

        assertEquals("participants.a", result.conflicts.single().field)
    }

    @Test
    fun `a set both sides reached the same value is a no-op even without a server override`() {
        val base = diveObject(durationSeconds = 3000.0)
        val server = diveObject(durationSeconds = 3600.0)
        val result = merge(base = base, server = server, patch = jsonOf("""{"set":{"durationSeconds":3600}}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

    @Test
    fun `a field the patch repeats unchanged takes the server value instead of conflicting`() {
        val result = merge(server = diveObject(notes = "Server"), patch = jsonOf("""{"notes":"A good dive"}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

    @Test
    fun `a reset of a field without an override changes nothing`() {
        val base = diveObject(maxDepthM = 30.0, overrides = emptyList())
        val server = diveObject(maxDepthM = 32.0, overrides = listOf("maxDepthM"))
        val result = merge(base = base, server = server, patch = jsonOf("""{"reset":["maxDepthM"]}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

    @Test
    fun `a set repeated unchanged takes the server value instead of conflicting`() {
        val base = diveObject(durationSeconds = 3000.0)
        val server = diveObject(durationSeconds = 3300.0, overrides = listOf("durationSeconds"))
        val result = merge(base = base, server = server, patch = jsonOf("""{"set":{"durationSeconds":3000}}"""))

        assertFalse(result.hasConflicts)
        assertTrue(result.patch.isEmpty())
    }

}

private fun JsonObject.withValue(field: String, value: JsonPrimitive): JsonObject {
    val values = this["values"] as JsonObject
    val changed = buildJsonObject {
        values.forEach { (key, element) -> if (key != field) put(key, element) }
        put(field, value)
    }
    val result = toMutableMap()
    result["values"] = changed
    return JsonObject(result)
}
