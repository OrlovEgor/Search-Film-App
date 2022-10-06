package ru.orlovegor.search_film_app.data.remote

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovegor.search_film_app.data.remote.models.RemoteSearchResult

interface MovieApi {

    @GET("/movie?field=name&limit=20&sortField[]=votes.kp&sortField[]=premiere.world&sortType[]=-1&sortType[]=-1")
    suspend fun getMovieByTittle(
        @Query("search") tittle: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("isStrict") isStrict: Boolean = false
    ): Response<RemoteSearchResult>

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 15
    }
}

