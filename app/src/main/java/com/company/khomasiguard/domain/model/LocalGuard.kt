package com.company.khomasiguard.domain.model

data class LocalGuard(
    val guardID: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val ownerId: String? = null,
    val token: String? = null
)