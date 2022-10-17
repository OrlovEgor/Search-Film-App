package ru.orlovegor.search_film_app.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.presentation.models.Movie

interface FavoriteMovieRepository {
     fun getMovies(): Flow<List<Movie>>

    suspend fun removeMovie(movie: Movie)
}