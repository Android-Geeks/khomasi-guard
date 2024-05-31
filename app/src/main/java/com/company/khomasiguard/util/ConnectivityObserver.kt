package com.company.khomasiguard.util

import kotlinx.coroutines.flow.Flow

 fun interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}

