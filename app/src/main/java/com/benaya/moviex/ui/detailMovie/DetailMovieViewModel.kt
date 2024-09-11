package com.benaya.moviex.ui.detailMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.benaya.core.domain.model.Movie
import com.benaya.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    fun getDetailPopularMovie(id: Int) = movieUseCase.getDetailMovie(id).asLiveData()

    fun getPopularMovieById(id: Int) = movieUseCase.getMovieById(id).asLiveData()

    fun setFavoriteMovie(movie: Movie, state: Boolean) = movieUseCase.setFavoriteMovie(movie, state)
}