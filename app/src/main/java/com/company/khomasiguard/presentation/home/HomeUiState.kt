package com.company.khomasiguard.presentation.home

import com.company.khomasiguard.domain.model.booking.Booking

data class HomeUiState(
    val bookingList:List<Booking> = listOf(),
    val date: String="",
    val bookingListNum: Int = 1,
    val bookingDetails :   Booking = Booking(
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
