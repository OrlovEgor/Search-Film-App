package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.PagingData
import ru.orlovegor.search_film_app.data.local.models.MovieLocal
import ru.orlovegor.search_film_app.presentation.models.Movie
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities


interface SearchMovieRepository {

  suspend fun getMovieByTittlePagingData(tittle: String): PagingData<Movie>

  suspend fun insertMovie(movie: Movie): Boolean

  suspend fun removeMovie(movie: Movie): Boolean

  suspend fun getLocalMovies(): List<Movie>

}