package com.company.khomasiguard.domain.use_case.remote_guard

import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class PlaygroundStateUseCase(
   private val remoteGuardRepository: RemoteGuardRepository
) {
    suspend operator fun invoke(token: String,playgroundId:Int,isActive:Boolean)=
       remoteGuardRepository.playgroundState(token,playgroundId,isActive)
}