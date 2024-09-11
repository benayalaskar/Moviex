package com.benaya.core.data.dataSource.local

import com.benaya.core.data.dataSource.local.entity.MovieEntity
import com.benaya.core.data.dataSource.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getMovie(): Flow<List<MovieEntity>> = movieDao.getMovie()

    fun getMovieById(id: Int): Flow<MovieEntity> = movieDao.getMovieById(id)

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}