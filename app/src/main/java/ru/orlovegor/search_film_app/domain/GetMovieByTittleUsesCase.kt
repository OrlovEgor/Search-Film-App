package ru.orlovegor.search_film_app.domain

import android.content.Context
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.data.models.Movie
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepositoryImpl
import java.util.ArrayList
import javax.inject.Inject

/*
class GetMovieByTittleUsesCase @Inject constructor(
    private val context: Context,
    private val searchMovieRepositoryImpl: SearchMovieRepositoryImpl

) {
    suspend fun invoke(tittle: String): ArrayList<Movie> {
        return searchMovieRepositoryImpl.getMovieByTittle(tittle).listMovieDto.map {
            it.mapToMovie()
        } as ArrayList<Movie>
    }

    fun MovieDto.mapToMovie() =
        Movie(
            id = this.id,
            title = this.title,
            releaseDate = releaseDate ?: context.resources.getString(R.string.empty_release_date),
            description = when {
                !this.shortDescription.isNullOrBlank() -> this.shortDescription
                !this.description.isNullOrBlank() -> this.description
                else -> context.resources.getString(R.string.not_destcriprion)
            },
            rating = when {
                this.rating.kp > 0.0 -> this.rating.kp
                else -> this.rating.imdb
            },
            posterUrl = when {
                this.posterUrl == null -> ""
                this.posterUrl.poster.isNotBlank() -> this.posterUrl.poster
                else -> this.posterUrl.imgUrl
            }

        )
}*/
