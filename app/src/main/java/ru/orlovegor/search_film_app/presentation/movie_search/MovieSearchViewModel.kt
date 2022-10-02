package ru.orlovegor.search_film_app.presentation.movie_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.search_film_app.data.models.Movie
import ru.orlovegor.search_film_app.data.models.MoviesPageSource
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import java.util.ArrayList
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val pagingSourceFactory: MoviesPageSource
) : ViewModel() {

    val movies: StateFlow<PagingData<MovieDto>> = Pager<Int, MovieDto>(
        PagingConfig(pageSize = 5)
    ) {
        pagingSourceFactory
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


}