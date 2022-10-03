package ru.orlovegor.search_film_app.presentation.movie_search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentSearchMoviesBinding

@AndroidEntryPoint
class MoviesSearchFragment : Fragment(R.layout.fragment_search_movies) {

    private val binding: FragmentSearchMoviesBinding by viewBinding()
    private val viewModel: MovieSearchViewModel by viewModels()

    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
        initList()
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest(
                movieAdapter::submitData
            )

        }
        initSearchView()
        movieAdapter.addLoadStateListener { state ->
            binding.movieProgressBarHorizontal.isVisible =
                state.append == LoadState.Loading || state.refresh == LoadState.Loading
        }
    }

    private fun initList() {
        with(binding.movieRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun initSearchView() {
        binding.movieSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val tittle = binding.movieSearchView.query.toString()
                viewModel.setQuery(tittle)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }
}