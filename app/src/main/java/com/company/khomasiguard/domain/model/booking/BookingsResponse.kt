package com.company.khomasiguard.domain.model.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingsResponse(
    @SerialName("guardBookings")
    val guardBookings: List<GuardBooking>
)