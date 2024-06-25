package com.company.khomasiguard.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.util.parseNullableTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _reviewState: MutableStateFlow<DataState<RatingRequest>> =
        MutableStateFlow(DataState.Empty)
    val reviewState: StateFlow<DataState<RatingRequest>> = _reviewState
    fun getHomeScreenBooking() {
        viewModelScope.launch() {
            val dayDiff = parseNullableTimestamp(_uiState.value.date.toString())?.let { timestamp ->
                val currentDate = LocalDateTime.now()
                ChronoUnit.DAYS.between(currentDate, timestamp).toInt()
            } ?: 0
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                   // dayDiff =  LocalDateTime.now().dayOfMonth
                    dayDiff = dayDiff
                ).collect { dataState ->
                    _responseState.value = dataState
                    Log.d("HomeBookingResponse", "HomeBookingResponse: $dataState")
                    if (dataState is DataState.Success) {
                       // updateBookingsCount(dataState.data.guardBookings)
                        dataState.data.guardBookings.forEach { guardBooking ->
                            _uiState.update {
                                it.copy(
                                    guardBooking = guardBooking,
                                    bookingListNum = guardBooking.bookingsCount,
                                    bookingList = guardBooking.bookings,

                                )
                            }
                            guardBooking.bookings.forEach { booking ->
                                _uiState.update {
                                    it.copy(
                                        bookingDetails = booking,
                                        date = booking.bookingTime.toInt()
                                    )
                                }
                            }
                        }
//                        _uiState.update {
//                            it.copy(
//                                guardBookings = dataState.data.guardBookings.forEach() {
//                                    guardBooking -> guardBooking.bookings.filter {
//                                        booking -> booking.isCanceled
//                                    }
//                                }
//                            )
//                        }
//                        dataState.data.guardBookings.forEach { guardBooking ->
//                            _uiState.update {
//                                it.copy(
//                                    guardBooking = guardBooking,
//                                    bookingListNum = guardBooking.bookingsCount,
//                                    bookingList = guardBooking.bookings,
//                                )
//                            }
//                            guardBooking.bookings.forEach { booking ->
//                                _uiState.update {
//                                    it.copy(
//                                        bookingDetails = booking,
//                                    )
//                                }
//                            }
//                        }

                    } else if (dataState is DataState.Error) {
                        Log.e(
                            "HomeBookingError",
                            "Error code: ${dataState.code}, message: ${dataState.message}"
                        )



                    }
                }
            }
        }

    }

//    fun updateBookingsCount(count:Int){
//        _uiState.update {
//            it.copy(
//                bookingListNum = count
//            )
//        }
//    }

    fun updateBookingsCount(guardBookings: List<GuardBooking>){
        val currentBookings:MutableList<Bookings> = mutableListOf()
        var bookingsCount=0
        guardBookings.forEach {

            val bookings = it.bookings.filter { condition ->
                !condition.isCanceled
            }

            bookingsCount += bookings.size
            currentBookings.add(Bookings(
                playgroundName = it.playgroundName,
                currentBookings = bookings
            ))
        }
        _uiState.update {
            it.copy(
                guardBookings= guardBookings,
                bookingListNum = bookingsCount,
                bookings = currentBookings

            )
        }
    }
    fun review() {
        viewModelScope.launch {
            val localGuard = localGuardUseCases.getLocalGuard().first()
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
            remoteUseCases.ratePlayerUseCase(
                token = "Bearer $token",
                guardRating = RatingRequest(
                    userEmail = "user26@example.com",
                    guardId = guardID,
                    ratingValue = _uiState.value.ratingValue
                )
            ).collect {
            }
        }
    }

}

