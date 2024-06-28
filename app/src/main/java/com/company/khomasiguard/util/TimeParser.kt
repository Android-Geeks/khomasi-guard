package com.company.khomasiguard.util

import android.util.Log
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.temporal.ChronoUnit
import java.time.ZoneId
import java.util.Date

fun parseTimestamp(timestamp: String): LocalDateTime {
    Log.d(
        "Timing", "Timestamp: ${
            LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } Now: ${LocalDateTime.now()}"
    )
    return if (timestamp.isNotEmpty()) {
        LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    } else {
        LocalDateTime.now()
    }
}

//fun parseDateToDayDiff(dateString: String): Int {
//    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
//    val date = LocalDate.parse(dateString, formatter)
//    val currentDate = LocalDate.now()
//    return ChronoUnit.DAYS.between(currentDate, date).toInt()
//}

// Function returning LocalDateTime?
fun parseNullableTimestamp(timestamp: String): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return try {
        LocalDateTime.parse(timestamp, formatter)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null // Handle parsing failure gracefully
    }
}


fun extractTimeFromTimestamp(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun extractDateFromTimestamp(localDateTime: LocalDateTime, format: String = "dd-MM-yyyy"): String {
    return localDateTime.format(DateTimeFormatter.ofPattern(format))
}

