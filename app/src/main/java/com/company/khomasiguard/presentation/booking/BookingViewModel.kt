package com.company.khomasiguard.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.presentation.home.Bookings
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
                            updateBookingsCount(dataState.data.guardBookings)
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

    fun onClickDialog(dialogBooking: Bookings) {
        _uiState.update {
            it.copy(
                dialogBooking = dialogBooking
            )
        }
    }

    private fun updateBookingsCount(guardBookings: List<GuardBooking>) {
        val currentBookings: MutableList<Bookings> = mutableListOf()
        var bookingsCount = 0
        guardBookings.forEach {

            val bookings = it.bookings.filter { condition ->
                !condition.isCanceled
            }

            bookingsCount += bookings.size
            bookings.forEach { booking ->
                currentBookings.add(
                    Bookings(
                        playgroundName = it.playgroundName,
                        bookingDetails = booking
                    )
                )
            }
        }
        _uiState.update {
            it.copy(
                bookingListNum = bookingsCount,
                bookings = currentBookings
            )
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


    fun cancelBooking(bookingId: Int) {
        _uiState.update {
            it.copy(
                bookings = _uiState.value.bookings.filterNot { booking ->
                    booking.bookingDetails.bookingNumber == bookingId
                }
            )
        }
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.cancelBookingUseCase(
                    token = "Bearer ${guardData.token}",
                    bookingId = bookingId,
                    isUser = false
                ).collect { dataState ->
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
