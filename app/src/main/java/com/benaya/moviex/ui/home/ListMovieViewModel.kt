package com.benaya.moviex.ui.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModel
import com.benaya.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getPopularMovies().asLiveData()
}