//package com.company.khomasiguard.presentation.booking
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.company.khomasiguard.domain.DataState
//import com.company.khomasiguard.domain.model.booking.BookingsResponse
//import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
//import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
//import com.company.khomasiguard.util.extractDateFromTimestamp
//import com.company.khomasiguard.util.parseTimestamp
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class BookingViewModel @Inject constructor(
//    private val localGuardUseCases: LocalGuardUseCases,
//    private val remoteUseCases: RemoteUseCases
//    ) : ViewModel()
//{
//    private val _responseState: MutableStateFlow<DataState<BookingsResponse>> =
//        MutableStateFlow(DataState.Empty)
//    private val _uiState: MutableStateFlow<BookingUiState> = MutableStateFlow(BookingUiState())
//    val uiState: StateFlow<BookingUiState> = _uiState
//
//    fun getBooking(date: String) {
//        viewModelScope.launch {
//            val localGuard = localGuardUseCases.getLocalGuard().firstOrNull()
//            if (localGuard == null) {
//                Log.e("BookingViewModel", "Local guard data is null")
//                return@launch
//            }
//            val token = localGuard.token ?: ""
//            val guardID = localGuard.guardID ?: ""
//            remoteUseCases.getGuardBookingsUseCase(
//                token = "Bearer $token",
//                guardID = guardID,
//                date = extractDateFromTimestamp(parseTimestamp(date), format = "dd MMMM yyyy")
//            ).collect { dataState ->
//                _responseState.value=dataState
//                Log.d("BookingResponse", "BookingResponse: $dataState")
//                _responseState.value = dataState
//                if (dataState is DataState.Success) {
//                    dataState.data.guardBookings.forEach { guardBooking ->
//                        _uiState.value = _uiState.value.copy(
//                            guardBooking = guardBooking,
//                            bookingListNum = guardBooking.bookingsCount,
//                            bookingList = guardBooking.bookings,
//                        )
//                        guardBooking.bookings.forEach { booking ->
//                            _uiState.value = _uiState.value.copy(
//                                bookingDetails = booking,
//                            )
//                        }
//                    }
//                } else if (dataState is DataState.Error) {
//                    Log.e(
//                        "BookingError",
//                        "Error code: ${dataState.code}, message: ${dataState.message}"
//                    )
//                }
//            }
//            }
//
//            }
//        }




package com.company.khomasiguard.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.util.extractDateFromTimestamp
import com.company.khomasiguard.util.parseTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
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

    fun getBooking(date: String) {
        viewModelScope.launch {
            val localGuard = localGuardUseCases.getLocalGuard().firstOrNull()
            if (localGuard == null) {
                Log.e("BookingViewModel", "Local guard data is null")
                return@launch
            }
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
            try {
                val parsedDate = extractDateFromTimestamp(parseTimestamp(date), format = "dd MMMM yyyy")
                remoteUseCases.getGuardBookingsUseCase(
                    token = "Bearer $token",
                    guardID = guardID,
                    date = parsedDate
                ).collect { dataState ->
                    _responseState.value = dataState
                    Log.d("BookingResponse", "BookingResponse: $dataState")
                    if (dataState is DataState.Success) {
                        _uiState.value = BookingUiState(
                            bookingListNum = dataState.data.guardBookings.size,
                            bookingList = dataState.data.guardBookings.flatMap { it.bookings }
                        )
                    } else if (dataState is DataState.Error) {
                        Log.e(
                            "BookingError",
                            "Error code: ${dataState.code}, message: ${dataState.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Error in getBooking: ${e.message}", e)
            }
        }
    }
}
