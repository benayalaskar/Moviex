package com.benaya.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val overview: String,
    val movieId: Int,
    val isFavorite: Boolean,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val title: String
) : Parcelable {

    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"

    val fullBackdropPath: String
        get() = "https://image.tmdb.org/t/p/w500$backdropPath"
}