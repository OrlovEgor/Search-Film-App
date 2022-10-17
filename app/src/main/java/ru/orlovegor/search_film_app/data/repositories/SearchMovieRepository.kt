package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.PagingData
import ru.orlovegor.search_film_app.presentation.models.Movie

interface SearchMovieRepository {

  suspend fun getMovieByTittlePagingData(tittle: String): PagingData<Movie>

}