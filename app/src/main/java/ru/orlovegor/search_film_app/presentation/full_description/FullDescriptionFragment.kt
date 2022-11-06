package ru.orlovegor.search_film_app.presentation.full_description

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentFullDescriptionBinding
import ru.orlovegor.search_film_app.presentation.adapters.FullDescriptionMovieAdapter

@AndroidEntryPoint
class FullDescriptionFragment : Fragment(R.layout.fragment_full_description) {

    private val binding: FragmentFullDescriptionBinding by viewBinding()
    private val viewModel: FullDescriptionViewModel by viewModels()
    private val fullDescriptionMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FullDescriptionMovieAdapter(
            { movieId -> viewModel.loadSimilarMovieFullDescription(movieId) },
            { movie, isFavorite -> viewModel.isFavoriteStateChange(movie, isFavorite) },
            requireContext()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeViewModelState()
    }

    private fun observeViewModelState() {
        lifecycleScope.launchWhenStarted {
            viewModel.isProgress.collectLatest {
                binding.progressBarHorizontal.isVisible = it
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.movie.collectLatest { movie ->
                scrollRwToTopAfterLoadData()
                fullDescriptionMovieAdapter.setMovies(movie)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest { errorMessage ->
                makeSnack(errorMessage)
            }
        }
    }

    private fun makeSnack(@StringRes message: Int) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun scrollRwToTopAfterLoadData() {
        if (fullDescriptionMovieAdapter.itemCount >= 1) {
            binding.fullDescriptionRecycler.smoothScrollToPosition(ADAPTER_ITEM_POSITION_0)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initList() {
        with(binding.fullDescriptionRecycler) {
            adapter = fullDescriptionMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        const val ADAPTER_ITEM_POSITION_0 = 0
    }
}