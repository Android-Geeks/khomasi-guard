package com.company.khomasiguard.domain.use_case.local_guard

import com.company.khomasiguard.domain.repository.LocalGuardRepository


class GetLocalGuard(
    private val localGuardRepository: LocalGuardRepository
) {
    operator fun invoke() = localGuardRepository.getLocalGuard()
}