package ru.orlovegor.search_film_app.data.models.remote_models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val title: String,
    @Json(name = "year")
    val releaseDate: String,
    @Json(name = "slogan")
    val description: String? = null,
    @Json(name = "rating")
    val rating : RatingMovie,
    @Json(name = "previewUrl")
    val posterUrl: PosterMovie,
    )
