package com.benaya.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.benaya.core.R
import com.benaya.core.databinding.ItemMovieBinding
import com.benaya.core.domain.model.Movie

class MovieAdapter(
    private var listMovie: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
        holder.itemView.findViewById<CardView>(R.id.card_view).setOnClickListener {
            onItemClickCallback.onItemClicked(listMovie[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listMovie.size

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root.context)
                .load(movie.fullPosterPath)
                .into(binding.ivPoster)

            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.tvReleaseDate.text = movie.releaseDate
            binding.tvPopularity.text = movie.popularity.toString()
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}

