package com.company.khomasiguard.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import com.company.khomasiguard.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasiguard.domain.use_case.auth.AuthUseCase
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val authUseCase: AuthUseCase,
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    private val _loginState: MutableStateFlow<DataState<GuardLoginResponse>> =
        MutableStateFlow(DataState.Empty)
    val loginState: StateFlow<DataState<GuardLoginResponse>> = _loginState
    fun login() {
        viewModelScope.launch() {
            authUseCase.loginUseCase(
                _uiState.value.email,
                _uiState.value.password
            ).collect {
                _loginState.value = it
                if (it is DataState.Success) {
                    onLoginSuccess()
                    localGuardUseCases.saveLocalGuard(
                        LocalGuard(
                            guardID = it.data.user.guardID,
                            firstName = it.data.user.firstName,
                            lastName = it.data.user.lastName,
                            email = it.data.user.email,
                            phoneNumber = it.data.user.phoneNumber,
                            ownerId = it.data.user.ownerId,
                            token = it.data.token,
                        )
                    )
                }
            }
            delay(1000)
            localGuardUseCases.getLocalGuard().collect {
                Log.d("TestDataStoreViewModel", "LocalGuard: $it")
            }
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    private fun onLoginSuccess() {
        viewModelScope.launch {
            appEntryUseCases.saveIsLogin(true)
        }
    }

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        // Use a regular expression to validate email format
        val isEmailValid = email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

        // Password should have at least 12 characters, a number, a symbol, and no spaces
        val isPasswordValid =
            password.matches(Regex("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+\$).{12,}\$"))

        return isEmailValid && isPasswordValid
    }

    fun contactUs() {
    }

    fun ourApp() {
    }
}