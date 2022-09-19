package ru.orlovegor.search_film_app.presentation.movie_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.search_film_app.data.models.remote_models.RemoteMovie
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepositoryImpl
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel@Inject constructor(private val searchRepository: SearchMovieRepositoryImpl) : ViewModel() {

    init {
        viewModelScope.launch {
            Log.d("SEARCH", "SCOPE STARTED")
            try {
                val movies = search()
                Log.d("SEARCH", "List movies = $movies")
            }
            catch (t: Throwable){
                Log.d("SEARCH", "Error = ${t.message}")
            }

        }
    }

    private suspend fun search(): List<RemoteMovie>{
       return withContext(Dispatchers.IO){
            searchRepository.getMovie("Начало", "name",false)
        }
    }

}