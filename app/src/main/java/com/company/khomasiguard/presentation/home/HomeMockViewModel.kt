package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.model.booking.GuardBooking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeMockViewModel: ViewModel() {
    private val _homeState: MutableStateFlow<DataState<GuardBooking>> =
        MutableStateFlow(DataState.Empty)
    val homeState: StateFlow<DataState<GuardBooking>> = _homeState

    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)

    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    fun getHomeScreenBooking() {}
}
