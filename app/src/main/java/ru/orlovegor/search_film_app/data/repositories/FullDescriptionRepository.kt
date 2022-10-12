package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.utils.ResultWrapper
import ru.orlovegor.search_film_app.presentation.models.Movie

interface FullDescriptionRepository{
  suspend fun getFullDescriptionMovieById(movieId: Long): ResultWrapper<Movie>

}
