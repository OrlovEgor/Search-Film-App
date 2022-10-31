package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.PagingData
import ru.orlovegor.search_film_app.presentation.models.Movie
import ru.orlovegor.search_film_app.utils.RequestQueryConstructor

interface SearchMovieRepository {

  suspend fun getMoviesPagingData(
    tittle: RequestQueryConstructor,
    year: RequestQueryConstructor,
    genre: RequestQueryConstructor,
    rating: RequestQueryConstructor
  ): PagingData<Movie>

}