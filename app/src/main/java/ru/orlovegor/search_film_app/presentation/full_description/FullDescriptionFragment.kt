package ru.orlovegor.search_film_app.presentation.full_description

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentFullDescriptionBinding

@AndroidEntryPoint
class FullDescriptionFragment : Fragment(R.layout.fragment_full_description) {

    private val binding: FragmentFullDescriptionBinding by viewBinding()
    private val viewModel: FullDescriptionViewModel by viewModels()
    private val fullDescriptionMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FullDescriptionMovieAdapter { movieId -> viewModel.loadSimilarMovieFullDescription(movieId) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModelState()
     TODO("добавить декораток к похожим фильмам. привестив  порядок адаптеры" +
             "вынести в констануту нориер элемента для скролла, обработать ошибки, обработать состояние загрузки," +
             "добавить анимацию при навигаци на данный фрагмент. добавить анимацию на добавление нового айтема в список" +
             "во вью модели вынести муфи айди в костанту отрефакторить все что есть ")
    }



    private fun observeViewModelState() {
        /* lifecycleScope.launchWhenStarted {
             viewModel.isProgress.collect {
                 binding.progressCircular.isVisible = it
             }

         }*/
        lifecycleScope.launchWhenStarted {
            viewModel.movie.collectLatest { movie ->
                scrollRwToTopAfterLoadData()
                fullDescriptionMovieAdapter.setMovies(listOf(movie))
            }
        }
        /*lifecycleScope.launchWhenStarted {
            viewModel.similarMovie.collectLatest { listSimilarMovie ->
                similarMovieAdapter.submitList(listSimilarMovie)
            }
        }*/
    }

    private fun scrollRwToTopAfterLoadData() {
        if (fullDescriptionMovieAdapter.itemCount >= 1) {
            binding.fullDescriptionRecycler.smoothScrollToPosition(0)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initList() {
        with(binding.fullDescriptionRecycler) {
            adapter = fullDescriptionMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}