package com.company.khomasiguard.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockLoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    private val _loginState: MutableStateFlow<DataState<GuardLoginResponse>> =
        MutableStateFlow(DataState.Empty)
    val loginState: StateFlow<DataState<GuardLoginResponse>> = _loginState
    fun updatePassword(newPassword: String) {
    }

    fun updateEmail(newEmail: String) {

    }

    fun login() {
    }

    fun contactUs() {
    }

    fun ourApp() {
    }

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return true
    }
}
