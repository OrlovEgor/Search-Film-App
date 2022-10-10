package ru.orlovegor.search_film_app.data.repositories

import androidx.paging.Pager
import ru.orlovegor.search_film_app.data.remote.models.MovieDto


interface SearchMovieRepository {

   fun getMovieByTittlePaging(tittle: String): Pager<Int, MovieDto>

}