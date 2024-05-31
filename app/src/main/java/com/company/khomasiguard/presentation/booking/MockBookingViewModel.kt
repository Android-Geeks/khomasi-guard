package com.company.khomasiguard.presentation.booking

import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
class MockBookingViewModel: ViewModel() {
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState
    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState
    fun getBooking(date:String) {}
}
