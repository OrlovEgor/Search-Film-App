package ru.orlovegor.search_film_app.data.models

import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie

interface MovieApi {

    @GET("/movie")
    suspend fun getMovieByTittle(
        @Query("search") tittle: String,
        @Query("field") params: String,
        @Query("isStrict") isStrict: Boolean
    ): List<RemoteMovie>
}
