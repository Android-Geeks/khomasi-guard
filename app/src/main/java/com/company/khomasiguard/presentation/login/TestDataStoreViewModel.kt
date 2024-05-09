package com.company.khomasiguard.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.use_case.auth.AuthUseCase
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDataStoreViewModel @Inject constructor(
    private val localGuardUseCases: LocalGuardUseCases,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    init {
        viewModelScope.launch(IO) {
            authUseCase.loginUseCase("user26@example.com", "string26").collect {
                if (it is DataState.Success) {
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
}