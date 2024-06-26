package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
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

    private val _reviewState: MutableStateFlow<DataState<RatingRequest>> =
        MutableStateFlow(DataState.Empty)
    val reviewState: StateFlow<DataState<RatingRequest>> = _reviewState
    fun getHomeScreenBooking() {
        viewModelScope.launch() {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                    dayDiff = 0
                ).collect { dataState ->
                    _responseState.value = dataState
                    when (dataState) {
                        is DataState.Success -> {
                            updateBookingsCount(dataState.data.guardBookings)
                        }

                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Error fetching playgrounds: ${dataState.message}"
                            )
                        }

                        is DataState.Empty -> {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                        }

                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
            }

        }
    }
    fun onClickDialog(dialogBooking: DialogBooking) {
        _uiState.update {
            it.copy(
                bookingDetails = dialogBooking
            )
        }
    }

    fun updateBookingsCount(guardBookings: List<GuardBooking>) {
        val currentBookings: MutableList<Bookings> = mutableListOf()
        var bookingsCount = 0
        guardBookings.forEach {

            val bookings = it.bookings.filter { condition ->
                !condition.isCanceled
            }

            bookingsCount += bookings.size
            currentBookings.add(
                Bookings(
                    playgroundName = it.playgroundName,
                    currentBookings = bookings
                )
            )
        }
        _uiState.update {
            it.copy(
                guardBookings = guardBookings,
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

