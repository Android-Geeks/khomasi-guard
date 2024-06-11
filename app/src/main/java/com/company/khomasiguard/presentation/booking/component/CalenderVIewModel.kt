//package com.company.khomasiguard.presentation.booking.component
//
//import org.threeten.bp.DayOfWeek
//import org.threeten.bp.LocalDate
//class CalendarDataSource {
//
//    val today: LocalDate
//        get() {
//            return LocalDate.now()
//        }
//    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
//        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
//        val endDayOfWeek = firstDayOfWeek.plusDays(7)
//        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
//        return toUiModel(visibleDates, lastSelectedDate)
//    }
//
//    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
//        val dates = mutableListOf<LocalDate>()
//        var currentDate = startDate
//        while (currentDate.isBefore(endDate)) {
//            dates.add(currentDate)
//            currentDate = currentDate.plusDays(1)
//        }
//        return dates
//    }
//
//    private fun toUiModel(
//        dateList: List<LocalDate>,
//        lastSelectedDate: LocalDate
//    ): CalendarUiModel {
//        return CalendarUiModel(
//            selectedDate = toItemUiModel(lastSelectedDate, true),
//            visibleDates = dateList.map {
//                toItemUiModel(it, it.isEqual(lastSelectedDate))
//            },
//        )
//    }
//
//    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
//        isSelected = isSelectedDate,
//        isToday = date.isEqual(today),
//        date = date,
//    )
//}


package com.company.khomasiguard.presentation.booking.component

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class CalendarDataSource {

    val today: LocalDate
        get() = LocalDate.now()

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var currentDate = startDate
        while (currentDate.isBefore(endDate)) {
            dates.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }
        return dates
    }

    private fun toUiModel(dateList: List<LocalDate>, lastSelectedDate: LocalDate): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}
