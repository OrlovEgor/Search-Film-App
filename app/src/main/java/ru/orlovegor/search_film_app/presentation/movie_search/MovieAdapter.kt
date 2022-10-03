package ru.orlovegor.search_film_app.presentation.movie_search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.data.models.Movie
import ru.orlovegor.search_film_app.databinding.ItemMovieBinding

class MovieAdapter(context: Context) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffItemCallback()) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding by viewBinding(ItemMovieBinding::bind)

        fun bind(movie: Movie) {
            with(binding) {
                itemMovieTitleText.text = movie.title
                itemMovieReleaseDateText.text = movie.releaseDate
                itemMovieSloganText.text = movie.description
                itemMovieRatingText.text = movie.rating.toString()
                Glide.with(itemView)
                    .load(movie.posterUrl)
                    .into(itemMovieImageView)


            }
        }
    }

    private class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

    }


}