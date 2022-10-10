package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.remote.MovieApi
import ru.orlovegor.search_film_app.data.remote.models.MovieDto
import javax.inject.Inject

class FullDescriptionRepositoryImpl@Inject constructor(
    private val  movieApi: MovieApi
) : FullDescriptionRepository {
    override suspend fun getFullDescriptionMovieById(movieId: Long): MovieDto {
       return  movieApi.getFullDescriptionMovie(movieId)
    }

}