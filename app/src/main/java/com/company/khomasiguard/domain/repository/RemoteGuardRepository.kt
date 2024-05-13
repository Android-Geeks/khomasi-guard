package com.company.khomasiguard.domain.repository

import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.MessageResponse
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import com.company.khomasiguard.domain.model.playground.PlaygroundsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteGuardRepository {
    suspend fun loginGuard(email: String, password: String): Flow<DataState<GuardLoginResponse>>
    suspend fun ratePlayer(token: String, guardRating: RatingRequest): Flow<DataState<MessageResponse>>
    suspend fun getGuardPlaygrounds(token: String,guardID: String): Flow<DataState<PlaygroundsResponse>>
    suspend fun getGuardBookings(token: String,guardID: String,date: String): Flow<DataState<BookingsResponse>>

}