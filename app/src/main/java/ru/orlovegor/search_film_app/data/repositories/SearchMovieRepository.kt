package ru.orlovegor.search_film_app.data.repositories

import retrofit2.Response
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteSearchResult


interface SearchMovieRepository {

    suspend fun getMovieByTittle(tittle: String): Response<RemoteSearchResult>
}