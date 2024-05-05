package com.company.khomasiguard.domain.repository

import com.company.khomasiguard.navigation.Screens
import kotlinx.coroutines.flow.Flow

interface LocalGuardRepository {

    fun readAppEntry(): Flow<Screens>

    suspend fun saveIsLogin(isLogin: Boolean)
}