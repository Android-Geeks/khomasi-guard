package com.company.khomasiguard.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.toFormattedDateString(): String {
    val sdf = SimpleDateFormat("EEEE,  dd LLLL yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toFormattedMonthDateString(): String {
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toFormattedDateShortString(): String {
    val sdf = SimpleDateFormat("dd", Locale.getDefault())
    return sdf.format(this)
}

fun Long.toFormattedDateString(): String {
    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toFormattedTimeString(): String {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormat.format(this)
}
fun Date.toAddTime(min: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MINUTE, min)
    return calendar.time
}

fun Date.hasPassed(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.SECOND, -1)
    val oneSecondAgo = calendar.time
    return time < oneSecondAgo.time
}
