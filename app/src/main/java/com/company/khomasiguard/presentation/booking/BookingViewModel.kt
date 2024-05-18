package com.company.khomasiguard.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
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
class BookingViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
    ) : ViewModel()
{
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState

    fun getBooking() {
        viewModelScope.launch() {
            val localGuard = localGuardUseCases.getLocalGuard().first()
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
            val date = extractDateFromTimestamp(parseTimestamp(_uiState.value.date), format = "dd MMMM yyyy")
            remoteUseCases.getGuardBookingsUseCase(
                token = "Bearer $token",
                guardID = guardID,
                date = date
                ).collect { dataState ->
                _responseState.value = dataState
                if (dataState is DataState.Success) {
                    Log.d("BookingViewModel","BookingResponse:${dataState}")
                    dataState.data.guardBookings.forEach { guardBooking ->
                        _uiState.value = _uiState.value.copy(
                            guardBookingList = dataState.data.guardBookings,
                        )
                        _uiState.value = _uiState.value.copy(
                            guardBooking =  guardBooking,
                        )
                        _uiState.value = _uiState.value.copy(
                            bookingList = guardBooking.bookings,
                        )
                        guardBooking.bookings.forEach { booking ->
                            _uiState.value = _uiState.value.copy(
                                bookingDetails = booking,
                            )
                        }
                    }
                }
            }

            }
        }
    }