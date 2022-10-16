package ru.orlovegor.search_film_app.presentation.movie_favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentFavoriteMoviesBinding
import ru.orlovegor.search_film_app.presentation.adapters.FavoriteMovieAdapter


@AndroidEntryPoint
class FavoriteMoviesFragment: Fragment(R.layout.fragment_favorite_movies) {

    private val binding: FragmentFavoriteMoviesBinding by viewBinding()
    private val viewModel: FavoriteMovieViewModel by viewModels()
    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteMovieAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()

        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest { movies ->
                movieAdapter.submitList(movies)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initList() {
        val dividerDecorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerDecorator.setDrawable(
            resources.getDrawable(
                R.drawable.divider_black,
                requireContext().theme
            )
        )

        with(binding.localMovieRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerDecorator)
        }
    }
}