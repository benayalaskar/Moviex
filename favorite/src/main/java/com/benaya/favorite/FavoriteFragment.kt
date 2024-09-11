package com.benaya.favorite

import androidx.fragment.app.Fragment
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benaya.core.domain.model.Movie
import com.benaya.core.presentation.MovieAdapter
import com.benaya.favorite.databinding.FragmentFavoriteBinding
import com.benaya.favorite.di.DaggerFavoriteComponent
import com.benaya.favorite.viewModel.FavoriteMovieViewModel
import com.benaya.favorite.viewModel.FavoriteMovieViewModelFactory
import com.benaya.moviex.di.FavoriteModuleDependency

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteMovieViewModelFactory
    private val favoriteViewModel: FavoriteMovieViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependency::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.setHasFixedSize(true)

        favoriteViewModel.getFavoritePopularMovie().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.rvMovie.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.rvMovie.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
                setAdapter(it)
            }
        }
    }

    private fun setAdapter(it: List<Movie>) {
        val adapter = MovieAdapter(it)
        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
                    data.movieId
                )
                findNavController().navigate(action)
            }
        })
        binding.rvMovie.adapter = adapter
    }
}