package ru.orlovegor.search_film_app.data.models

import com.squareup.moshi.Json
import ru.orlovegor.search_film_app.data.models.remote_models.PosterMovie
import ru.orlovegor.search_film_app.data.models.remote_models.RatingMovie

data class Movie(
    val id: Long,
    val title: String,
    val releaseDate: String,
    val description: String,
    val rating : Double,
    val posterUrl: String,
)
