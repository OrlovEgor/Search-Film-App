package ru.orlovegor.search_film_app.presentation.movie_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.search_film_app.data.models.Movie
import ru.orlovegor.search_film_app.data.models.remote_models.MovieDto
import ru.orlovegor.search_film_app.domain.GetMovieByTittleUsesCase
import java.util.ArrayList
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val getMovieByTittleUsesCase: GetMovieByTittleUsesCase
) : ViewModel() {


    init {
        viewModelScope.launch {
            Log.d("SEARCH", "SCOPE STARTED")
            try {
                val movies = search()
                Log.d(
                    "SEARCH",
                    "Result = ${movies} "
                )
            } catch (t: Throwable) {
                Log.d("SEARCH", "Error = ${t.message}")
            }

        }
    }

    private suspend fun search(): ArrayList<Movie> {
        return withContext(Dispatchers.IO) {

            getMovieByTittleUsesCase.invoke("Начало")
        }
    }

}