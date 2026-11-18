package dev.divehub.shared.logbook

/** A row's gas by the name divers use: "18/45", "EAN32", or the language's word for air. */
fun mixName(o2: Double, he: Double, air: String): String = when {
    he > 0 -> "${o2.toInt()}/${he.toInt()}"
    o2 == 21.0 -> air
    else -> "EAN${o2.toInt()}"
}

/**
 * Two letters for a Diver's circle: the first letters of the first and the last name, or the first two of a single
 * name. One letter collides too often (Lena, Lars, Lukas).
 */
fun initials(name: String): String {
    val words = name.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
    val first = words.firstOrNull().orEmpty()
    if (words.size < 2) return first.take(2).uppercase()
    return (first.take(1) + words.last().take(1)).uppercase()
}

/** Whole minutes, as a logbook row shows a duration. */
fun durationMinutes(seconds: Double): Long = (seconds / 60.0).toLong().let { if (seconds % 60 >= 30) it + 1 else it }

/** A surface interval is said in a row while the dive before was the same day's (the web client's rule). */
const val SAME_DAY_SECONDS: Double = 24.0 * 3600.0
