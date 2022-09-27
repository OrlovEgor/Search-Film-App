package ru.orlovegor.search_film_app.data.models

import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteSearchResult

interface MovieApi {

    @GET("/movie?field=name&limit=20&sortField[]=votes.kp&sortField[]=premiere.world&sortType[]=-1&sortType[]=-1")
    suspend fun getMovieByTittle(
        @Query("search") tittle: String,
    ): RemoteSearchResult

    //https://api.kinopoisk.dev/movie?token=&search=%D0%9C%D0%B0&page=1&field=name&limit=20&sortField[]=votes.kp&sortField[]=premiere.world&sortType[]=-1&sortType[]=-1
}
