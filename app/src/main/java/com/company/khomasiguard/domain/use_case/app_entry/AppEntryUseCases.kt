package com.company.khomasiguard.domain.use_case.app_entry

import com.company.khomasiguard.domain.use_case.app_entry.ReadAppEntry
import com.company.khomasiguard.domain.use_case.app_entry.SaveIsLogin

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveIsLogin: SaveIsLogin
)