package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.data.models.MovieApi
import ru.orlovegor.search_film_app.data.models.MoviesPageSource
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteSearchResult
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : SearchMovieRepository {
    override  fun getMovieByTittlePaging(tittle: String): Pager<Int,MovieDto> {
        return Pager<Int, MovieDto>(
            PagingConfig(pageSize = 5 )
        ) {
            MoviesPageSource(movieApi, tittle)
        }
    }


}