package com.company.khomasiguard.presentation.venues

import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.playground.PlaygroundsResponse
import com.company.khomasiguard.presentation.venues.components.VenuesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockVenuesViewModel :ViewModel(){
    private val _venuesState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val venuesState: StateFlow<DataState<PlaygroundsResponse>> = _venuesState

    private val _uiState: MutableStateFlow<VenuesUiState> = MutableStateFlow(VenuesUiState())
    val uiState: StateFlow<VenuesUiState> = _uiState
    fun getGuardPlaygrounds(){}
    fun cancel(playgroundId: Int,isActive:Boolean){}

}