package ru.orlovegor.search_film_app.presentation.movie_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepositoryImpl
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val searchMovieRepositoryImpl: SearchMovieRepositoryImpl
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    val movies: StateFlow<PagingData<MovieDto>> = query
        .map(::getMovies)
        .flatMapLatest { pager -> pager.flow }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    private fun getMovies(tittle: String): Pager<Int, MovieDto> {
        return searchMovieRepositoryImpl.getMovieByTittlePaging(tittle)
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

}