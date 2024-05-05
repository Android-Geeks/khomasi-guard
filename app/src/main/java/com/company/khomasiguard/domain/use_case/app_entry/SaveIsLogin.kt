package com.company.khomasiguard.domain.use_case.app_entry

import com.company.khomasiguard.domain.repository.LocalGuardRepository


class SaveIsLogin(
    private val localGuardRepository: LocalGuardRepository
) {
    suspend operator fun invoke(isLogin: Boolean) = localGuardRepository.saveIsLogin(isLogin)
}