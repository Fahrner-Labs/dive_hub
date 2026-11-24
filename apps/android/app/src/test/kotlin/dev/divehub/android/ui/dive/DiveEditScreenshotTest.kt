package dev.divehub.android.ui.dive

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.Density
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewCylindersItemGas
import dev.divehub.api.models.DiveViewCylindersItemSeries
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewParticipantsItem
import dev.divehub.api.models.DiveViewLeadItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemDevice
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewSite
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.pressureSeriesOptions
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.Diver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The edit screen's contract-visible states as pictures: a synced Dive being edited, the site picker in its list, no-match
 * and dark states, an edited value with its "use the recording's value" way back, the destructive verbs' not-available
 * note, dark at the top and at the weighting and more actions, and German at 200 % on a narrow phone at the top, over
 * the value fields, at the Notes field and at the true foot. Rendered from a fixed state, so a drawing change shows up
 * as a changed image.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class DiveEditScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a synced dive opens for editing`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithText("Edit dive").assertExists()
        compose.onNodeWithText("Lighthouse Reef").assertExists()
        capture("dive_edit_light.png")
    }

    @Test
    fun `an edited value says what the recording had and offers its value back`() {
        render(state(draft = DiveEditDraft.of(dive(overrides = listOf("maxDepthM")))))

        compose.onNodeWithText("edited · recording: 20.0 m").assertExists()
        compose.onNodeWithText("Use the recording’s value").assertExists()
        capture("dive_edit_edited.png")
    }

    @Test
    fun `the site picker lists the cached sites and the current one`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithText("Choose a dive site").performClick()

        compose.onNodeWithText("Blue Hole").assertExists()
        compose.onNode(hasText("Lighthouse Reef") and hasClickAction()).assertIsSelected()
        capture("dive_edit_site_picker.png")
    }

    @Test
    fun `the site picker's no-match state hides the no-site row`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithText("Choose a dive site").performClick()
        compose.onNode(hasSetTextAction() and hasText("Find a dive site")).performTextInput("xyz")

        compose.onNodeWithText("No dive sites match").assertExists()
        compose.onNodeWithText("No dive site").assertDoesNotExist()
        capture("dive_edit_site_picker_no_match.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the site picker follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive())), darkTheme = true)

        compose.onNodeWithText("Choose a dive site").performClick()
        compose.onNode(hasText("Lighthouse Reef") and hasClickAction()).assertIsSelected()
        capture("dive_edit_site_picker_dark.png")
    }

    @Test
    fun `the destructive verbs say they are not available on this device`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("More actions"))
        compose.onNodeWithText("Deleting, merging, moving and appending aren’t available on this device yet.")
            .assertExists()
        capture("dive_edit_more_actions.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the editor follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive())), darkTheme = true)

        capture("dive_edit_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the weighting and the more actions follow the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive(overrides = listOf("maxDepthM")))), darkTheme = true)

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("More actions"))
        capture("dive_edit_dark_actions.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the editor at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithText("Tauchgang bearbeiten").assertExists()
        capture("dive_edit_de_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the value fields at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithTag("dive_edit_scroll")
            .performScrollToNode(hasText("Durchschnittliche Tiefe (m)"))
        capture("dive_edit_de_narrow_2x_values.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the notes field at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithTag("dive_edit_scroll")
            .performScrollToNode(hasText("Was ist bei diesem Tauchgang passiert?"))
        capture("dive_edit_de_narrow_2x_notes.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the foot at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithTag("dive_edit_scroll")
            .performScrollToNode(hasText("Als Fortsetzung anhängen"))
        capture("dive_edit_de_narrow_2x_foot.png")
    }

    @Test
    fun `the cylinders are editable as an ordered list`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Cylinders"))
        compose.onNodeWithText("Cylinder 1").assertExists()
        compose.onNodeWithText("Cylinder 2").assertExists()
        compose.onNodeWithText("from a tank pod").assertExists()
        capture("dive_edit_cylinders.png")
    }

    @Test
    fun `the lead is editable with its placements and total`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Total 5.0 kg"))
        compose.onNodeWithText("Total 5.0 kg").assertExists()
        capture("dive_edit_lead.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the cylinders follow the dark theme`() {
        val dive = dive(withGear = true)
        render(
            state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)),
            darkTheme = true,
        )

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Cylinders"))
        capture("dive_edit_cylinders_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the cylinders at two hundred percent in german`() {
        val dive = dive(withGear = true)
        render(
            state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)),
            fontScale = 2f,
        )

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Flaschen"))
        capture("dive_edit_de_narrow_2x_cylinders.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the lead at two hundred percent in german`() {
        val dive = dive(withGear = true)
        render(
            state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)),
            fontScale = 2f,
        )

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Blei 1 entfernen"))
        capture("dive_edit_de_narrow_2x_lead.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the cylinders at three hundred and twenty dp`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Cylinders"))
        capture("dive_edit_cylinders_320.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w360dp-h891dp-xxhdpi")
    fun `the cylinders at three hundred and sixty dp`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Cylinders"))
        capture("dive_edit_cylinders_360.png")
    }

    @Test
    fun `an invalid cylinder is explained under its pressure fields and holds the save`() {
        val dive = dive(withGear = true)
        val opened = DiveEditDraft.of(dive)
        val draft = opened.setCylinders(
            opened.cylinders.mapIndexed { index, cylinder ->
                if (index == 0) cylinder.copy(startPressureBar = 70.0, endPressureBar = 220.0) else cylinder
            },
        )
        render(state(draft = draft, seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll")
            .performScrollToNode(hasText("The end pressure is above the start pressure."))
        compose.onNodeWithText("The end pressure is above the start pressure.").assertExists()
        compose.onNodeWithText("Fix the cylinder errors before saving.").assertExists()
        compose.onNodeWithText("Save").assertIsNotEnabled()
        capture("dive_edit_cylinders_invalid.png")
    }

    @Test
    fun `an empty cylinders section offers the add action`() {
        val dive = dive(withGear = true)
        val draft = DiveEditDraft.of(dive).setCylinders(emptyList())
        render(state(draft = draft, seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("No cylinders yet."))
        compose.onNodeWithText("No cylinders yet.").assertExists()
        capture("dive_edit_cylinders_empty.png")
    }

    @Test
    fun `the second cylinder and add action are reachable`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Add a cylinder"))
        compose.onNodeWithText("Cylinder 2").assertExists()
        capture("dive_edit_cylinders_add.png")
    }

    @Test
    fun `a cylinder can be removed from its heading`() {
        val dive = dive(withGear = true)
        render(state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Remove cylinder 2"))
        compose.onNodeWithText("Remove cylinder 2").assertExists()
        capture("dive_edit_cylinders_remove.png")
    }

    @Test
    fun `the cylinders show imperial pressures`() {
        val dive = dive(withGear = true)
        render(
            state(draft = DiveEditDraft.of(dive), seriesOptions = pressureSeriesOptions(dive.recordings)),
            units = "imperial",
        )

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Cylinders"))
        capture("dive_edit_cylinders_imperial.png")
    }

    @Test
    fun `the participants section lists people with their roles and says what a joint dive does`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText(jointNote))
        compose.onNodeWithText("Anna").assertExists()
        compose.onNodeWithText("Ben").assertExists()
        compose.onNodeWithText(jointNote).assertExists()
        capture("dive_edit_participants.png")
    }

    @Test
    fun `the roster says when nobody else is listed`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Add a participant"))
        compose.onNodeWithText("No one else listed yet.").assertExists()
        capture("dive_edit_participants_empty.png")
    }

    @Test
    fun `adding someone searches the divers the store knows and says the names are shared`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))))

        openAddParticipant()

        compose.onNodeWithText(sharedHint).assertExists()
        compose.onNodeWithText("Cleo").assertExists()
        // A Diver the User keeps but does not own is still "You manage this diver", never another User's.
        compose.onAllNodesWithText("You manage this diver").assertCountEquals(2)
        compose.onNodeWithText("Another user’s diver").assertDoesNotExist()
        compose.onNode(hasText("Instructor") and hasClickAction())
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        capture("dive_edit_participant_add.png")
    }

    @Test
    fun `the add sheet with no cached divers still offers the live search`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people())), divers = emptyList()))

        openAddParticipant()

        compose.onNodeWithText(
            "Type a name to search every diver. To add someone new, use the web.",
        ).assertExists()
        compose.onNodeWithTag("dive_edit_participant_search").assertExists()
        capture("dive_edit_participant_add_no_cached.png")
    }

    @Test
    fun `offline with no cached divers says how to search and add`() {
        render(
            state(draft = DiveEditDraft.of(dive(participants = people())), online = false, divers = emptyList()),
        )

        openAddParticipant()

        compose.onNodeWithText(
            "No other divers on this device. Connect to search every diver; to add someone new, use the web.",
        ).assertExists()
        compose.onNodeWithTag("dive_edit_participant_search").assertDoesNotExist()
        capture("dive_edit_participant_add_offline_no_cached.png")
    }

    @Test
    fun `offline the add sheet lists only the cached divers and says so`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people())), online = false))

        openAddParticipant()

        compose.onNodeWithText(
            "Offline: only the divers on this device. Connect to search every diver.",
        ).assertExists()
        compose.onNodeWithText("Cleo").assertExists()
        capture("dive_edit_participant_add_offline.png")
    }

    @Test
    fun `live search results say whose logbook each diver is in`() {
        render(
            state(
                draft = DiveEditDraft.of(dive(participants = people())),
                participantSearch = DiverSearchState.Found(
                    "a",
                    listOf(
                        DiverChoice("diver-4", "Anna", managed = true, external = false),
                        DiverChoice("diver-5", "Mara", managed = false, external = false),
                        DiverChoice("diver-6", "Cara", managed = false, external = true),
                    ),
                ),
            ),
        )

        openAddParticipant()
        compose.onNodeWithTag("dive_edit_participant_search").performTextInput("a")

        compose.onNodeWithText("You manage this diver").assertExists()
        compose.onNodeWithText("Another user’s diver").assertExists()
        compose.onNodeWithText("External diver").assertExists()
        capture("dive_edit_participant_search.png")
    }

    @Test
    fun `changing a role opens a named choice`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))))

        openRoleSheet()

        compose.onNodeWithText("Role for Anna").assertExists()
        compose.onNodeWithText("Instructor").assertExists()
        compose.onNode(hasText("Instructor") and hasClickAction())
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        capture("dive_edit_participant_role.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the participants section follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))), darkTheme = true)

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText(jointNote))
        capture("dive_edit_participants_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the add sheet follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))), darkTheme = true)

        openAddParticipant()

        compose.onNodeWithText(sharedHint).assertExists()
        capture("dive_edit_participant_add_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the role choice follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))), darkTheme = true)

        openRoleSheet()

        compose.onNodeWithText("Role for Anna").assertExists()
        capture("dive_edit_participant_role_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the participants at two hundred percent in german`() {
        render(
            state(draft = DiveEditDraft.of(dive(participants = peopleWithInstructor()))),
            fontScale = 2f,
        )

        compose.onNodeWithTag("dive_edit_scroll")
            .performScrollToNode(hasContentDescription("Alexandra Winterhalter entfernen"))
        compose.onNodeWithText("Alexandra Winterhalter").assertExists()
        compose.onNodeWithText("Tauchlehrer").assertExists()
        capture("dive_edit_de_narrow_2x_participants.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `the add sheet at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))), fontScale = 2f)

        openAddParticipant(label = "Teilnehmende hinzufügen")

        compose.onNodeWithText("Taucher suchen").assertExists()
        compose.onNodeWithTag("dive_edit_participant_search").assertExists()
        compose.onNode(hasText("Tauchlehrer") and hasClickAction())
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        capture("dive_edit_de_narrow_2x_participant_add.png")
        // The sheet is one scroll, so the list stays reachable when the header fills a 200 % viewport.
        compose.onNodeWithTag("dive_edit_participant_add_scroll").performScrollToNode(hasText("Cleo"))
        compose.onNodeWithText("Cleo").assertExists()
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `the role choice at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive(participants = people()))), fontScale = 2f)

        openRoleSheet()

        compose.onNodeWithText("Rolle für Anna").assertExists()
        compose.onNodeWithText("Tauchlehrer").assertExists()
        capture("dive_edit_de_narrow_2x_participant_role.png")
    }

    private fun people() = listOf(
        DiveViewParticipantsItem(diverId = "anna", name = "Anna", role = "buddy"),
        DiveViewParticipantsItem(diverId = "ben", name = "Ben", role = "guide"),
    )

    private fun peopleWithInstructor() = people() + DiveViewParticipantsItem(
        diverId = "clara",
        name = "Alexandra Winterhalter",
        role = "instructor",
    )

    private fun openAddParticipant(label: String = "Add a participant") {
        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText(label))
        compose.onNode(hasText(label) and hasClickAction()).performClick()
    }

    private fun openRoleSheet(role: String = "Buddy") {
        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText(role))
        compose.onNode(hasText(role) and hasClickAction()).performClick()
    }

    private val jointNote =
        "In a joint dive, someone you add here also appears on the other divers’ dives. Removing someone here " +
            "leaves them on the others."

    private val sharedHint =
        "Every user of this Dive Hub sees the names of all divers and what their SSI code says: name, e-mail and " +
            "leader number. Nothing else about them."

    private fun diver(id: String, name: String) = Diver(
        id = id,
        name = name,
        isOwn = false,
        external = false,
        apneaCounts = false,
        divesBefore = 0,
    )

    private fun render(
        state: DiveEditUiState,
        darkTheme: Boolean = false,
        fontScale: Float? = null,
        units: String = "metric",
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    DiveEditScreen(
                        state = state,
                        units = units,
                        onBack = {},
                        onEdit = {},
                        onSave = {},
                        onRetry = {},
                        onSearchParticipants = {},
                        onResetParticipantSearch = {},
                    )
                }
            }
        }
    }

    private fun state(
        draft: DiveEditDraft,
        online: Boolean = true,
        divers: List<Diver> = listOf(diver("diver-2", "Bea"), diver("diver-3", "Cleo")),
        participantSearch: DiverSearchState = DiverSearchState.Idle,
        seriesOptions: List<dev.divehub.shared.dive.DiveEditSeriesOption> = emptyList(),
    ) = DiveEditUiState(
        loading = false,
        draft = draft,
        originalSiteName = "Lighthouse Reef",
        sites = listOf(
            DiveSite(id = "site-1", name = "Lighthouse Reef"),
            DiveSite(id = "site-2", name = "Blue Hole"),
        ),
        divers = divers,
        ownerDiverId = "diver",
        participantSearch = participantSearch,
        seriesOptions = seriesOptions,
        online = online,
    )

    private fun dive(
        overrides: List<String> = emptyList(),
        withGear: Boolean = false,
        participants: List<DiveViewParticipantsItem> = emptyList(),
    ) = DiveView(
        id = "d1",
        diverId = "diver",
        version = 3,
        values = DiveViewValues(
            number = 42,
            startsAt = DiveViewValuesStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = 3_000.0,
            maxDepthM = 24.3,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        overrides = overrides,
        utcOffsetSource = "device",
        fromProvider = null,
        fromRecording = DiveViewFromRecording(
            startsAt = DiveViewFromRecordingStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = 3_000.0,
            maxDepthM = 20.0,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        notes = "Two barracuda on the wreck.",
        site = DiveViewSite("site-1", "Lighthouse Reef"),
        waterType = "salt",
        waterMismatch = null,
        position = null,
        recordings = if (withGear) {
            listOf(
                DiveViewRecordingsItem(
                    id = "recording-1",
                    isPrimary = true,
                    device = DiveViewRecordingsItemDevice("garmin", "Descent Mk3", "123"),
                    startsAt = "2026-04-02T09:00:00Z",
                    durationSeconds = 3_000.0,
                    maxDepthM = 24.3,
                    parser = "fit",
                    partOf = null,
                    summary = DiveViewRecordingsItemSummary(),
                    channels = listOf("depth", "tankPressure"),
                ),
            )
        } else {
            emptyList()
        },
        cylinders = if (withGear) {
            listOf(
                DiveViewCylindersItem(
                    volumeL = 12.0,
                    workingPressureBar = 232.0,
                    material = "steel",
                    gas = DiveViewCylindersItemGas(o2 = 21.0, he = 0.0),
                    startPressureBar = 220.0,
                    endPressureBar = 70.0,
                    fromPod = true,
                    series = DiveViewCylindersItemSeries("recording-1", "tankPressure"),
                ),
                DiveViewCylindersItem(
                    volumeL = 11.0,
                    workingPressureBar = 200.0,
                    material = "aluminium",
                    gas = DiveViewCylindersItemGas(o2 = 32.0, he = 0.0),
                    startPressureBar = 200.0,
                    endPressureBar = 90.0,
                    fromPod = false,
                    series = null,
                ),
            )
        } else {
            emptyList()
        },
        lead = if (withGear) listOf(DiveViewLeadItem(amountKg = 4.0, placement = "belt"), DiveViewLeadItem(amountKg = 1.0, placement = "trim")) else emptyList(),
        leadTotalKg = if (withGear) 5.0 else null,
        weightingFeedback = null,
        suit = null,
        sac = null,
        sacMissing = null,
        participants = participants,
        verificationCodes = emptyList(),
    )

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
