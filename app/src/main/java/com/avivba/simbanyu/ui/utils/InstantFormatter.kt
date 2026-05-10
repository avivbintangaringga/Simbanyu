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
    val localeId = Locale.forLanguageTag("id-ID")
    val formatter = DateTimeFormatter.ofPattern("MMM")
        .withLocale(localeId)
        .withZone(zoneId)

    return this.formatWith(formatter)
}

/**
 * Returns the full Indonesian month name for this Instant.
 * @param zoneId The time zone to use (defaults to system default)
 */
fun Instant.toMonthFull(zoneId: ZoneId = ZoneId.systemDefault()): String {
    val localeId = Locale.forLanguageTag("id-ID")
    val formatter = DateTimeFormatter.ofPattern("MMMM")
        .withLocale(localeId)
        .withZone(zoneId)

    return this.formatWith(formatter)
}

/**
 * Returns the year for this Instant.
 * @param zoneId The time zone to use (defaults to system default)
 */
fun Instant.toYear(zoneId: ZoneId = ZoneId.systemDefault()): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy")
        .withZone(zoneId)

    return this.formatWith(formatter)
}

/**
 * Returns the date and time in format "d MMMM yyyy HH:mm" (e.g., 12 Agustus 2026 23:22)
 * @param zoneId The time zone to use (defaults to system default)
 */
fun Instant.toFullDateTime(zoneId: ZoneId = ZoneId.systemDefault()): String {
    val localeId = Locale.forLanguageTag("id-ID")
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm")
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
        diffSeconds < 3600 -> "${diffSeconds / 60} menit lalu"
        diffSeconds < 86400 -> "${diffSeconds / 3600} jam lalu"
        diffSeconds < 604800 -> "${diffSeconds / 86400} hari lalu"
        else -> {
            val localeId = Locale.forLanguageTag("id-ID")
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
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