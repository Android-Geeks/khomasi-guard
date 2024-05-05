package com.company.khomasiguard.domain.use_case.app_entry

import com.company.khomasiguard.domain.repository.LocalGuardRepository
import com.company.khomasiguard.navigation.Screens
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localGuardRepository: LocalGuardRepository
) {
    operator fun invoke(): Flow<Screens> = localGuardRepository.readAppEntry()
}