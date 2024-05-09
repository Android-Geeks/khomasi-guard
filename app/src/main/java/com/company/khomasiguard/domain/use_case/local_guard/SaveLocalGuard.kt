package com.company.khomasiguard.domain.use_case.local_guard

import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.repository.LocalGuardRepository

class SaveLocalGuard(
    private val localGuardRepository: LocalGuardRepository
) {
    suspend operator fun invoke(localGuard: LocalGuard) = localGuardRepository.saveLocalGuard(localGuard)
}