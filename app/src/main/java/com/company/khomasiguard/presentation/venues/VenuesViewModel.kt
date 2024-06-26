package com.company.khomasiguard.presentation.venues

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
                                playgroundName = playgrounds.forEach { playground ->
                                    playground.playgroundInfo.playground.name
                                }.toString(),
                                activated = playgrounds.filter { it.playgroundInfo.playground.isBookable }
                                    .toMutableList(),
                                notActivated = playgrounds.filter { !it.playgroundInfo.playground.isBookable }
                                    .toMutableList(),
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

    fun cancel(playgroundId: Int, isActive: Boolean) {
        viewModelScope.launch {
            localGuardUseCases.getLocalGuard().collect { guardData ->
                remoteUseCases.playgroundStateUseCase(
                    token = "Bearer ${guardData.token}",
                    playgroundId = playgroundId,
                    isActive = isActive
                ).collect {
                    when (it) {
                        is DataState.Success -> {
                            _uiState.update { currentState ->
                                val updatedActivated = currentState.activated.toMutableList()
                                val updatedNotActivated = currentState.notActivated.toMutableList()

                                if (isActive) {
                                    val deactivatedPlayground =
                                        updatedNotActivated.find { playground ->
                                            playground.playgroundInfo.playground.id == playgroundId
                                        }
                                    if (deactivatedPlayground != null) {
                                        updatedNotActivated.remove(deactivatedPlayground)
                                        updatedActivated.add(deactivatedPlayground.apply {
                                            playgroundInfo.playground.isBookable = true
                                        })
                                    }
                                } else {
                                    val activatedPlayground = updatedActivated.find { playground ->
                                        playground.playgroundInfo.playground.id == playgroundId
                                    }
                                    if (activatedPlayground != null) {
                                        updatedActivated.remove(activatedPlayground)
                                        updatedNotActivated.add(activatedPlayground.apply {
                                            playgroundInfo.playground.isBookable = false
                                        })
                                    }
                                }

                                currentState.copy(
                                    activated = updatedActivated,
                                    notActivated = updatedNotActivated
                                )
                            }
                        }

                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                errorMessage = "Error updating playground state: ${it.message}"
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
