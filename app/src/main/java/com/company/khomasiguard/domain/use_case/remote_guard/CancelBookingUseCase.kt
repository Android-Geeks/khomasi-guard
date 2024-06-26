package com.company.khomasiguard.domain.use_case.remote_guard

import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class CancelBookingUseCase (
    private val remoteGuardRepository : RemoteGuardRepository

){
    suspend operator fun invoke( token: String,bookingId: Int,isUser: Boolean)=
        remoteGuardRepository.cancelBooking(token,bookingId,isUser)
}