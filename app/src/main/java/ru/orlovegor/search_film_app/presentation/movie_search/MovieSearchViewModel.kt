package ru.orlovegor.search_film_app.presentation.movie_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepository
import ru.orlovegor.search_film_app.presentation.models.Movie
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val searchMovieRepository: SearchMovieRepository
) : ViewModel() {

    private val _query = MutableSharedFlow<String>()
    private val query = _query.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<Movie>> = query
        .mapLatest { searchMovieRepository.getMovieByTittlePagingData(it) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setQuery(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }



}