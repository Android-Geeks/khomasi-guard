package com.company.khomasiguard.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState

    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState
    private val _localGuard = localGuardUseCases.getLocalGuard().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalGuard()
    )
    val localGuard: StateFlow<LocalGuard> = _localGuard
    fun getBooking() {
        viewModelScope.launch() {
            remoteUseCases.getGuardBookingsUseCase(
                token = "Bearer ${_localGuard.value.token ?: ""}",
                guardID = _localGuard.value.guardID ?: "",
                date = _uiState.value.bookingDetails.bookingTime
                ).collect { dataState ->
                _responseState.value = dataState
                if (dataState is DataState.Success) {
                    dataState.data.guardBookings.forEach { guardBooking ->
                        _uiState.value = _uiState.value.copy(
                            guardBookingList = dataState.data.guardBookings,
                        )
                        _uiState.value = _uiState.value.copy(
                            guardBooking =  guardBooking,
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