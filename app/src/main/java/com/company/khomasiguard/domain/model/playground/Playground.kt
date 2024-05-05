package com.company.khomasiguard.domain.model.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Playground(
    @SerialName("playgroundInfo")
    val playgroundInfo: PlaygroundInfo,
    @SerialName("newBookings")
    val newBookings: Int,
    @SerialName("finishedBookings")
    val finishedBookings: Int,
    @SerialName("totalBookings")
    val totalBookings: Int
)