package ru.orlovegor.search_film_app.presentation.full_description

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private val _movie = MutableStateFlow<List<Movie>>(listOf())
    private val _movieId = MutableSharedFlow<Long>()
    private val _isProgress = MutableStateFlow(false)
    private val _snackText = MutableSharedFlow<Int>()
    private val _movieFavorite = fullDescriptionRepository.getMoviesTest()

    val movie = _movie.asStateFlow()
    val isProgress = _isProgress.asStateFlow()
    val error = _snackText.asSharedFlow()

    init {

        // _movieFavorite.onEach { Log.d("TEST", "startViewModel init ") }.launchIn(viewModelScope)
        load()
        viewModelScope.launch {
            stateNavArgs.get<Long>(NAV_ARG_KEY_MOVIE_ID)?.let { _movieId.emit(it) }
        }

        viewModelScope.launch {
            // checkFavorite()
            ggg()
        }


    }

    suspend fun ggg() {
        _movieFavorite.onEach { Log.d("TEST", "startViewModel  start ggg") }
            .onEach {


            }

            //.onEach { _movie.emit(emptyList()) }
            .onEach {
                val id = stateNavArgs.get<Long>(NAV_ARG_KEY_MOVIE_ID)
                Log.d("TEST", "startViewModel111  value = ${movie.value}")
                if (it.map { movies -> movies.id }.contains(id)) {
                    Log.d("TEST", "startViewModel  True")

                    _movie.value = listOf( it.find { it.id == id }!!.copy(isFavorite = true))
                } else {

                }
            }
            .launchIn(viewModelScope)
    }

    private fun load() {
        _movieId
            .onEach {
                _isProgress.value = true
                when (val data = fullDescriptionRepository.getFullDescriptionMovieById(it)) {
                    is ResultWrapper.Success -> {
                        _movie.emit(listOf(data.value))
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

    suspend fun checkFavorite(): Job {
        Log.d("TEST", "startViewModel checkFavorite")
        val movieId = _movieId.map { it }
            .first()

        return _movieFavorite
            .onEach {
                Log.d("TEST", "startViewModel Favorite Flow")
            }
            .onEach { moviesFavorite ->
                if (moviesFavorite.map { movie -> movie.id }.contains(movieId)) {
                    _movie.emit(listOf(_movie.value.first().copy(isFavorite = false)))
                } else _movie.emit(listOf(_movie.value.first().copy(isFavorite = false)))
            }.launchIn(viewModelScope)

        /* val iss = _movieFavorite.map {
                moviesFavorite -> moviesFavorite.map { movie -> movie.id }.contains(movieId) }*/
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

    override fun onCleared() {
        super.onCleared()
        Log.d("FULL", "DESTROY")
    }

    companion object {
        const val NAV_ARG_KEY_MOVIE_ID = "movieId"
    }
}