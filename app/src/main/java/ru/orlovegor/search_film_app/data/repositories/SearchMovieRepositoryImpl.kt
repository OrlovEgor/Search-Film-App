package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ru.orlovegor.search_film_app.data.models.MovieApi
import ru.orlovegor.search_film_app.data.paging.MoviesPageSource
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : SearchMovieRepository {
    override fun getMovieByTittlePaging(tittle: String): Pager<Int, MovieDto> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { MoviesPageSource(movieApi, tittle) }
        )
    }

}
