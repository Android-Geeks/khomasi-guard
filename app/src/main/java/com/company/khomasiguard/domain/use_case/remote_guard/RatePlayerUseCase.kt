package com.company.khomasiguard.domain.use_case.remote_guard

import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class RatePlayerUseCase(
    private val remoteGuardRepository: RemoteGuardRepository

) {
    suspend operator fun invoke(token: String, guardRating: RatingRequest){
        remoteGuardRepository.ratePlayer(token,guardRating)
    }
}