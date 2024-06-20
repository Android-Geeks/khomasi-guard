package com.company.khomasiguard.presentation.venues

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.presentation.venues.components.VenuesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _uiState: MutableStateFlow<VenuesUiState> = MutableStateFlow(VenuesUiState())
    val uiState: StateFlow<VenuesUiState> = _uiState

    fun getGuardPlaygrounds() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.getGuardPlaygroundsUseCase(
                    token = "Bearer ${guardData.token}",
                    guardID = guardData.guardID ?: "",
                ).collect { dataState ->
                    Log.d("VenuesViewModel", "LocalGuard: $dataState")
                    when (dataState) {
                        is DataState.Success -> {
                            val playgrounds = dataState.data.playgrounds
                            _uiState.value = VenuesUiState(
                                playgroundName = playgrounds.firstOrNull()?.playgroundInfo?.playground?.name ?: "",
                                activated = playgrounds.filter { it.playgroundInfo.playground.isBookable },
                                notActivated = playgrounds.filter { !it.playgroundInfo.playground.isBookable },
                                isLoading = false
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Error fetching playgrounds: ${dataState.message}"
                            )
                        }
                        is DataState.Empty -> {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun cancel(playgroundId: Int) {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.playgroundStateUseCase(
                    token = "Bearer ${guardData.token}",
                    playgroundId = playgroundId,
                    isActive = false
                ).collect {}
            }
        }
    }
}
