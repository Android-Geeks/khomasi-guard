package com.company.khomasiguard.util


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
fun String.toDateTime(format: String = "yyyy-MM-dd'T'HH:mm:ss"): Date? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
        sdf.parse(this)
    } catch (ex: Exception) {
        null
    }
}

