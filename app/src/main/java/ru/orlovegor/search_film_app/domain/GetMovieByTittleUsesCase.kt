package ru.orlovegor.search_film_app.domain

import android.content.Context
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.data.models.Movie
import ru.orlovegor.search_film_app.data.remote.models.MovieDto
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepository
import ru.orlovegor.search_film_app.di.IoDispatcher
import javax.inject.Inject


class GetMovieByTittleUsesCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val searchMovieRepository: SearchMovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun invoke(tittle: String): PagingData<Movie> {
        return try {
            searchMovieRepository.getMovieByTittlePaging(tittle).flow
                .mapLatest { pagingData -> pagingData.map { movieDto -> movieDto.mapToMovie() } }
                .flowOn(ioDispatcher)
                .first()
        } catch (t: Throwable) {
            PagingData.empty()
        }
    }

     fun MovieDto.mapToMovie() =
        Movie(
            id = this.id,
            title = this.title,
            posterUrl = when {
                this.posterUrl == null -> ""
                this.posterUrl.poster.isNotBlank() -> this.posterUrl.poster
                else -> this.posterUrl.imgUrl
            },
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
            ageRestriction = this.ageRestriction?.age
                ?: context.resources.getString(R.string.age_restriction),
            similarMovieDto = this.similarMovieDto?.filterNotNull() ?: listOf()

        )
}
