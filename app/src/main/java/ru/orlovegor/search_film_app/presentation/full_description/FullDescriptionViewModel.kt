package ru.orlovegor.search_film_app.presentation.full_description

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val _movieId = MutableStateFlow(stateNavArgs.get<Long>("movieId"))
    private val _similarMovies = MutableSharedFlow<List<SimilarMovie>>()
    private val _isProgress = MutableStateFlow(false)

    val movie = _movie.asSharedFlow()
    private val movieId = _movieId.asStateFlow()
    val similarMovie = _similarMovies.asSharedFlow()
    val isProgress = _isProgress.asStateFlow()

    init {
        viewModelScope.launch { load() }
    }

    private suspend fun load() {
        if (movieId.value == null) {
            return
        } else {
            movieId
                .onEach {
                    _isProgress.value = true
                    when (val data = fullDescriptionRepository.getFullDescriptionMovieById(it!!)) {
                        is ResultWrapper.Success -> {
                            _movie.emit(data.value)
                            _similarMovies.emit(data.value.similarMovie)
                        }
                        is ResultWrapper.Error -> {
                            Log.d("VM", "Error")
                        }
                        is ResultWrapper.NetworkError -> {
                            Log.d("VM", "NetworkError")
                        }
                    }
                }
                .onEach { _isProgress.value = false }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    fun loadSimilarMovieFullDescription(movieId: Long) {
        viewModelScope.launch {
            _movieId.emit(movieId)
        }
    }

}