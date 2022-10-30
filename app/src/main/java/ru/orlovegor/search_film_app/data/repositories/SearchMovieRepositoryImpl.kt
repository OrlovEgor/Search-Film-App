package ru.orlovegor.search_film_app.data.repositories

import android.content.Context
import androidx.paging.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.orlovegor.search_film_app.data.paging.MoviesPageSource
import ru.orlovegor.search_film_app.data.remote.networking.MovieApi
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.presentation.models.mapToMovie
import ru.orlovegor.search_film_app.utils.RequestQueryConstructor
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context,
) : SearchMovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMoviesPagingData(
        tittle: RequestQueryConstructor,
        year: RequestQueryConstructor,
        genre: RequestQueryConstructor,
        rating: RequestQueryConstructor
    ): PagingData<Movie> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                MoviesPageSource(
                    movieApi,
                    tittle.makeQuery(),
                    year.makeQuery(),
                    genre.makeQuery(),
                    rating.makeQuery()
                )
            }
        ).flow
            .mapLatest { pagingData -> pagingData.map { movieDto -> movieDto.mapToMovie(context) } }
            .flowOn(Dispatchers.IO)
            .first()
    }

}
