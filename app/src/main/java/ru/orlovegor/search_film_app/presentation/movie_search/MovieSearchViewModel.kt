package ru.orlovegor.search_film_app.presentation.movie_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import ru.orlovegor.search_film_app.domain.GetMovieByTittleUsesCase
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val usesCase: GetMovieByTittleUsesCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<MovieDto>> = query
        .mapLatest { usesCase.invoke(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

}