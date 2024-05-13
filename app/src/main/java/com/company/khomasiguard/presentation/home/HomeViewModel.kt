package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.RatingRequest
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
class HomeViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
    ) : ViewModel()
{
    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    val responseState: StateFlow<DataState<BookingsResponse>> = _responseState

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    private val _localGuard = localGuardUseCases.getLocalGuard().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalGuard()
    )
    val localGuard: StateFlow<LocalGuard> = _localGuard

    fun getHomeScreenBooking() {
        viewModelScope.launch() {
            remoteUseCases.getGuardBookingsUseCase(
                token = "Bearer ${_localGuard.value.token ?: ""}",
                guardID = _localGuard.value.guardID ?: "",
                date = _uiState.value.bookingDetails.bookingTime
            ).collect{dataState->
                if (dataState is DataState.Success){
                    _uiState.value = _uiState.value.copy(
                        guardBookingList =dataState.data.guardBookings ,
                    )
                }

            }
        }
    }
    fun review(){
        viewModelScope.launch {
            remoteUseCases.ratePlayerUseCase(
                token = "Bearer ${_localGuard.value.token ?: ""}",
                guardRating = RatingRequest(
                    userEmail =_localGuard.value.email ?:"",
                    guardId = _localGuard.value.guardID ?:"",
                    ratingValue = _uiState.value.ratingValue
                )
            ).collect{

            }
        }
    }
}