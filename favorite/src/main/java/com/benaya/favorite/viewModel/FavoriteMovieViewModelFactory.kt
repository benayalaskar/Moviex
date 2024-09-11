package com.benaya.favorite.viewModel

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benaya.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> FavoriteMovieViewModel(
                movieUseCase
            ) as T

            else -> throw IllegalArgumentException("Unknown Viewmodel class ${modelClass.name}")
        }
}