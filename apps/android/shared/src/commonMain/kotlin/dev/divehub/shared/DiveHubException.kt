package dev.divehub.shared

/**
 * A refusal the app can translate by its `code` (client contract §2). The server's English `error`/`message` text is
 * kept for logs only and never shown.
 */
open class DiveHubException(
    val code: String,
    val status: Int? = null,
    val serverMessage: String? = null,
    cause: Throwable? = null,
) : Exception(code, cause)

/** A request that got no answer at all: offline or the instance cannot be reached. Not an unknown error. */
class NoAnswerException(cause: Throwable? = null) : Exception("No answer from the server", cause)

/**
 * A server answer whose body did not decode: a contract violation, not a network failure and never worth a retry.
 * It is a [DiveHubException] so every caller's refusal path ends the request cleanly instead of letting a serializer
 * exception escape into the app's coroutine scope.
 */
class DecodeException(cause: Throwable? = null) : DiveHubException(ErrorCodes.SERVER_ERROR, cause = cause)

