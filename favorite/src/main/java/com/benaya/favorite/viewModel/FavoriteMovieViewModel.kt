package com.benaya.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.benaya.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getFavoritePopularMovie() = movieUseCase.getFavoriteMovie().asLiveData()
}