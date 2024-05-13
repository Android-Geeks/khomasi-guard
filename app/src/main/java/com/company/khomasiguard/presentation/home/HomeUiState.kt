package com.company.khomasiguard.presentation.home

import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.domain.model.booking.GuardBooking

data class HomeUiState(
    val bookingList:List<Booking> = listOf(),
    val date: String="",
    val bookingListNum: Int = 1,
    val ratingValue: Int= 1,
    val guardBookingList: List<GuardBooking> = listOf(),
    val guardBooking: GuardBooking = GuardBooking(
        playgroundId = 1,
        bookingsCount = 1,
        bookings = bookingList
    ),
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
    )
)
