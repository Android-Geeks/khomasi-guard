package com.company.khomasiguard.presentation.booking

import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockBookingViewModel: ViewModel() {
    val cancelState: StateFlow<DataState<MessageResponse>> = MutableStateFlow(DataState.Empty).asStateFlow()
    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState
    fun getBooking() {}
}
