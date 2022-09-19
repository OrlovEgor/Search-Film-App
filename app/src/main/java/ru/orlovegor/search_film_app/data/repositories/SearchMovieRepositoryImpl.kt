package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.models.MovieApi
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : SearchMovieRepository {

    override suspend fun getMovie(tittle: String, params: String,isStrict: Boolean) = movieApi.getMovieByTittle(tittle, params, isStrict)

}