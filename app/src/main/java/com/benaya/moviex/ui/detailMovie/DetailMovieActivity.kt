package com.benaya.moviex.ui.detailMovie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.benaya.core.data.dataSource.Resource
import com.benaya.core.domain.model.Movie
import com.benaya.moviex.R
import com.benaya.moviex.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val args: DetailMovieActivityArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val movieId: Int = args.movieId

        detailMovieViewModel.getDetailPopularMovie(movieId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBarDetail.visibility = View.GONE
                    resource.data?.let { setData(it) }
                }

                is Resource.Error -> TODO()
            }
        }

        binding.fabBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(movie: Movie) {
        binding.apply {
            Glide.with(this@DetailMovieActivity)
                .load(movie.fullPosterPath)
                .into(ivPosterDetail)
            tvTitleDetail.text = movie.title
            tvOverviewDetail.text = movie.overview
            tvReleaseDateDetail.text = movie.releaseDate
            tvPopularityDetail.text = movie.popularity.toString()
            tvVoteAverage.text = movie.voteAverage.toString()

            detailMovieViewModel.getPopularMovieById(movie.movieId)
                .observe(this@DetailMovieActivity) { movieEntity ->
                    statusFavorite = movieEntity.isFavorite
                    setStatusFavorite(statusFavorite)
                }

            fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailMovieViewModel.setFavoriteMovie(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }


    private fun setStatusFavorite(isFavorite: Boolean) {
        val favoriteIcon = if (isFavorite) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_black_24
        }
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, favoriteIcon))
        binding.fabFavorite.imageTintList =
            ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
    }
}


