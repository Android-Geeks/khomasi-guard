package com.company.khomasiguard.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.presentation.components.SelectedFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState

    fun getBooking() {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                    dayDiff = _uiState.value.selectedDay
                ).collect { dataState ->
                    _responseState.value = dataState
                    Log.d("BookingResponse", "BookingResponse: $dataState")
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                            _uiState.update {
                                it.copy(
                                    guardBookings = dataState.data.guardBookings
                                )
                            }
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
                                        )
                                    }
                                }
                            }
                        }

                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Error fetching playgrounds: ${dataState.message}"
                            )
                        }

                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }

                        is DataState.Empty -> {}
                    }
                }
            }
        }
    }

    fun updateSelectedDay(day: Int) {
        _uiState.update {
            it.copy(
                selectedDay = day,
                selectedSlots = mutableListOf()
            )
        }
    }

    fun onSelectedFilterChanged(filter: SelectedFilter) {
        _uiState.value = _uiState.value.copy(
            searchFilter = filter,
            playgroundResults = when (filter) {
                SelectedFilter.TOP_RATING -> _uiState.value.playgroundResults.sortedByDescending { booking -> booking.rating }
                SelectedFilter.BOOKING_FIRST -> _uiState.value.playgroundResults.sortedBy { booking ->
                    booking.bookingNumber
                }
            }
        )
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
            ).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _uiState.update {
                            it.copy(
                                rateMessage = dataState.data.message
                            )
                        }
                    }
                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = "Error updating playground state: ${dataState.message}"
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
    fun onCancel(id: Int) {
        _uiState.update {
            it.copy(
                guardBookings = it.guardBookings.filterNot { guardBooking ->
                    guardBooking.bookings.any { booking ->
                        booking.bookingNumber == id
                    }
                }
            )
        }
    }
    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.cancelBookingUseCase(
                    token = "Bearer ${guardData.token}",
                    bookingId = bookingId,
                    isUser = false
                ).collect {dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.update {
                                it.copy(
                                    cancelMessage = dataState.data.message
                                )
                            }
                        }
                        is DataState.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = "Error updating playground state: ${dataState.message}"
                                )
                            }
                        }

                        else -> {}
                    }
                }

            }
        }
    }
}
