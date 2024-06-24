package com.company.khomasiguard.domain.model.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundsResponse(
    @SerialName("playgroundCount")
    val playgroundCount: Int,
    @SerialName("playgrounds")
    val playgrounds: List<Playground>
)
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
@Serializable
data class PlaygroundInfo(
    @SerialName("playground")
    val playground: PlaygroundX,
    @SerialName("picture")
    val picture: String?
)
@Serializable
data class PlaygroundX(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("feesForHour")
    val feesForHour: Int,
    @SerialName("address")
    val address: String,
    @SerialName("isBookable")
    var isBookable: Boolean
)