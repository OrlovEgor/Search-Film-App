package ru.orlovegor.search_film_app.presentation.models

import android.content.Context
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.data.local.models.MovieLocal
import ru.orlovegor.search_film_app.data.remote.models.MovieDto
import ru.orlovegor.search_film_app.data.remote.models.SimilarMovieDto
import kotlin.random.Random

fun MovieDto.mapToMovie(context: Context) =
    Movie(
        id = this.id ?: 0,
        title = this.title?: "",
        posterUrl = when {
            this.posterUrl == null -> ""
            this.posterUrl.poster.isNotBlank() -> this.posterUrl.poster
            else -> this.posterUrl.imgUrl
        },
        releaseDate = releaseDate ?: context.resources.getString(R.string.empty_release_date),
        description = when {
            !this.description.isNullOrBlank() -> this.description
            else -> context.resources.getString(R.string.not_destcriprion)
        },
        shortDescription = this.shortDescription
            ?: context.resources.getString(R.string.not_destcriprion),
        rating = when {
            this.rating.kp > 0.0 -> this.rating.kp
            else -> this.rating.imdb
        },
        ageRestriction = this.ageRestriction?.age
            ?: context.resources.getString(R.string.age_restriction),
        similarMovie = this.similarMovieDto?.filterNotNull()?.map { it.mapToSimilarMovie() }
            ?: listOf(),
        isFavorite = this.isFavorite

    )

fun SimilarMovieDto.mapToSimilarMovie() =
    SimilarMovie(
        id = this.id ?: Random.nextLong(),
        tittle = this.tittle ?: "",
        imageUrl = when {
            this.posterUrl == null -> ""
            this.posterUrl.poster.isNotBlank() -> this.posterUrl.poster
            else -> this.posterUrl.imgUrl
        }
    )

fun MovieLocal.mapToMovie() =
    Movie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl,
        releaseDate = this.releaseDate,
        description = this.description,
        shortDescription = this.shortDescription,
        rating = this.rating,
        ageRestriction = this.ageRestriction,
        similarMovie = listOf(),
        isFavorite = this.isFavorite
    )

fun Movie.mapToMovieLocal() =
    MovieLocal(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl,
        releaseDate = this.releaseDate,
        description = this.description,
        shortDescription = this.shortDescription,
        rating = this.rating,
        ageRestriction = this.ageRestriction,
        isFavorite = this.isFavorite
    )