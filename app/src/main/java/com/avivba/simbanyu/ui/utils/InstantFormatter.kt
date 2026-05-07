package com.avivba.simbanyu.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Returns the 3-letter Indonesian month name for this Instant.
 * @param zoneId The time zone to use (defaults to system default)
 */
fun Instant.toMonthShort(zoneId: ZoneId = ZoneId.systemDefault()): String {
    val localeId = Locale("id", "ID")
    val formatter = DateTimeFormatter.ofPattern("MMM")
        .withLocale(localeId)
        .withZone(zoneId)

    return this.formatWith(formatter)
}

/**
 * Returns a relative time string in Indonesian (e.g., "Baru saja", "5 mnt lalu").
 */
fun Instant.toRelativeTime(): String {
    val now = Instant.now()
    val diffSeconds = now.epochSecond - this.epochSecond

    return when {
        diffSeconds < 60 -> "Baru saja"
        diffSeconds < 3600 -> "${diffSeconds / 60} mnt lalu"
        diffSeconds < 86400 -> "${diffSeconds / 3600} jam lalu"
        diffSeconds < 604800 -> "${diffSeconds / 86400} hr lalu"
        else -> {
            val localeId = Locale("id", "ID")
            val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
                .withLocale(localeId)
                .withZone(ZoneId.systemDefault())
            this.formatWith(formatter)
        }
    }
}

// Helper to make formatting syntax cleaner
private fun Instant.formatWith(formatter: DateTimeFormatter): String {
    return formatter.format(this)
}