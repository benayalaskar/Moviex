package com.benaya.core.domain.usecase

import com.benaya.core.data.dataSource.Resource
import com.benaya.core.domain.model.Movie
import com.benaya.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getPopularMovies() = movieRepository.getMovies()

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> = movieRepository.getDetailMovie(id)

    override fun getMovieById(id: Int): Flow<Movie> = movieRepository.getMovieById(id)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

}