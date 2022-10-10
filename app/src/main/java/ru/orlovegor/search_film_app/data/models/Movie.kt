package ru.orlovegor.search_film_app.data.models

import ru.orlovegor.search_film_app.data.remote.models.SimilarMovieDto

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    val description: String,
    val rating : Double,
    val ageRestriction: String,
    val similarMovieDto: List<SimilarMovieDto>

)

