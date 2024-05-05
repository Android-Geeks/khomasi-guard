package com.company.khomasiguard.domain.model.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundInfo(
    @SerialName("playground")
    val playground: PlaygroundX,
    @SerialName("picture")
    val picture: String?
)