package ru.orlovegor.search_film_app.data.models.remote_models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RemoteSearchResult(
    @Json(name = "docs")
    val listRemoteMovie: List<RemoteMovie>,
    @Json(name = "total")
    val totalResult: Int,
    @Json(name = "page")
    val currentPage: Int,
    @Json(name = "pages")
    val totalPages: Int
)

