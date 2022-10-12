package ru.orlovegor.search_film_app.presentation.models

import ru.orlovegor.search_film_app.data.remote.models.PosterMovie

data class SimilarMovie(
    val id: Long,
    val tittle: String,
    val imageUrl: String
)