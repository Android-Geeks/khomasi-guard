package com.company.khomasiguard.presentation.home

import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeMockViewModel: ViewModel() {
    val cancelState: StateFlow<DataState<MessageResponse>> = MutableStateFlow(DataState.Empty)
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
}
