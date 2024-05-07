package com.company.khomasiguard.domain.use_case.remote_guard


data class RemoteUseCases(
    val getGuardBookingsUseCase: GetGuardBookingsUseCase,
    val getGuardPlaygroundsUseCase: GetGuardPlaygroundsUseCase,
    val ratePlayerUseCase: RatePlayerUseCase
)
