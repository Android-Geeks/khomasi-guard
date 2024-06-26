package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases,
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _reviewState: MutableStateFlow<DataState<RatingRequest>> =
        MutableStateFlow(DataState.Empty)

    fun getHomeScreenBooking() {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                    dayDiff = 0
                ).collect { dataState ->
                    _responseState.value = dataState
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

    fun onClickDialog(dialogBooking: DialogBooking) {
        _uiState.update {
            it.copy(
                bookingDetails = dialogBooking
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
            ).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _reviewState.update { it }
                    }

                    is DataState.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Error updating playground state: ${dataState.message}"
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.cancelBookingUseCase(
                    token = "Bearer ${guardData.token}",
                    bookingId = bookingId,
                    isUser = false
                ).collect {}

            }
        }
    }
    fun onLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            appEntryUseCases.saveIsLogin(false)
            localGuardUseCases.saveLocalGuard(LocalGuard())
        }
    }

}

