package com.company.khomasiguard.presentation.home

import com.company.khomasiguard.domain.model.booking.Booking
import org.threeten.bp.LocalDateTime

data class HomeUiState(
    val date: LocalDateTime = LocalDateTime.now(),
    val bookingListNum: Int = 0,
    val ratingValue: Int = 0,
    val isLoading: Boolean = true,
    val dialogDetails: Bookings = Bookings("", Booking(
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
    )),
    val bookings: List<Bookings> = listOf()
)

data class Bookings(
    val playgroundName: String,
    val bookingDetails: Booking
)
