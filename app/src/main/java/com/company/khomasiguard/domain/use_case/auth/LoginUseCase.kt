package com.company.khomasiguard.domain.use_case.auth

import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class LoginUseCase(
    private val remoteGuardRepository: RemoteGuardRepository
) {
    suspend operator fun invoke(email: String, password: String){
        remoteGuardRepository.loginGuard(email,password)
    }

}