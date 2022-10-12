package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import ru.orlovegor.search_film_app.utils.ResultWrapper
import ru.orlovegor.search_film_app.data.remote.MovieApi
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import ru.orlovegor.search_film_app.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject

class FullDescriptionRepositoryImpl@Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context
) : FullDescriptionRepository {
    override suspend fun getFullDescriptionMovieById(movieId: Long): ResultWrapper<Movie> =
        try {  safeApiCall {  movieApi.getFullDescriptionMovie(movieId).mapToMovie(context)}
        } catch (i: HttpException) {
            ResultWrapper.Error(i)
        } catch (i: IOException) {
            ResultWrapper.NetworkError
        }

}


