package com.company.khomasiguard.domain.model.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuardBooking(
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("bookingsCount")
    val bookingsCount: Int,
    @SerialName("bookings")
    val bookings: List<Booking>
)