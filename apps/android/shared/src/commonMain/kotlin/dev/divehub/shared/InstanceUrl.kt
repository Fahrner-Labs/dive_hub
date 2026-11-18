package dev.divehub.shared

/** The self-hosted instance address: scheme and authority only, no trailing slash, no path (ADR 0074). */
object InstanceUrl {
    private val PATTERN = Regex("^(https?)://([^/?#\\s]+)(/[^?#\\s]*)?$", RegexOption.IGNORE_CASE)

    fun normalize(raw: String): String? {
        val text = raw.trim()
        if (text.isEmpty()) return null
        val withScheme = if (text.contains("://")) text else "https://$text"
        val match = PATTERN.matchEntire(withScheme) ?: return null
        val scheme = match.groupValues[1].lowercase()
        val authority = match.groupValues[2]
        val path = match.groupValues[3].trimEnd('/')
        if (authority.isEmpty() || authority.contains('@')) return null
        if (path.isNotEmpty()) return null
        return "$scheme://$authority"
    }
}
