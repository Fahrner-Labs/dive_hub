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
}
