package com.company.khomasiguard.domain.use_case.remote_guard

import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class GetGuardBookingsUseCase(
    private val remoteGuardRepository: RemoteGuardRepository

) {
    suspend operator fun invoke(token: String, guardID: String, dayDiff: Int) =
        remoteGuardRepository.getGuardBookings(token,guardID,dayDiff)

}