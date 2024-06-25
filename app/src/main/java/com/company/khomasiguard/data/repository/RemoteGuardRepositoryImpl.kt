package com.company.khomasiguard.data.repository

import com.company.khomasiguard.data.data_source.remote.RetrofitService
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.RatingRequest
import com.company.khomasiguard.domain.repository.RemoteGuardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import retrofit2.Response

class RemoteGuardRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteGuardRepository {
    override suspend fun loginGuard(
        email: String,
        password: String
    ) = handleApi { retrofitService.loginGuard(email,password) }

    override suspend fun ratePlayer(
        token: String,
        guardRating: RatingRequest
    ) = handleApi { retrofitService.ratePlayer(token,guardRating) }

    override suspend fun getGuardPlaygrounds(
        token: String,
        guardID: String
    ) = handleApi { retrofitService.getGuardPlaygrounds(token,guardID) }
    override suspend fun getGuardBookings(
        token: String,
        guardID: String,
        dayDiff: Int
    ) = handleApi { retrofitService.getGuardBookings(token,guardID,dayDiff) }

    override suspend fun playgroundState(
        token: String,
        playgroundId: Int,
        isActive: Boolean
    )= handleApi { retrofitService.playgroundState(token,playgroundId,isActive) }


}
suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): Flow<DataState<T>> {
    return flow {
        emit(DataState.Loading)
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(DataState.Success(body))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = parseErrorBody(errorBody) ?: response.message()
                emit(DataState.Error(response.code(), message))
            }
        } catch (e: HttpException) {
            emit(DataState.Error(e.code(), e.message()))
        } catch (e: Throwable) {
            emit(DataState.Error(0, e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}

fun parseErrorBody(errorBody: String?): String? {
    return try {
        val jsonElement = Json.parseToJsonElement(errorBody ?: "")
        val jsonObject = jsonElement.jsonObject
        return jsonObject["message"]?.jsonPrimitive?.content
    } catch (e: Exception) {
        null
    }
}