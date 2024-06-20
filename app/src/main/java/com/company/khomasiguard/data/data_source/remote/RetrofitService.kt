package com.company.khomasiguard.data.data_source.remote

import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.MessageResponse
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import com.company.khomasiguard.domain.model.playground.PlaygroundsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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
        @Query("guardId") guardID: String
    ): Response<PlaygroundsResponse>

    @GET("Guard/bookings")
    suspend fun getGuardBookings(
        @Header("Authorization") token: String,
        @Query("guardId") guardID: String,
        @Query("date") date: String
    ): Response<BookingsResponse>

    @PUT("Playground/state")
    suspend fun playgroundState(
        @Header("Authorization") token: String,
        @Query("playgroundId") playgroundId: Int,
        @Query("isActive") isActive: Boolean
    ):Response<MessageResponse>
}