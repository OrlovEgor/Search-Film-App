package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.remote.models.MovieDto

interface FullDescriptionRepository{
  suspend fun getFullDescriptionMovieById(movieId: Long): MovieDto
}
