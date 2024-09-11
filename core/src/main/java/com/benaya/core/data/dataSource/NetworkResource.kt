package com.benaya.core.data.dataSource

import com.benaya.core.data.dataSource.remote.retrofit.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkResource<ResultType, RequestType> {

    private val resourceFlow: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = initiateApiCall().first()) {
            is ApiResponse.Success -> {
                emitAll(processNetworkResponse(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }

            else -> Unit
        }
    }

    protected abstract fun processNetworkResponse(data: RequestType): Flow<ResultType>

    protected abstract suspend fun initiateApiCall(): Flow<ApiResponse<RequestType>>

    fun toFlow(): Flow<Resource<ResultType>> = resourceFlow

}
