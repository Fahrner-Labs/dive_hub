package dev.divehub.shared

/**
 * A refusal the app can translate by its `code` (client contract §2). The server's English `error`/`message` text is
 * kept for logs only and never shown.
 */
class DiveHubException(
    val code: String,
    val status: Int? = null,
    val serverMessage: String? = null,
    cause: Throwable? = null,
) : Exception(code, cause)

/** A request that got no answer at all: offline or the instance cannot be reached. Not an unknown error. */
class NoAnswerException(cause: Throwable? = null) : Exception("No answer from the server", cause)
