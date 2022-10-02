package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto


interface SearchMovieRepository {

   fun getMovieByTittlePaging(tittle: String): Pager<Int, MovieDto>

}