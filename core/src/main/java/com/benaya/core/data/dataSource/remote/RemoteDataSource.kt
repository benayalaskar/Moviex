package com.benaya.core.data.dataSource.remote

import android.util.Log
import com.benaya.core.data.dataSource.remote.response.DetailPopularMovieResponse
import com.benaya.core.data.dataSource.remote.response.ResultsItem
import com.benaya.core.data.dataSource.remote.retrofit.ApiResponse
import com.benaya.core.data.dataSource.remote.retrofit.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiServices){
    suspend fun getPopularMovies(): Flow<ApiResponse<List<ResultsItem?>?>> {
        return flow {
            try {
                val response = apiService.getPopularMovies()
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailPopularMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}