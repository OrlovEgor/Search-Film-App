package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.orlovegor.search_film_app.data.remote.MovieApi
import ru.orlovegor.search_film_app.di.IoDispatcher
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import ru.orlovegor.search_film_app.utils.ResultWrapper
import ru.orlovegor.search_film_app.utils.safeApiCall
import javax.inject.Inject

class FullDescriptionRepositoryImpl@Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FullDescriptionRepository {
    override suspend fun getFullDescriptionMovieById(movieId: Long): ResultWrapper<Movie> =
        withContext(dispatcher) {
            safeApiCall { movieApi.getFullDescriptionMovie(movieId).mapToMovie(context) }
        }
}


