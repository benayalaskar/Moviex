package com.benaya.core.data.dataSource


import com.benaya.core.data.dataSource.remote.retrofit.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkResourceHandler<ResultType, RequestType> {

    private var resourceFlow: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val localData = fetchFromDB().first()
        if (shouldFetchFromNetwork(localData)) {
            emit(Resource.Loading())
            when (val apiResponse = initiateNetworkCall().first()) {
                is ApiResponse.Success -> {
                    saveNetworkResult(apiResponse.data)
                    emitAll(fetchFromDB().map {
                        Resource.Success(it)
                    })
                }

                is ApiResponse.Empty -> {
                    emitAll(fetchFromDB().map {
                        Resource.Success(it)
                    })
                }

                is ApiResponse.Error -> {
                    onNetworkFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(fetchFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected open fun onNetworkFetchFailed() {}

    protected abstract fun fetchFromDB(): Flow<ResultType>

    protected abstract fun shouldFetchFromNetwork(data: ResultType?): Boolean

    protected abstract suspend fun initiateNetworkCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveNetworkResult(data: RequestType)

    fun getResourceFlow(): Flow<Resource<ResultType>> = resourceFlow

}