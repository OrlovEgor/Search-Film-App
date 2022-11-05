package ru.orlovegor.search_film_app.presentation.movie_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepository
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.utils.QueryType
import ru.orlovegor.search_film_app.utils.RequestQueryConstructor
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val searchMovieRepository: SearchMovieRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _year = MutableStateFlow("")
    private val _genre = MutableStateFlow("")
    private val _rating = MutableStateFlow("")

    private val _movies = MutableStateFlow(PagingData.empty<Movie>())
    val movies = _movies.asStateFlow()

    init {
        searchMovies()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun searchMovies() {
        combine(_name, _year, _genre, _rating) { flowArray ->
            arrayOf(
                RequestQueryConstructor(flowArray[0], QueryType.NAME),
                RequestQueryConstructor(flowArray[1], QueryType.YEAR),
                RequestQueryConstructor(flowArray[2], QueryType.Genre),
                RequestQueryConstructor(flowArray[3], QueryType.Rating)
            )
        }.mapLatest {
            searchMovieRepository.getMoviesPagingData(
                it[0], it[1], it[2], it[3]
            )
        }
            .cachedIn(viewModelScope)
            .onEach { _movies.emit(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }


    fun getQueryData(
        name: Flow<String>,
        year: Flow<String>,
        genre: Flow<String>,
        rating: Flow<String>
    ) {
        viewModelScope.launch {
            launch {
                name.collectLatest { _name.value = it }
            }
            launch {
                year.collectLatest { _year.value = it }
            }
            launch {
                genre.collectLatest { _genre.value = it }
            }
            launch {
                rating.collectLatest { _rating.value = it }
            }
        }
    }

}