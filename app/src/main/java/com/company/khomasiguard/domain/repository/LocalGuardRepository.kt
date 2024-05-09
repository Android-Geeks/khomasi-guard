package com.company.khomasiguard.domain.repository

import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.navigation.Screens
import kotlinx.coroutines.flow.Flow

interface LocalGuardRepository {
    fun getLocalGuard(): Flow<LocalGuard>
    suspend fun saveLocalGuard(localUser: LocalGuard)
    fun readAppEntry(): Flow<Screens>
    suspend fun saveIsLogin(isLogin: Boolean)
}