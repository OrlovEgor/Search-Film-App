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
import ru.orlovegor.search_film_app.utils.ResultWrapper
import javax.inject.Inject

@HiltViewModel
class FullDescriptionViewModel @Inject constructor(
    private val stateNavArgs: SavedStateHandle,
    private val fullDescriptionRepository: FullDescriptionRepository

) : ViewModel() {

    private val _movie = MutableSharedFlow<Movie>()
    private val _movieId = MutableSharedFlow<Long>()
    private val _isProgress = MutableStateFlow(false)
    private val _snackText = MutableSharedFlow<Int>()

    val movie = _movie.asSharedFlow()
    val isProgress = _isProgress.asStateFlow()
    val error = _snackText.asSharedFlow()

    init {
        load()
        viewModelScope.launch {
            stateNavArgs.get<Long>(NAV_ARG_KEY_MOVIE_ID)?.let { _movieId.emit(it) }
        }
    }

    private fun load() {
        _movieId
            .onEach {
                _isProgress.value = true
                when (val data = fullDescriptionRepository.getFullDescriptionMovieById(it)) {
                    is ResultWrapper.Success -> {
                        _movie.emit(data.value)
                    }
                    is ResultWrapper.Error -> {
                        _snackText.emit(R.string.connection_error)
                    }
                    is ResultWrapper.NetworkError -> {
                        _snackText.emit(R.string.no_internet_connection)
                    }
                }
            }
            .onEach { _isProgress.value = false }
            .launchIn(viewModelScope)
    }

    fun isFavoriteHandleState(movie: Movie, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                val isInsert = fullDescriptionRepository.insertMovie(movie)
                errorDatabaseText(isInsert)
            } else {
                val isDelete = fullDescriptionRepository.removeMovie(movie)
                errorDatabaseText(isDelete)
            }
        }
    }

    fun loadSimilarMovieFullDescription(movieId: Long) {
        viewModelScope.launch {
            _movieId.emit(movieId)
        }
    }

    private suspend fun errorDatabaseText(isSuccess: Boolean) {
        if (!isSuccess) _snackText.emit(R.string.error)
    }

    companion object {
        const val NAV_ARG_KEY_MOVIE_ID = "movieId"
    }
}