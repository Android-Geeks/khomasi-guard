package com.company.khomasiguard.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.util.extractDateFromTimestamp
import com.company.khomasiguard.util.parseTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun getHomeScreenBooking() {
        viewModelScope.launch {
            val localGuard = localGuardUseCases.getLocalGuard().first()
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
            val date = extractDateFromTimestamp(parseTimestamp(_uiState.value.date), format = "dd MMMM yyyy")
            remoteUseCases.getGuardBookingsUseCase(
                token = "Bearer $token",
                guardID = guardID,
                date = date
            ).collect { dataState ->
                _responseState.value=dataState
                Log.d("HomeBookingResponse", "HomeBookingResponse: $dataState")
                _responseState.value = dataState
                if (dataState is DataState.Success) {
                    dataState.data.guardBookings.forEach { guardBooking ->
                        _uiState.value = _uiState.value.copy(
                            guardBooking = guardBooking,
                            bookingListNum = guardBooking.bookingsCount,
                            bookingList = guardBooking.bookings,
                        )
                        guardBooking.bookings.forEach { booking ->
                            _uiState.value = _uiState.value.copy(
                                bookingDetails = booking,
                            )
                        }
                    }
                } else if (dataState is DataState.Error) {
                    Log.e(
                        "HomeBookingError",
                        "Error code: ${dataState.code}, message: ${dataState.message}"
                    )
                }
            }
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
            ).collect {}
        }
    }
}


