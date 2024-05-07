package com.company.khomasiguard.util

import android.os.Build
import androidx.annotation.RequiresApi
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.threeten.bp.LocalDateTime
fun String.toDateTime(format: String = "yyyy-MM-dd'T'HH:mm:ss"): Date? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
        sdf.parse(this)
    } catch (ex: Exception) {
        null
    }
}

fun String.toLocalTime(format: String = "hh:mm a"): LocalTime? {
    return try {
        LocalTime.parse(this, DateTimeFormatter.ofPattern(format))
    } catch (ex: Exception) {
        null
    }
}
