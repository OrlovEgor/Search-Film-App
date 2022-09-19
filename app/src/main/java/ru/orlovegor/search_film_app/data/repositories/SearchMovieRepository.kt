package ru.orlovegor.search_film_app.data.repositories

import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie

interface SearchMovieRepository {

    suspend fun getMovie( tittle: String, params: String,isStrict: Boolean): List<RemoteMovie>
}