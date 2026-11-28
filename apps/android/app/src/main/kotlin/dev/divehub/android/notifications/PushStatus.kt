package dev.divehub.android.notifications

/** Where the push lifecycle stands; Settings → Debug turns it into one sentence. */
enum class PushState {
    Unknown,
    Off,
    ConfigFailed,
    NoPlayServices,
    InitFailed,
    PermissionMissing,
    NotSignedIn,
    TokenFailed,
    RegisterFailed,
    Registered,
}

/**
 * The Debug status row's read model (FAH-557). [reason] is a problem code or an exception class name, never a token or
 * any other secret; [deviceId] and [lastSeenAt] come from the server's registration answer.
 */
data class PushStatus(
    val state: PushState = PushState.Unknown,
    val deviceId: String? = null,
    val lastSeenAt: String? = null,
    val reason: String? = null,
)
