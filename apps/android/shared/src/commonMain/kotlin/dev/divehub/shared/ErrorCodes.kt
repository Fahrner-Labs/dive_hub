package dev.divehub.shared

/** The codes the app translates. Server codes arrive as they are (docs/spec/clients.md §2); the UI falls back. */
object ErrorCodes {
    /** The server's sign-in failed; never say whether the e-mail exists (client contract §1 Security). */
    const val INVALID_CREDENTIALS = "invalid_credentials"
    const val TOO_MANY_ATTEMPTS = "too_many_attempts"
    const val USER_DISABLED = "user_disabled"
    const val SIGN_IN_REQUIRED = "sign_in_required"
    const val INVALID_INSTANCE_URL = "invalid_instance_url"
    const val MISSING_TOKEN = "missing_token"
    const val SERVER_ERROR = "internal_error"
    /** The server refuses this app's version (ADR 0076): the session is fine, the app is old. */
    const val CLIENT_TOO_OLD = "client_too_old"

    /** A request got no answer at all (offline, unreachable); not a server refusal. */
    const val NO_ANSWER = "no_answer"

    /** The instance's admin does not allow client diagnostics uploads (ADR 0080 D2). */
    const val CLIENT_DIAGNOSTICS_DISABLED = "client_diagnostics_disabled"

    /** Too many diagnostics uploads from this User in the last hour (ADR 0080 D5). */
    const val CLIENT_DIAGNOSTICS_LIMITED = "client_diagnostics_limited"
}
