package ru.orlovegor.search_film_app.presentation.full_description

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.data.repositories.FullDescriptionRepository
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.SimilarMovie
import ru.orlovegor.search_film_app.utils.ResultWrapper
import javax.inject.Inject

@HiltViewModel
class FullDescriptionViewModel @Inject constructor(
   private val stateNavArgs: SavedStateHandle,
    private val fullDescriptionRepository: FullDescriptionRepository

) : ViewModel() {

    private val _movie = MutableSharedFlow<Movie>()
    private val _movieId = MutableSharedFlow<Long>()
    private val _similarMovies = MutableSharedFlow<List<SimilarMovie>>()
    private val _isProgress = MutableStateFlow(false)
    private val _error = MutableSharedFlow<Int>()

    val movie = _movie.asSharedFlow()
    val isProgress = _isProgress.asStateFlow()
    val error = _error.asSharedFlow()

    init {
        viewModelScope.launch {
            load()
        }
        viewModelScope.launch {
            _movieId.emit(stateNavArgs.get<Long>(NAV_ARG_KEY_MOVIE_ID)!!)
        }
    }

    private suspend fun load() {
            _movieId
                .onEach {
                    _isProgress.value = true
                    when (val data = fullDescriptionRepository.getFullDescriptionMovieById(it!!)) {
                        is ResultWrapper.Success -> {
                            _movie.emit(data.value)
                            _similarMovies.emit(data.value.similarMovie)
                        }
                        is ResultWrapper.Error -> {
                            _error.emit(R.string.connection_error)
                        }
                        is ResultWrapper.NetworkError -> {
                            _error.emit(R.string.no_internet_connection)
                        }
                    }
                }
                .onEach { _isProgress.value = false }
                .launchIn(viewModelScope)
        }


    fun loadSimilarMovieFullDescription(movieId: Long) {
        viewModelScope.launch {
            _movieId.emit(movieId)
        }
    }
    companion object{
        const val  NAV_ARG_KEY_MOVIE_ID = "movieId"
    }
}