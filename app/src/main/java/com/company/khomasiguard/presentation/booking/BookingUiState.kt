package com.company.khomasiguard.presentation.booking

import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.presentation.components.SelectedFilter
import org.threeten.bp.LocalDateTime


data class BookingUiState(
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
    val bookingDetails :Booking = Booking(
        bookingTime = "2024-05-05T04:15:00",
        userName = "userName",
        profilePicture = "profilePicture",
        rating = 4,
        cost = 50,
        email = "email",
        phoneNumber = "phoneNumber",
        bookingNumber = 1,
        confirmationCode = "2345",
        isCanceled = false,
        duration = 47.0
    ),
    val bookingList:List<Booking> = listOf(),
    val date: Int=0,
    val bookingListNum: Int = 1,
    val ratingValue: Int= 1,
    val guardBooking: GuardBooking = GuardBooking(
        playgroundId = 1,
        bookingsCount = 0,
        bookings = bookingList,
        playgroundName = " "
    ),
    var guardBookings: List<GuardBooking> = listOf(),
    val searchFilter: SelectedFilter = SelectedFilter.BOOKING_FIRST,
    val playgroundResults: List<Booking> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
    )
