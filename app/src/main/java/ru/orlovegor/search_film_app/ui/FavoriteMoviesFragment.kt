package ru.orlovegor.search_film_app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.FragmentFavoriteMoviesBinding

class FavoriteMoviesFragment: Fragment(R.layout.fragment_favorite_movies) {

    private val binding: FragmentFavoriteMoviesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}