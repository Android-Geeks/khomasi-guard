package com.company.khomasiguard.domain.model.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingsResponse(
    @SerialName("guardBookings")
    val guardBookings: List<GuardBooking>
)
@Serializable
data class GuardBooking(
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("playgroundName")
    val playgroundName: String,
    @SerialName("bookingsCount")
    val bookingsCount: Int,
    @SerialName("bookings")
    val bookings: List<Booking>
)
@Serializable
data class Booking(
    @SerialName("userName")
    val userName: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("email")
    val email: String,
    @SerialName("profilePicture")
    val profilePicture: String?,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("bookingNumber")
    val bookingNumber: Int,
    @SerialName("cost")
    val cost: Int,
    @SerialName("confirmationCode")
    val confirmationCode: String,
    @SerialName("isCanceled")
    val isCanceled: Boolean,
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Double
)