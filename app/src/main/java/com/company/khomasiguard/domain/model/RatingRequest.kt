package com.company.khomasiguard.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingRequest(
    @SerialName("userEmail")
    val userEmail: String,
    @SerialName("guardId")
    val guardId: String,
    @SerialName("ratingValue")
    val ratingValue: Int
)