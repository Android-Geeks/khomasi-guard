package com.company.khomasiguard.presentation.venues

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.playground.PlaygroundsResponse
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.presentation.venues.components.VenuesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel()
{
    private val _venuesState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
        val venuesState: StateFlow<DataState<PlaygroundsResponse>> = _venuesState

    private val _uiState: MutableStateFlow<VenuesUiState> = MutableStateFlow(VenuesUiState())
    val uiState: StateFlow<VenuesUiState> = _uiState
    private val _localGuard = localGuardUseCases.getLocalGuard().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalGuard()
    )
    val localGuard: StateFlow<LocalGuard> = _localGuard

    fun getGuardPlaygrounds() {
        viewModelScope.launch {
            val token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoidXNlcjI2QGV4YW1wbGUuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiR3VhcmQiLCJleHAiOjE3MTgzMDQ1OTksImlzcyI6IldlYkFQSURlbW8iLCJhdWQiOiJXZWJBUElEZW1vIn0.meTiTWIMG1vROhBANdYc__JLeOdTCEfbfa7ftnuCAKo"
            val guardID = "08b37a90-831e-49cc-9ec9-f2326e705eb9"
            // val token = _localGuard.value.token ?: ""
            // val guardID = _localGuard.value.guardID ?: ""
            if (token.isEmpty() || guardID.isEmpty()) {
                Log.e("VenuesViewModel", "Token or Guard ID is missing")
                return@launch
            }
            remoteUseCases.getGuardPlaygroundsUseCase(
                token = "Bearer $token",
                guardID = guardID,
            ).collect { dataState ->
                Log.d("TestPlayground", "LocalGuard: $dataState")
                when (dataState) {
                    is DataState.Success -> {
                        dataState.data.playgrounds.forEach { playground ->
                            _uiState.value = _uiState.value.copy(
                                playgroundName = playground.playgroundInfo.playground.name
                            )
                        }

                    _uiState.value = _uiState.value.copy(
                        activated = dataState.data.playgrounds.filter { playground ->
                            playground.playgroundInfo.playground.isBookable
                        },notActivated = dataState.data.playgrounds.filter { playground ->
                            !playground.playgroundInfo.playground.isBookable
                        },
                    )
                }

                    is DataState.Error -> {
                        Log.e("VenuesViewModel", "Error fetching playgrounds: ${dataState.message}")
                    }

                    is DataState.Empty -> {


                    }

                    is DataState.Loading -> {

                    }
                }

            }

        }
        }

}