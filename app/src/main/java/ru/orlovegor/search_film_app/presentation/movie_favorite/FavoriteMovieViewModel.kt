package ru.orlovegor.search_film_app.presentation.movie_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.orlovegor.search_film_app.data.repositories.FavoriteMovieRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {
    val movies = favoriteRepository.getMovies().shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)
}