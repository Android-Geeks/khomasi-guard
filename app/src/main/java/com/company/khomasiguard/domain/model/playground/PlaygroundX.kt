package com.company.khomasiguard.domain.model.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val isBookable: Boolean
)