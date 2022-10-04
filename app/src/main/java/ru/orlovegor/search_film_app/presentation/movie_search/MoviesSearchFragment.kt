package ru.orlovegor.search_film_app.presentation.movie_search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
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
            val refreshError = state.refresh
            binding.movieProgressBarHorizontal.isVisible =
                state.append == LoadState.Loading || state.refresh == LoadState.Loading

            if (state.refresh is LoadState.Error || state.append is LoadState.Error){
                Log.d("ERROR!!!!!!!!!!!!!!!!", "${refreshError.toString()}")
                showSnack()

            }

        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initList() {
        val dividerDecorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerDecorator.setDrawable(
            resources.getDrawable(
                R.drawable.divider_drawable,
                requireContext().theme
            )
        )
        with(binding.movieRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerDecorator)

            /*TODO( " " +
                    " +
                    "обработать эти ошибки в пэйджинге и вывести пользователю снэкбар" +
                    ". добавить текст вью при пустом списке с тектстом пока ничего не найдено.")*/
        }
    }

    private fun showSnack(){
        Snackbar.make(binding.root,getString(R.string.no_connection), Snackbar.LENGTH_LONG)
            .show()
    }

    private fun initSearchView() {
        binding.movieSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val tittle = binding.movieSearchView.query.toString()
                viewModel.setQuery(tittle)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
}