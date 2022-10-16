package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.orlovegor.search_film_app.data.local.MovieDao
import ru.orlovegor.search_film_app.data.paging.MoviesPageSource
import ru.orlovegor.search_film_app.data.remote.MovieApi
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import ru.orlovegor.search_film_app.presentation.models.mapToMovieLocal
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context,
    private val movieDao: MovieDao
) : SearchMovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMovieByTittlePagingData(tittle: String): PagingData<Movie> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { MoviesPageSource(movieApi, tittle) }
        ).flow
            .map { pagingData -> pagingData.map { movieDto -> movieDto.mapToMovie(context) } }
            .mapLatest { pagingDataMovie ->
                pagingDataMovie.map { movie -> checkMovieForFavorites(getLocalMovies(), movie) }
            }
            .flowOn(Dispatchers.IO)
            .first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLocalMovies(): List<Movie> {
        return try {
            movieDao.getMovies()
                .mapLatest { list -> list.map { localMovie -> localMovie.mapToMovie() } }
                .first()
        } catch (t: Throwable) {
            Log.d("DATABASE", "getLocalMovie message: ${t.message}")
            listOf<Movie>()
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

    private fun checkMovieForFavorites(local: List<Movie>, movie: Movie): Movie {
        val isFavorite = local.map { it.id }.contains(movie.id)
        return if (isFavorite) {
            movie.copy(isFavorite = true)
        } else {
            movie
        }
    }
}
