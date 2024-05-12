package com.company.khomasiguard.data.data_source.remote

import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.MessageResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import com.company.khomasiguard.domain.model.playground.PlaygroundsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitService {
    @POST("Account/login")
    suspend fun loginGuard(
        @Query("email") email: String,
        @Query("passwd") password: String
    ): Response<GuardLoginResponse>

    @POST("Guard/rating")
    suspend fun ratePlayer(
        @Header("Authorization") token: String,
        @Body guardRating: RatingRequest
    ): Response<MessageResponse>

    @GET("Guard/playgrounds")
    suspend fun getGuardPlaygrounds(
        @Header("Authorization") token: String,
        @Query("guardID") guardID: String
    ): Response<PlaygroundsResponse>

    @GET("Guard/bookings")
    suspend fun getGuardBookings(
        @Header("Authorization") token: String,
        @Query("guardID") guardID: String,
        @Query("date") date: String
    ): Response<GuardBooking>
}