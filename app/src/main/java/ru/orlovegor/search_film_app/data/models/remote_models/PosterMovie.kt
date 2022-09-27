package ru.orlovegor.search_film_app.data.models.remote_models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PosterMovie(
    @Json(name = "url")
    val imgUrl: String = "",
    @Json(name = "previewUrl")
    val poster: String = ""
)
