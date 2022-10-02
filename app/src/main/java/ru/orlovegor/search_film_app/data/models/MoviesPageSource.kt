package ru.orlovegor.search_film_app.data.models

import androidx.paging.PagingSource
import androidx.paging.PagingState

import retrofit2.HttpException
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto


class MoviesPageSource (
     private val movieApi: MovieApi,
     private val query: String
) : PagingSource<Int, MovieDto>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize = params.loadSize.coerceAtMost(MovieApi.MAX_PAGE_SIZE)

        val response = movieApi.getMovieByTittle(query, page, pageSize)
        return if (response.isSuccessful) {
            val moviesDto = checkNotNull(response.body()).listMovieDto
            val nextKey = if (moviesDto.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(moviesDto, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }

    interface Factory {
        fun create( movieApi: MovieApi,query: String): MoviesPageSource
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}