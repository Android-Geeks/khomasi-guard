package com.company.khomasiguard.presentation.booking.component

import com.company.khomasiguard.util.extractDateFromTimestamp
import com.company.khomasiguard.util.parseTimestamp
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

data class CalendarUiModel(
    val selectedDate: Date, // the date selected by the User. by default is Today.
    val visibleDates: List<Date> // the dates shown on the screen
) {

    val startDate: Date = visibleDates.first() // the first of the visible dates
    val endDate: Date = visibleDates.last() // the last of the visible dates

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
       // val day :String = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      // val day: String = date.format(DateTimeFormatter.ofPattern("E")) // get the day by formatting the date
    }
}
