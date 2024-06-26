package com.company.khomasiguard.di

import android.app.Application
import com.company.khomasiguard.data.repository.LocalGuardRepositoryImpl
import com.company.khomasiguard.domain.repository.LocalGuardRepository
import com.company.khomasiguard.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasiguard.domain.use_case.app_entry.ReadAppEntry
import com.company.khomasiguard.domain.use_case.app_entry.SaveIsLogin
import com.company.khomasiguard.domain.use_case.local_guard.GetLocalGuard
import com.company.khomasiguard.domain.use_case.local_guard.LocalGuardUseCases
import com.company.khomasiguard.domain.use_case.local_guard.SaveLocalGuard
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideLocalGuardRepository(
        application: Application
    ): LocalGuardRepository = LocalGuardRepositoryImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localGuardRepository: LocalGuardRepository
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localGuardRepository),
        saveIsLogin = SaveIsLogin(localGuardRepository)
    )

    @Provides
    @Singleton
    fun provideLocalGuardUseCases(
        localGuardRepository: LocalGuardRepository
    ): LocalGuardUseCases = LocalGuardUseCases(
        getLocalGuard = GetLocalGuard(localGuardRepository),
        saveLocalGuard = SaveLocalGuard(localGuardRepository)
    )
}