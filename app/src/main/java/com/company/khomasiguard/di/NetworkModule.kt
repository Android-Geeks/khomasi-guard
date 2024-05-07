package com.company.khomasiguard.di


import com.company.khomasiguard.data.data_source.remote.RetrofitService
import com.company.khomasiguard.data.repository.RemoteGuardRepositoryImpl
import com.company.khomasiguard.domain.repository.RemoteGuardRepository
import com.company.khomasiguard.domain.use_case.auth.AuthUseCase
import com.company.khomasiguard.domain.use_case.remote_guard.RemoteUseCases
import com.company.khomasiguard.domain.use_case.remote_guard.GetGuardBookingsUseCase
import com.company.khomasiguard.domain.use_case.remote_guard.GetGuardPlaygroundsUseCase
import com.company.khomasiguard.domain.use_case.auth.LoginUseCase
import com.company.khomasiguard.domain.use_case.remote_guard.RatePlayerUseCase
import com.company.khomasiguard.util.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
            connectTimeout(1, TimeUnit.MINUTES) // connect timeout
            readTimeout(30, TimeUnit.SECONDS) // socket timeout
            writeTimeout(15, TimeUnit.SECONDS) // write timeout
        }.build()
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteGuardRepository(
        retrofitService: RetrofitService
    ): RemoteGuardRepository = RemoteGuardRepositoryImpl(retrofitService)
    @Provides
    @Singleton
    fun provideAuthUseCases(
        remoteGuardRepository: RemoteGuardRepository
    ): AuthUseCase = AuthUseCase(
        LoginUseCase(remoteGuardRepository)
    )
    @Provides
    @Singleton
    fun provideRemoteUseCases(
        remoteGuardRepository: RemoteGuardRepository
    ): RemoteUseCases = RemoteUseCases(
        getGuardBookingsUseCase = GetGuardBookingsUseCase(remoteGuardRepository),
        getGuardPlaygroundsUseCase = GetGuardPlaygroundsUseCase(remoteGuardRepository),
        ratePlayerUseCase = RatePlayerUseCase(remoteGuardRepository)

    )

}