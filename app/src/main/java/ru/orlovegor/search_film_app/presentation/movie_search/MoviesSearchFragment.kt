package ru.orlovegor.search_film_app.presentation.movie_search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentSearchMoviesBinding
import ru.orlovegor.search_film_app.presentation.adapters.MovieAdapter
import ru.orlovegor.search_film_app.utils.*
import java.util.*

@AndroidEntryPoint
class MoviesSearchFragment : Fragment(R.layout.fragment_search_movies) {

    private val binding: FragmentSearchMoviesBinding by viewBinding()
    private val viewModel: MovieSearchViewModel by viewModels()

    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieAdapter(requireContext()) { movieId ->
            findNavController().navigate(
                MoviesSearchFragmentDirections.actionMoviesSearchFragmentToFullDescriptionFragment(
                    movieId
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        initDropdownMenu()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initErrorViewGroup()
        binding.expandCheckbox.setOnClickListener {
            binding.searchViewGroup.isVisible = binding.expandCheckbox.isChecked
        }
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
        sendDataToViewModel()
    }

    private fun sendDataToViewModel() {
        viewModel.getQueryData(
            binding.movieSearchView.queryTextListenerFlow(),
            binding.yearTextInput.valueChangedFlow(),
            binding.genreInput.valueGenreChangedFlow(),
            binding.ratingSlider.onTouchListenerFlow()
        )
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

        with(binding.movieRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerDecorator)
        }
    }

    private fun initErrorViewGroup() {
        binding.errorButton.setOnClickListener {
            movieAdapter.retry()
        }
        lifecycleScope.launchWhenStarted {
            movieAdapter.loadStateFlow.collect { loadState ->

                binding.movieProgressBarHorizontal.isVisible =
                    loadState.append == LoadState.Loading ||
                            loadState.refresh == LoadState.Loading || loadState.prepend == LoadState.Loading

                binding.errorViewGroup.isVisible =
                    loadState.prepend is LoadState.Error ||
                            loadState.append is LoadState.Error || loadState.refresh is LoadState.Error

                binding.emptyTextText.isVisible = movieAdapter.itemCount < 1
            }
        }
    }

    private fun initDropdownMenu() {
        /* android:minWidth="@dimen/custom_min_width"
         android:maxWidth="@dimen/custom_max_width"*/

        val itemYear = listOf("") + (CURRENT_YEAR downTo LAST_YEAR).map { it.toString() }
        val yearsAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, itemYear)
        (binding.textInputYear.editText as? AutoCompleteTextView)?.setAdapter(yearsAdapter)

        val itemGenres = Genres.values().map {
            requireContext().getString(it.displayName)
        }
        val genresAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, itemGenres)
        (binding.textInputGenre.editText as? AutoCompleteTextView)?.setAdapter(genresAdapter)
    }

    companion object {
        private const val LAST_YEAR = 1980
        private val CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR)
    }
}