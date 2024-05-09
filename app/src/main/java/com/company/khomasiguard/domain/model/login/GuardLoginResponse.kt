package com.company.khomasiguard.domain.model.login


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuardLoginResponse(
    @SerialName("user")
    val user: User,
    @SerialName("token")
    val token: String,
    @SerialName("expiration")
    val expiration: String
)