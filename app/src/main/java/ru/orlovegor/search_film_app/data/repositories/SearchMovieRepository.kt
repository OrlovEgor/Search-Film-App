package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteSearchResult

interface SearchMovieRepository {

    suspend fun getMovie( tittle: String): RemoteSearchResult
}