package dev.divehub.android.ui.navigation

import dev.divehub.android.ui.tools.ToolPage
import kotlinx.serialization.Serializable

/**
 * The app shell's destinations (ADR 0079). They are Serializable so Navigation can encode them in the back stack and
 * restore the dive id and the tool page after process death.
 */
@Serializable
internal data object InstanceRoute

@Serializable
internal data object SignInRoute

@Serializable
internal data object MainRoute

@Serializable
internal data object LogbookRoute

@Serializable
internal data class DiveRoute(val id: String)

@Serializable
internal data class DiveEditRoute(val id: String)

@Serializable
internal data class ToolsRoute(val page: ToolPage = ToolPage.Index)

@Serializable
internal data object SettingsRoute

@Serializable
internal data object SettingsDebugRoute

/** The device-vs-hub decision screen (ADR 0084 D3); opened from the pending indicator, above its origin. */
@Serializable
internal data object ConflictsRoute

@Serializable
internal data object ScanRoute
