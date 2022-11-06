package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.orlovegor.search_film_app.data.local.MovieDao
import ru.orlovegor.search_film_app.data.remote.networking.MovieApi
import ru.orlovegor.search_film_app.di.IoDispatcher
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import ru.orlovegor.search_film_app.presentation.models.mapToMovieLocal
import ru.orlovegor.search_film_app.utils.ResultWrapper
import ru.orlovegor.search_film_app.utils.safeApiCall
import javax.inject.Inject

class FullDescriptionRepositoryImpl@Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FullDescriptionRepository {
    override suspend fun getFullDescriptionMovieById(movieId: Long): ResultWrapper<Movie> =
        withContext(dispatcher) {
            safeApiCall {
                    movieApi.getFullDescriptionMovie(movieId).mapToMovie(context)
            }
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLocalMovies(): List<Movie> {
        return try {
            movieDao.getMovies()
                .mapLatest { list -> list.map { localMovie -> localMovie.mapToMovie() } }
                .first()
        } catch (t: Throwable) {
            Log.d("DATABASE", "getLocalMovie message: ${t.message}")
            listOf()
        }
    }

    override suspend fun insertMovie(movie: Movie): Boolean {
        return try {
            movieDao.insertMovie(movie.mapToMovieLocal())
            true
        } catch (t: Throwable) {
            Log.d("DATABASE", "insertMovie message: ${t.message}")
            false
        }
    }

    override suspend fun removeMovie(movie: Movie): Boolean {
        return try {
            movieDao.deleteMovie(movie.mapToMovieLocal())
            true
        } catch (t: Throwable) {
            Log.d("DATABASE", "removeMovie message: ${t.message}")
            false
        }
    }

    override  fun getMoviesTest(): Flow<List<Long>> = movieDao.getMoviesId()

    }




