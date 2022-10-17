package ru.orlovegor.search_film_app.presentation.models

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    val description: String,
    val shortDescription: String,
    val rating : Double,
    val ageRestriction: String,
    val similarMovie: List<SimilarMovie>,
    var isFavorite:Boolean
)

