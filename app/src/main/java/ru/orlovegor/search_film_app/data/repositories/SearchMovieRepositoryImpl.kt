package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.orlovegor.search_film_app.data.paging.MoviesPageSource
import ru.orlovegor.search_film_app.data.remote.MovieApi
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context,
) : SearchMovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMovieByTittlePagingData(tittle: String): PagingData<Movie> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { MoviesPageSource(movieApi, tittle) }
        ).flow
            .mapLatest { pagingData -> pagingData.map { movieDto -> movieDto.mapToMovie(context) } }
            .flowOn(Dispatchers.IO)
            .first()
    }

}
