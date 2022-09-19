package ru.orlovegor.search_film_app.data.models.remote_models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingMovie(
    @Json(name = "kp")
    val kp : Double,
    @Json(name = "imdb")
    val imdb: Double
)
