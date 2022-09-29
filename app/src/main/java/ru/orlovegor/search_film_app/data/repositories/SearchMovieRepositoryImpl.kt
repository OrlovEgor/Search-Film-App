package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.models.MovieApi
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : SearchMovieRepository {

    override suspend fun getMovieByTittle(tittle: String) = movieApi.getMovieByTittle(tittle)



}