package com.company.khomasiguard.data.repository

import com.company.khomasiguard.data.data_source.remote.RetrofitService
import com.company.khomasiguard.domain.repository.RemoteGuardRepository

class RemoteGuardRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteGuardRepository {

}