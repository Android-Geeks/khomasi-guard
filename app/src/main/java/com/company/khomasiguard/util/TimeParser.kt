package com.company.khomasiguard.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

fun parseTimestamp(timestamp: String): LocalDateTime {
    return if (timestamp.isNotEmpty()) {
        LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    } else {
        LocalDateTime.now()
    }
}

fun extractTimeFromTimestamp(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun extractDateFromTimestamp(localDateTime: LocalDateTime, format: String = "dd-MM-yyyy"): String {
    return localDateTime.format(DateTimeFormatter.ofPattern(format))
}

