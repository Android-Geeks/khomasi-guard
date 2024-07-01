package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.MessageResponse
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

    private val _reviewState: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Empty)
    val reviewState: StateFlow<DataState<MessageResponse>> = _reviewState

    private val _cancelState: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Empty)
    val cancelState: StateFlow<DataState<MessageResponse>> = _cancelState

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
                dialogDetails = dialogBooking
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

    fun onRateChange(rate: Int) {
        _uiState.update {
            it.copy(
                ratingValue = rate
            )
        }
    }

    fun review(email: String) {
        viewModelScope.launch {
            val localGuard = localGuardUseCases.getLocalGuard().first()
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
            remoteUseCases.ratePlayerUseCase(
                token = "Bearer $token",
                guardRating = RatingRequest(
                    userEmail = email,
                    guardId = guardID,
                    ratingValue = _uiState.value.ratingValue
                )
            ).collect {
                _reviewState.value = it
            }
        }
    }


    fun cancelBooking(bookingId: Int) {
        _uiState.update {
            it.copy(
                bookings = _uiState.value.bookings.filterNot { booking ->
                    booking.bookingDetails.bookingNumber == bookingId
                },
                bookingListNum = _uiState.value.bookingListNum - 1
            )
        }
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.cancelBookingUseCase(
                    token = "Bearer ${guardData.token}",
                    bookingId = bookingId,
                    isUser = false
                ).collect {
                    _cancelState.value = it
                }
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            appEntryUseCases.saveIsLogin(false)
            localGuardUseCases.saveLocalGuard(LocalGuard())
        }
    }

    fun clearStates() {
        _cancelState.value = DataState.Empty
        _reviewState.value = DataState.Empty
    }
}

