
package com.company.khomasiguard.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.presentation.components.SelectedFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState
    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState

    fun getBooking() {
        viewModelScope.launch() {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                    date = _uiState.value.selectedDay.toString()
                ).collect { dataState ->
                    _responseState.value = dataState
                    Log.d("BookingResponse", "BookingResponse: $dataState")
                    if (dataState is DataState.Success) {
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
                    } else if (dataState is DataState.Error) {
                        Log.e(
                            "BookingError",
                            "Error code: ${dataState.code}, message: ${dataState.message}"
                        )
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
            playgroundResults =  when (filter) {
                SelectedFilter.TOP_RATING -> _uiState.value.playgroundResults.sortedByDescending{ booking->booking.rating }
                SelectedFilter.BOOKING_FIRST -> _uiState.value.playgroundResults.sortedBy {
                    booking -> booking.bookingNumber
                 }
            }
        )
    }
}
