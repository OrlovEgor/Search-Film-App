package ru.orlovegor.search_film_app.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSearchResult(
    @Json(name = "docs")
    val listMovieDto: List<MovieDto>,
    @Json(name = "total")
    val totalResult: Int,
    @Json(name = "page")
    val currentPage: Int,
    @Json(name = "pages")
    val totalPages: Int
)

