package com.company.khomasiguard.presentation.booking

import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.presentation.home.Bookings
import org.threeten.bp.LocalDateTime


data class BookingUiState(
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
    val dialogBooking: Bookings = Bookings(
        "", Booking(
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
        )
    ),
    val bookings: List<Bookings> = listOf(),
    val date: LocalDateTime = LocalDateTime.now(),
    val bookingListNum: Int = 1,
    val ratingValue: Int = 1,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val rateMessage: String? = null,
    val cancelMessage: String? = null,
)