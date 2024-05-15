package com.company.khomasiguard.presentation.booking

import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.domain.model.booking.GuardBooking
data class BookingUiState(
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
        duration = 47
    ),
    val bookingList:List<Booking> = listOf(bookingDetails),
    val date: String="",
    val bookingListNum: Int = 1,
    val ratingValue: Int= 1,
    val guardBooking: GuardBooking = GuardBooking(
        playgroundId = 1,
        bookingsCount = 0,
        bookings = bookingList
    ),
    val guardBookingList: List<GuardBooking> = listOf(guardBooking),

    )
