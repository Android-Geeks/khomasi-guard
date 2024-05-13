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
        viewModelScope.launch() {
            remoteUseCases.getGuardPlaygroundsUseCase(
                token = "Bearer ${_localGuard.value.token ?: ""}",
                guardID = _localGuard.value.guardID ?: "",
            ).collect { dataState ->
                Log.d("TestPlayground", "LocalGuard: $dataState")
                //LocalGuard: Error(code=401, message=Unauthorized)
                if (dataState is DataState.Success) {
                    _uiState.value = _uiState.value.copy(
                        activated = dataState.data.playgrounds.filter { playground ->
                            playground.playgroundInfo.playground.isBookable
                        },notActivated = dataState.data.playgrounds.filter { playground ->
                            !playground.playgroundInfo.playground.isBookable
                        }

                    )
                }

            }

        }
        }

}