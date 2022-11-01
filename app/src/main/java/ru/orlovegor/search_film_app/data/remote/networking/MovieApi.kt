package ru.orlovegor.search_film_app.data.remote.networking

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.*
import ru.orlovegor.search_film_app.data.remote.models.MovieDto
import ru.orlovegor.search_film_app.data.remote.models.RemoteSearchResult

interface MovieApi {

    @GET("/movie?field=id")
    suspend fun getFullDescriptionMovie(
       @Query("search") id:Long
    ): MovieDto

    @GET("/movie?")
    suspend fun getMovies(
        @QueryMap tittle: Map<String, String>,
        @QueryMap year: Map<String, String>,
        @QueryMap genre: Map<String, String>,
        @QueryMap rating: Map<String, String>,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 10, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("isStrict") isStrict: Boolean = false,
        @Query("sortField[]") sortFieldKp: String = "votes.kp",
        @Query("sortField[]") sortFieldPremiere: String = "premiere.world",
        @Query("sortType[]") sortType:String = "-1",
        @Query("sortType[]") sortTypeR: String = "-1"
    ):Response<RemoteSearchResult>

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 30
    }

}

