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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val remoteUseCases: RemoteUseCases
) : ViewModel()
{
    private val _uiState: MutableStateFlow<VenuesUiState> = MutableStateFlow(VenuesUiState())
    val uiState: StateFlow<VenuesUiState> = _uiState

    fun getGuardPlaygrounds() {
        viewModelScope.launch {
            val localGuard = localGuardUseCases.getLocalGuard().first()
            val token = localGuard.token ?: ""
            val guardID = localGuard.guardID ?: ""
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