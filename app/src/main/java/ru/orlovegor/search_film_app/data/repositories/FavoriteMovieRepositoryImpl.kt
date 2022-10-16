package ru.orlovegor.search_film_app.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.orlovegor.search_film_app.data.local.MovieDao
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : FavoriteMovieRepository {
    override fun getMovies(): Flow<List<Movie>> =
        movieDao.getMovies().map { list -> list.map { movieLocal -> movieLocal.mapToMovie() } }
}