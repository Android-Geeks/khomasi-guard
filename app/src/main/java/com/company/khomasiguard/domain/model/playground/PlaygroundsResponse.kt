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