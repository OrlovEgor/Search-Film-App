package ru.orlovegor.search_film_app.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.utils.ResultWrapper

interface FullDescriptionRepository {
  suspend fun getFullDescriptionMovieById(movieId: Long): ResultWrapper<Movie>

  suspend fun getLocalMovies(): List<Movie>

  suspend fun insertMovie(movie: Movie): Boolean

  suspend fun removeMovie(movie: Movie): Boolean

   fun getMoviesTest(): Flow<List<Long>>

}
